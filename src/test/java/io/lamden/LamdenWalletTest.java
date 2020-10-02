package io.lamden;

import io.lamden.wallet.KeyPair;
import io.lamden.wallet.LamdenWallet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

class LamdenWalletTest {

    private static final SecureRandom RANDOM = new SecureRandom();

    private LamdenWallet testee = new LamdenWallet();

    @Test
    void generateKeyPair_withoutSeed_returnsConsistent(){
        //Act
        KeyPair keyPair = testee.generateKeyPair();

        //Assert
        KeyPair verificationPair = testee.generateKeyPairFromPrivateKey(keyPair.getPrivateKey());
        Assertions.assertEquals(verificationPair, keyPair);
    }

    @Test
    void generateKeyPair_withSeed_returnsConsistent(){
        //Act
        KeyPair keyPair = testee.generateKeyPair(3456);

        //Assert
        KeyPair verificationPair = testee.generateKeyPairFromPrivateKey(keyPair.getPrivateKey());
        Assertions.assertEquals(verificationPair, keyPair);
    }

    @Test
    void generateKeyPair_withNegativeSeed_returnsConsistent(){
        //Act
        KeyPair keyPair = testee.generateKeyPair(-3456);

        //Assert
        KeyPair verificationPair = testee.generateKeyPairFromPrivateKey(keyPair.getPrivateKey());
        Assertions.assertEquals(verificationPair, keyPair);
    }

    @Test
    void generateKeyPairFromPrivateKey_withJavascriptGeneratedPrivateKey_returnsSame(){
        //Arrange
        String jsPrivateKey = "c3a542e8a03067781f6b1352bf9b3fbeb65c6fa54cf0da5b1815a477ea656147";
        String jsPublicKey = "b30aa7639eb57a084e82746c338ec663053f976c5c70ff605d37157649c874bb";

        //Act
        KeyPair keyPair = testee.generateKeyPairFromPrivateKey(jsPrivateKey);

        //Assert
        Assertions.assertEquals(jsPublicKey, keyPair.getPublicKey());
    }

    @Test
    void signAndVerifyMessage_newMessage_verified(){
        //Arrange
        KeyPair keyPair = testee.generateKeyPair();
        String message = "This is a test message";

        //Act
        String signature = testee.signMessage(keyPair.getPrivateKey(), message);
        boolean verificationResult = testee.verifyMessage(keyPair.getPublicKey(), message, signature);

        //Assert
        Assertions.assertTrue(verificationResult);
    }

    @Test
    void signAndVerifyMessage_wrongMessage_notVerified(){
        //Arrange
        KeyPair keyPair = testee.generateKeyPair();
        String inputMessage = "This is a test message";
        String verifiedMessage = "This is a wrong message";


        //Act
        String signature = testee.signMessage(keyPair.getPrivateKey(), inputMessage);
        boolean verificationResult = testee.verifyMessage(keyPair.getPublicKey(), verifiedMessage, signature);

        //Assert
        Assertions.assertFalse(verificationResult);
    }

}