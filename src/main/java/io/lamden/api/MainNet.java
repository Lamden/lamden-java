package io.lamden.api;

import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MainNet implements Network{

    private List<URI> masternodes = new ArrayList<>();

    public MainNet() {
        try {
            masternodes.add(new URI("https://masternode-01.lamden.io"));
            masternodes.add(new URI("https://masternode-02.lamden.io"));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("No valid URI for masternode");
        }
    }

    @Override
    public String getName() {
        return "mainnet";
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
