package com.imooc.bilibili.service.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * AES: Advanced Encryption Standard 高级加密标准
 * 最常见的对称加密算法，即加密和解密使用同样的密钥，加密结果可逆
 * 特点：加密速度非常快，适合经常发送数据的场合
 *
 */
public class AESUtil {

    private static final String KEY_ALGORITHM = "AES";

    private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    private final Cipher decryptCipher;

    private final Cipher encryptCipher;

    private final String seed;

    public AESUtil(String seed) throws Exception {
        this.seed = seed;
        decryptCipher = Cipher.getInstance(KEY_ALGORITHM);
        encryptCipher = Cipher.getInstance(KEY_ALGORITHM);
        decryptCipher.init(Cipher.DECRYPT_MODE, this.getSecretKey());
        encryptCipher.init(Cipher.ENCRYPT_MODE, this.getSecretKey());
    }

    public String decrypt(String content) throws Exception {
        byte[] bytes = Base64.decodeBase64(content);
        byte[] result = decryptCipher.doFinal(bytes);
        return new String(result, StandardCharsets.UTF_8);
    }

    public String encrypt(String content) throws Exception {
        byte[] result = encryptCipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(result);
    }

    public SecretKey getSecretKey() throws Exception {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(seed.getBytes());
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        kg.init(128, random);
        return kg.generateKey();
    }


}
