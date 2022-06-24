import java.security.*;

import java.security.PrivateKey;

public class DSASignature {
    public static byte[] sign(PrivateKey privateKey, String content) throws SignatureException, InvalidKeyException, NoSuchAlgorithmException {
        Signature signature = Signature.getInstance("DSA");
        signature.initSign(privateKey);
        signature.update(content.getBytes());
        return signature.sign();
    }

    public static boolean verify(PublicKey publicKey, byte[] signature, String content) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature verifier = Signature.getInstance("DSA");
        verifier.initVerify(publicKey);
        verifier.update(content.getBytes());
        return verifier.verify(signature);
    }
}
