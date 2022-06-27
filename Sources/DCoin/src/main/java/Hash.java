import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    public static String getHash(String message) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = messageDigest.digest(message.getBytes());
            return Hex.encodeHexString(bytes);
        }
        catch (NoSuchAlgorithmException ignored) {
            return null;
        }
    }
}
