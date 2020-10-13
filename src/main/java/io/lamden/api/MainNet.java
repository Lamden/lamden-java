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

    private List<MasterNode> masternodes = new ArrayList<>();

    public MainNet(int retriesUntilUnhealthy, int healthCheckTimeSpanSeconds){
        try {
            masternodes.add(new MasterNode(new URI("https://masternode-01.lamden.io"), retriesUntilUnhealthy, healthCheckTimeSpanSeconds));
            masternodes.add(new MasterNode(new URI("https://masternode-02.lamden.io"), retriesUntilUnhealthy, healthCheckTimeSpanSeconds));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("No valid URI for masternode");
        }
    }

    public MainNet(){
        this(5, 300);
    }



    @Override
    public String getName() {
        return "mainnet";
    }

    @Override
    public List<MasterNode> getMasterNodes() {
        return masternodes;
    }

    @Override
    public void setMasterNodes(List<MasterNode> masternodes) {
        this.masternodes = masternodes;
    }

}
