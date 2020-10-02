package io.lamden.api;

import java.net.URL;
import java.util.List;

public interface Network {
    String getName();
    List<URL> getMasterNodes();
    void setMasterNodes(List<URL> masternodes);
}
