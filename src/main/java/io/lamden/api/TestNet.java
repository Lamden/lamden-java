package io.lamden.api;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestNet implements Network {

    private List<MasterNode> masternodes = new ArrayList<>();

    public TestNet(int retriesUntilUnhealthy, int healthCheckTimeSpanSeconds){
        try {
            masternodes.add(new MasterNode(new URI("https://testnet-master-1.lamden.io"), retriesUntilUnhealthy, healthCheckTimeSpanSeconds));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("No valid url for masternode");
        }
    }

    public TestNet() {
        this(5,300);
    }

    @Override
    public String getName() {
        return "testnet";
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
