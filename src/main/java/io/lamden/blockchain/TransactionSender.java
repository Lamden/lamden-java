package io.lamden.blockchain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lamden.api.Network;
import io.lamden.exception.RequestFailedException;
import io.lamden.common.Signer;
import io.lamden.api.MasterNodeApi;
import io.lamden.api.json.nonce.Nonce;
import io.lamden.exception.TransactionFailedException;
import io.lamden.utils.HexUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
public class TransactionSender {

    private final TransactionInfo txInfo;
    private final MasterNodeApi api;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Signer signer;

    /**
     * Creates a <tt>TransactionSender</tt> which allows to send signed transactions to the Lamden blockchain
     * @param network target network for the transaction
     * @param txInfo transaction data
     */
    public TransactionSender(Network network, TransactionInfo txInfo) {

        //TODO: Validate everything
        this.txInfo = txInfo;
        this.api = new MasterNodeApi(network);

        this.signer = new Signer();
    }

    /**
     * Sends the transaction to the blockchain.
     * @param senderPrivateKey privateKey for signing
     */
    public String send(String senderPrivateKey) {

        if (txInfo.getNonce() == 0 || txInfo.getProcessor() == null || txInfo.getProcessor().isEmpty()){
            //get nonce
            Nonce nonce = this.readNonce();
            txInfo.setNonce(nonce.getNonce());
            txInfo.setProcessor(nonce.getProcessor());
        }

        //make payload
        Map<String, Object> payload = makePayload();

        //sign transaction
        byte[] signedMessage = sign(senderPrivateKey, payload);

        // send transaction
        Map<String, Object> rawTransaction = makeTransaction(signedMessage, payload);
        Map<String, Object> txResult = api.sendTransaction(rawTransaction);
        if (txResult.containsKey("error")){
            log.error("Transaction failed: " + txResult.get("error"));
            throw new TransactionFailedException(txResult.get("error").toString());
        }else{
            log.info("Transaction successful: {}", txResult.toString());
            return txResult.get("hash").toString();
        }


    }

    private Map<String, Object> makeTransaction(byte[] signedMessage, Map<String,Object> payload){
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("signature", HexUtils.bytesToHex(signedMessage));
        metadata.put("timestamp", Instant.now().toEpochMilli() / 1000);

        Map<String, Object> transaction = new HashMap<>();
        transaction.put("metadata", metadata);
        transaction.put("payload", payload);

        return transaction;
    }

    private Map<String, Object> makePayload(){

        TreeMap<String, Object> sortedArgs = new TreeMap<>();
        sortMap(sortedArgs, this.txInfo.getArgs());

        Map<String, Object> payload = new TreeMap<>();
        payload.put("contract", this.txInfo.getContractName());
        payload.put("function", this.txInfo.getMethodName());
        payload.put("kwargs", sortedArgs);
        payload.put("nonce", this.txInfo.getNonce());
        payload.put("processor", this.txInfo.getProcessor());
        payload.put("sender", this.txInfo.getSenderPublicKey());
        payload.put("stamps_supplied", this.txInfo.getStampLimit());

        return payload;
    }

    private void sortMap(TreeMap<String, Object> targetMap, Map<String, Object> unsortedMap){

        for (Map.Entry<String, Object> kvEntry : unsortedMap.entrySet()) {
            if (kvEntry.getValue() instanceof Map){
                TreeMap<String, Object> innerMap = new TreeMap<>();
                targetMap.put(kvEntry.getKey(), innerMap);
                sortMap(innerMap, (Map<String,Object>)kvEntry.getValue());
            }else {
                targetMap.put(kvEntry.getKey(), kvEntry.getValue());
            }
        }

    }

    private byte[] sign(String senderPrivateKey, Map<String, Object> payload) {
        try{
            //if (log.isDebugEnabled()){
                log.info("Sending transaction: {}", mapper.writer().writeValueAsString(payload));
            //}
            byte[] payloadBytes = mapper.writer().writeValueAsBytes(payload);
            return signer.sign(senderPrivateKey, payloadBytes);
        }catch (JsonProcessingException e) {
            throw new RequestFailedException("Message could not be signed", e);
        }

    }

    private Nonce readNonce() {
        return api.readNonce(txInfo.getSenderPublicKey());
    }
}
