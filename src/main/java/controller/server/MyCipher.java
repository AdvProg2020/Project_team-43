package controller.server;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MyCipher {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static MyCipher ourInstance = new MyCipher();

    public static MyCipher getInstance() {
        return ourInstance;
    }

    private MyCipher() {
    }

    public String encryptMessage(String message, String key) throws InvalidKeyException, NoSuchPaddingException,
            NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        byte[] messageBytes = message.getBytes();
        byte[] keyBytes = key.getBytes();
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKey secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedMessage = cipher.doFinal(messageBytes);
        return new String(encryptedMessage);
    }

    public String decryptMessage(String message, String key) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] encryptedMessage = message.getBytes();
        byte[] keyBytes = key.getBytes();
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKey secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] clearMessage = cipher.doFinal(encryptedMessage);
        return new String(clearMessage);
    }

}
