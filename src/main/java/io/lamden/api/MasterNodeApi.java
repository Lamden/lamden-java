package io.lamden.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lamden.api.datatypes.FloatValue;
import io.lamden.api.json.contract.ContractInfoResult;
import io.lamden.api.json.method.MethodsResult;
import io.lamden.api.json.nonce.NonceResult;
import io.lamden.api.json.transaction.TransactionResult;
import io.lamden.exception.MasternodesNotAvailableException;
import io.lamden.exception.RequestFailedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class MasterNodeApi {

    private final Network network;
    ObjectMapper mapper = new ObjectMapper();

    public MasterNodeApi(Network network) {
        this.network = network;
    }

    public NonceResult readNonce(String senderPublicKey) {
        URI uri = network.getMasterNodes().get(0);
        HttpGet request = new HttpGet(uri.resolve("/nonce/" + senderPublicKey));

        return send(request, NonceResult.class);
    }

    public TransactionResult readTransaction(String hash) {
        URI uri = network.getMasterNodes().get(0);
        HttpGet request = new HttpGet( uri.resolve("/tx?hash=" + hash));

        try{
            return send(request, TransactionResult.class);
        }catch(RequestFailedException e){
            if (e.getStatusCode() == HttpStatus.SC_BAD_REQUEST){
                //Hmm, masternode should respond with 404 instead of 400 in this case
                return null;
            }else{
                throw e;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> sendTransaction(Map<String, Object> data) {
        String host = network.getMasterNodes().get(0).toString();
        HttpPost request = new HttpPost(host);

        try {
            request.setEntity(new StringEntity(mapper.writeValueAsString(data), StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RequestFailedException("Error while preparing transaction for sending", e);
        }

        return send(request, Map.class);
    }

    public <T> T send(HttpRequestBase request, Class<T> targetType) {

        final MasterNodeRequest<T> masterNodeRequest = (HttpRequestBase newRequest) -> {
            try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
                try (CloseableHttpResponse response = client.execute(request)) {
                    StatusLine statusLine = response.getStatusLine();
                    String content = readInputStream(response.getEntity().getContent());
                    if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                        return mapper.readValue(content, targetType);
                    }else{
                        log.error("Request failed with message: " + content);
                        throw new RequestFailedException("Http request did not respond with Code 200 (returned " + statusLine.getStatusCode() + "), please take a look at the response details", statusLine.getStatusCode(),  content);
                    }
                }
            }
        };

        return runWithRetries(network.getMasterNodes(), request, masterNodeRequest ,targetType);

    }

    <T> T runWithRetries(List<URI> masterNodes, HttpRequestBase request, MasterNodeRequest<T> t, Class<T> targetType) {

        List<Integer> indexes = IntStream.rangeClosed(0, masterNodes.size()-1).boxed().collect(Collectors.toList());
        Collections.shuffle(indexes);

        URI uri = request.getURI();

        for (int i = 0; i < indexes.size(); i++) {

            int currentIndex = indexes.get(i);

            URI newUri = null;

            try {
                newUri = replaceHost(uri, masterNodes.get(currentIndex).getHost());
                request.setURI(newUri);
                return t.call(request);

            } catch (URISyntaxException e) {
                throw new IllegalArgumentException("URI is not valid: " + uri);
            } catch (IOException e){

                int leftRetries = indexes.size() - 1 - i;
                if (leftRetries == 0){
                    //No more retries left
                    throw new MasternodesNotAvailableException("Request "+ newUri.toString() +" could not be processed despite "+ (indexes.size() -1) +" retries!", e);
                }else{
                    log.info("Request failed: {}", newUri);
                    log.info("{} retries left", leftRetries);
                }
            }
        }

        throw new IllegalStateException("Request "+ uri.toString() +" not failed, but also not successful --> illegal state");

    }


    private URI replaceHost(URI uri, String newHostName) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(uri.toString());
        builder.setHost(newHostName);
        return builder.build();
    }

    private String readInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        byte[] byteArray = buffer.toByteArray();

        return new String(byteArray, StandardCharsets.UTF_8);
    }

    public ContractInfoResult readContractInfo(String contractName) {
        URI uri = network.getMasterNodes().get(0);
        HttpGet request = new HttpGet( uri.resolve("/contracts/" + contractName));

        try{
            return send(request, ContractInfoResult.class);
        }catch(RequestFailedException e){
            if (e.getStatusCode() == HttpStatus.SC_NOT_FOUND){
                return null;
            }else{
                throw e;
            }
        }
    }

    public <T> T readVariable(String contractName, String variableName, String params, Class<T> resultType) {
        URI uri = network.getMasterNodes().get(0);

        if (params != null && !params.trim().isEmpty()){
            uri = uri.resolve("/contracts/" + contractName + "/" + variableName + "?key=" + params);
        }else{
            uri = uri.resolve("/contracts/" + contractName + "/" + variableName);
        }

        HttpGet request = new HttpGet(uri);

        try{
            return send(request, resultType);
        }catch(RequestFailedException e){
            if (e.getStatusCode() == HttpStatus.SC_NOT_FOUND){
                return null;
            }else{
                throw e;
            }
        }
    }

    public MethodsResult readContractMethods(String contractName) {
        URI uri = network.getMasterNodes().get(0);
        HttpGet request = new HttpGet(uri.resolve("/contracts/" + contractName + "/methods"));

        try{
            return send(request, MethodsResult.class);
        }catch(RequestFailedException e){
            if (e.getStatusCode() == HttpStatus.SC_NOT_FOUND){
                return null;
            }else{
                throw e;
            }
        }
    }

    public boolean pingServer() {
        URI uri = network.getMasterNodes().get(0);
        HttpGet request = new HttpGet( uri.resolve("/ping/"));

        try{
            send(request, Void.class);
            return true;
        }catch(RequestFailedException e){
            return false;
        }

    }

    public BigDecimal readCurrencyBalance(String publicKey) {
        FloatValue result = this.readVariable("currency", "balances", publicKey, FloatValue.class);
        if (result == null){
            //There ist no balance
            return null;
        }else{
            return result.getValue();
        }

    }
}
