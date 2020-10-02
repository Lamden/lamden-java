package io.lamden.api;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestNet implements Network {

    private List<URL> masternodes = new ArrayList<>();

    public TestNet() {
        try {
            masternodes.add(new URL("https://testnet-master-1.lamden.io"));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("No valid url for masternode");
        }
    }

    @Override
    public String getName() {
        return "testnet";
    }

    @Override
    public List<URL> getMasterNodes() {
        return masternodes;
    }

    @Override
    public void setMasterNodes(List<URL> masternodes) {
        this.masternodes = masternodes;
    }

}
