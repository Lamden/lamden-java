package io.lamden.api;

import java.net.URI;
import java.util.List;

public interface Network {
    String getName();
    List<MasterNode> getMasterNodes();
    void setMasterNodes(List<MasterNode> masternodes);
}
