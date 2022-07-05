import java.security.*;

import java.security.PrivateKey;

public class DSASignature {
    public static byte[] sign(PrivateKey privateKey, String content){
        try {
            Signature signature = Signature.getInstance("DSA");
            signature.initSign(privateKey);
            signature.update(content.getBytes());
            return signature.sign();
        }
        catch (Exception e){
            System.err.println("Error while signing");
            return null;
        }
    }

    public static boolean verify(PublicKey publicKey, byte[] signature, String content){
        try {
            Signature verifier = Signature.getInstance("DSA");
            verifier.initVerify(publicKey);
            verifier.update(content.getBytes());
            return verifier.verify(signature);
        }
        catch (Exception e){
            System.err.println("Error while verifying");
            return false;
        }
    }

}
