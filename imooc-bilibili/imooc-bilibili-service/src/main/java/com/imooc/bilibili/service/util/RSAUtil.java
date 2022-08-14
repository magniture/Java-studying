package com.imooc.bilibili.service.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA加密
 * 非对称加密，有公钥和私钥之分，公钥用于数据加密，私钥用于数据解密。加密结果可逆
 * 公钥一般提供给外部进行使用，私钥需要放置在服务器端保证安全性。
 * 特点：加密安全性很高，但是加密速度较慢
 *
 */
public class RSAUtil {

	private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQk33iNdA8Iey7J6XrBsidqn6u8EDLWPHsfEUgLQ3qiTikhPKDTzZkpAfU/O0x6NvSKa7Dp0+uqWT3vnW1De0+3u8mCYdVfOdH94VG4xg5U5UrRJei8HhPiXuvKQ+6NBtebCCW5adZ4pBgOiU14cJLhVmm+dYiLo3IDD5LqrlomQIDAQAB";

	private static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJCTfeI10Dwh7LsnpesGyJ2qfq7wQMtY8ex8RSAtDeqJOKSE8oNPNmSkB9T87THo29IprsOnT66pZPe+dbUN7T7e7yYJh1V850f3hUbjGDlTlStEl6LweE+Je68pD7o0G15sIJblp1nikGA6JTXhwkuFWab51iIujcgMPkuquWiZAgMBAAECgYA1UT9mciQWWQh9yNRmhXzssFjB2TZ8B5RIe1fe0t7D9NEf0yvAgzDzEo8U3CX5dv/CVL7vxr8bEbt7phCwsa8hJiLEOr7hLZaJzXVTbvfqb91oCZGNkqDQ3NJfGBMVgUmltEYF2Bbk3U0NDyat+Gu54tRd2OH+adJYKsD0XYeDBQJBAN5FE8E04A4FA1q8mQbVTSVJDYIEJwOrdC0r3iZ7za5CyXGk+br8pFalRePFaksRGdN32+mYhDKVNrNHspAObVMCQQCmhBsD+xiWrmpnrzeIfCW1cX8qRC3/RMkq0ACw3l6YedNFdN2Tb5WsRHmcbCI9y8mfLHiG/X1R+zHZKG67EKjjAkAmvAkGSY2mQ89i160fWLq5/bIh71FRPWbgnF15fWfJr4/lgyeWI4MMKn80g2nTrSZACQpE+jRHkGNY+OywWCNLAkEAli5nvztkfeJpDYK2b16pE/B9ZL2BTs3XMcnQFbU5VAPsTKSOgz8MmwZXOIE+kMWP3wPY4McXlC0eVGFnHUh1SQJAeAl3RPk+XbZDMYfPkStRJwocG9Ap+88mwTgR1I7uPzZ1aM84/WsQskiVMXv2SZLmMWvYtnhIKosL6IACp2AcDA==";

	public static void main(String[] args) throws Exception{
		String str = RSAUtil.encrypt("123456");
		System.out.println(str);
	}

	public static String getPublicKeyStr(){
		return PUBLIC_KEY;
	}

	public static RSAPublicKey getPublicKey() throws Exception {
		byte[] decoded = Base64.decodeBase64(PUBLIC_KEY);
		return (RSAPublicKey) KeyFactory.getInstance("RSA")
				.generatePublic(new X509EncodedKeySpec(decoded));
	}

	public static RSAPrivateKey getPrivateKey() throws Exception {
		byte[] decoded = Base64.decodeBase64(PRIVATE_KEY);
		return (RSAPrivateKey) KeyFactory.getInstance("RSA")
				.generatePrivate(new PKCS8EncodedKeySpec(decoded));
	}
	
	public static RSAKey generateKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(1024, new SecureRandom());
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
		String privateKeyString = new String(Base64.encodeBase64(privateKey.getEncoded()));
		return new RSAKey(privateKey, privateKeyString, publicKey, publicKeyString);
	}

	public static String encrypt(String source) throws Exception {
		byte[] decoded = Base64.decodeBase64(PUBLIC_KEY);
		RSAPublicKey rsaPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
				.generatePublic(new X509EncodedKeySpec(decoded));
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(1, rsaPublicKey);
		return Base64.encodeBase64String(cipher.doFinal(source.getBytes(StandardCharsets.UTF_8)));
	}

	public static Cipher getCipher() throws Exception {
		byte[] decoded = Base64.decodeBase64(PRIVATE_KEY);
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
				.generatePrivate(new PKCS8EncodedKeySpec(decoded));
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(2, rsaPrivateKey);
		return cipher;
	}

	public static String decrypt(String text) throws Exception {
		Cipher cipher = getCipher();
		byte[] inputByte = Base64.decodeBase64(text.getBytes(StandardCharsets.UTF_8));
		return new String(cipher.doFinal(inputByte));
	}
	
	public static class RSAKey {
		  private RSAPrivateKey privateKey;
		  private String privateKeyString;
		  private RSAPublicKey publicKey;
		  public String publicKeyString;

		  public RSAKey(RSAPrivateKey privateKey, String privateKeyString, RSAPublicKey publicKey, String publicKeyString) {
		    this.privateKey = privateKey;
		    this.privateKeyString = privateKeyString;
		    this.publicKey = publicKey;
		    this.publicKeyString = publicKeyString;
		  }

		  public RSAPrivateKey getPrivateKey() {
		    return this.privateKey;
		  }

		  public void setPrivateKey(RSAPrivateKey privateKey) {
		    this.privateKey = privateKey;
		  }

		  public String getPrivateKeyString() {
		    return this.privateKeyString;
		  }

		  public void setPrivateKeyString(String privateKeyString) {
		    this.privateKeyString = privateKeyString;
		  }

		  public RSAPublicKey getPublicKey() {
		    return this.publicKey;
		  }

		  public void setPublicKey(RSAPublicKey publicKey) {
		    this.publicKey = publicKey;
		  }

		  public String getPublicKeyString() {
		    return this.publicKeyString;
		  }

		  public void setPublicKeyString(String publicKeyString) {
		    this.publicKeyString = publicKeyString;
		  }
		}
}