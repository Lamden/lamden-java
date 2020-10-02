package io.lamden.common;

import io.lamden.utils.HexUtils;
import org.bouncycastle.math.ec.rfc8032.Ed25519;

public class Signer {

    public byte[] sign(String senderPrivateKey, byte[] payload) {
        byte[] privateKeyBytes = HexUtils.hexToBytes(senderPrivateKey);
        byte[] signatureBytes = new byte[Ed25519.SIGNATURE_SIZE];

        Ed25519.sign(privateKeyBytes, 0, payload, 0, payload.length, signatureBytes, 0);

        return signatureBytes;
    }

}
