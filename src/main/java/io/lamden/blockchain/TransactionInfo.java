package io.lamden.blockchain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Map;

/**
 * A builder to define all transaction related data
 */
@Builder
@Data
public class TransactionInfo {

    @NonNull
    private String senderPublicKey;

    @NonNull
    private String contractName;

    @NonNull
    private String methodName;

    @NonNull
    private int stampLimit;

    @NonNull
    private Map<String, Object> args;

    private int nonce;

    private String processor;

}
