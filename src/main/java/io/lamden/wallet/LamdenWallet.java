package io.lamden.wallet;

import io.lamden.utils.HexUtils;
import io.lamden.common.Signer;
import org.bouncycastle.math.ec.rfc8032.Ed25519;

import java.security.SecureRandom;

/**
 * A Lamden Java Wallet
 */
public class LamdenWallet {

    private static final SecureRandom RANDOM = new SecureRandom();
    private final Signer signer;

    /**
     * Creates a new Lamden Wallet
     */
    public LamdenWallet() {
        this.signer = new Signer();
    }

    /**
     * Generates a new keypair (publicKey / privateKey) based on a random seed
     * @return the generated <tt>KeyPair</tt>
     */
    public KeyPair generateKeyPair()  {
        return this.generateKeyPair(null);
    }

    /**
     * Generates a new keypair with a defined seed
     * @param seed seed to use
     * @return the generated <tt>KeyPair</tt>
     */
    public KeyPair generateKeyPair(long seed)  {
        return this.generateKeyPair(Long.valueOf(seed));
    }

    /**
     * Generates a new keypair with a defined seed
     * @param seed seed to use
     * @return the generated <tt>KeyPair</tt>
     */
    private KeyPair generateKeyPair(Long seed)  {
        byte[] privateKeyBytes = new byte[Ed25519.SECRET_KEY_SIZE];
        byte[] publicKeyBytes = new byte[Ed25519.PUBLIC_KEY_SIZE];

        //Create private key
        if (seed != null){
            RANDOM.setSeed(seed);
        }

        //Generate random private key
        RANDOM.nextBytes(privateKeyBytes);

        //Generate associating public Key
        Ed25519.generatePublicKey(privateKeyBytes, 0, publicKeyBytes, 0);
        return new KeyPair(HexUtils.bytesToHex(privateKeyBytes), HexUtils.bytesToHex(publicKeyBytes));
    }

    /**
     * Generates a <tt>KeyPair</tt> from a predefined privateKey
     * @param privateKey the privateKey to use
     * @return the generated <tt>KeyPair</tt>
     */
    public KeyPair generateKeyPairFromPrivateKey(String privateKey) {
        byte[] privateKeyBytes = HexUtils.hexToBytes(privateKey);
        byte[] publicKeyBytes = new byte[Ed25519.PUBLIC_KEY_SIZE];

        Ed25519.generatePublicKey(privateKeyBytes, 0, publicKeyBytes, 0);
        return new KeyPair(HexUtils.bytesToHex(privateKeyBytes), HexUtils.bytesToHex(publicKeyBytes));
    }

    /**
     * Signs a message with a privateKey
     * @param privateKey privateKey to use
     * @param message message to sign
     * @return Hex representation of the signed message
     */
    public String signMessage(String privateKey, String message){
        return HexUtils.bytesToHex(signer.sign(privateKey, message.getBytes()));
    }

    /**
     * Verfies a signed message with a publicKey
     * @param publicKey the publicKey for verification
     * @param message the desired message (as normal readable String)
     * @param signedHexMessage the signed message (as hex String)
     * @return <tt>true</tt> if the verification was successful, otherwise <tt>false</tt>
     */
    public boolean verifyMessage(String publicKey, String message, String signedHexMessage){
        byte[] publicKeyBytes = HexUtils.hexToBytes(publicKey);
        byte[] messageBytes = message.getBytes();
        byte[] signatureBytes = HexUtils.hexToBytes(signedHexMessage);

        return Ed25519.verify(signatureBytes, 0, publicKeyBytes, 0, messageBytes, 0, messageBytes.length);
    }



}
