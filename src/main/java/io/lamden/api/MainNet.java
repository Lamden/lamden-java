package io.lamden.api;

import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MainNet implements Network{

    private List<URL> masternodes = new ArrayList<>();

    public MainNet() {
        try {
            masternodes.add(new URL("https://masternode-01.lamden.io"));
            masternodes.add(new URL("https://masternode-02.lamden.io"));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("No valid url for masternode");
        }
    }

    @Override
    public String getName() {
        return "mainnet";
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
