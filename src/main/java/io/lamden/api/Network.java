package io.lamden.api;

import java.net.URI;
import java.util.List;

public interface Network {
    String getName();
    List<URI> getMasterNodes();
    void setMasterNodes(List<URI> masternodes);
}
