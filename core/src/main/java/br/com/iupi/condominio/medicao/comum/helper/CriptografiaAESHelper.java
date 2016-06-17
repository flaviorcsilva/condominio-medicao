package br.com.iupi.condominio.medicao.comum.helper;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class CriptografiaAESHelper {

	private static final String algorithm = "AES";

	public static SecretKey generateKey() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		KeyGenerator generator = KeyGenerator.getInstance(algorithm);
		generator.init(128);

		SecretKey key = generator.generateKey();

		return key;
	}

	public static String encrypt(SecretKey key, String message) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, key);

		byte[] messageBytes = message.getBytes();
		byte[] encrypted = cipher.doFinal(messageBytes);

		return Base64.getEncoder().encodeToString(encrypted);
	}

	public static String decrypt(SecretKey key, String encryptedMessage) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, key);

		byte[] encryptedMessageBytes = Base64.getDecoder().decode(encryptedMessage);
		byte[] decrypted = cipher.doFinal(encryptedMessageBytes);

		return new String(decrypted, StandardCharsets.UTF_8);
	}
}
