package io.lamden.wallet;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class KeyPair {
    private String privateKey;
    private String publicKey;
}
