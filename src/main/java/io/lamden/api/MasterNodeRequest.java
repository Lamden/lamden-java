package io.lamden.api;

import org.apache.http.client.methods.HttpRequestBase;

import java.io.IOException;

public interface MasterNodeRequest<T> {
    T call(MasterNode masterNode, HttpRequestBase newRequest) throws IOException;
}
