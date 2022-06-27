import java.security.*;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;


public class DSAKeyPair {
    private final static int KEY_SIZE = 1024;
    private final DSAPrivateKey privateKey;
    private final DSAPublicKey publicKey;

    private DSAKeyPair(DSAPrivateKey privateKey, DSAPublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public static DSAKeyPair getKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("DSA");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        generator.initialize(KEY_SIZE,secureRandom);
        KeyPair pair = generator.generateKeyPair();
        return new DSAKeyPair((DSAPrivateKey)pair.getPrivate(), (DSAPublicKey )pair.getPublic());
    }

    public DSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    public DSAPublicKey getPublicKey() {
        return publicKey;
    }
}
