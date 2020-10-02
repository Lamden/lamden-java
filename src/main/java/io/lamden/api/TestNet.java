package io.lamden.api;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestNet implements Network {

    private List<URI> masternodes = new ArrayList<>();

    public TestNet() {
        try {
            masternodes.add(new URI("https://testnet-master-1.lamden.io"));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("No valid url for masternode");
        }
    }

    @Override
    public String getName() {
        return "testnet";
    }

    @Override
    public List<URI> getMasterNodes() {
        return masternodes;
    }

    @Override
    public void setMasterNodes(List<URI> masternodes) {
        this.masternodes = masternodes;
    }

}
