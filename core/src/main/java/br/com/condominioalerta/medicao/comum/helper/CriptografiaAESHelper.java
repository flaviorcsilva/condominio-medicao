package br.com.condominioalerta.medicao.comum.helper;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class CriptografiaAESHelper {

	private static final String algoritmo = "AES";

	public static SecretKey geraSecretKey() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		KeyGenerator generator = KeyGenerator.getInstance(algoritmo);
		generator.init(128);

		SecretKey key = generator.generateKey();

		return key;
	}

	public static String criptografa(SecretKey secretKey, String mensagem) throws Exception {
		Cipher cipher = Cipher.getInstance(algoritmo);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		byte[] criptografada = cipher.doFinal(mensagem.getBytes());

		return Base64.getEncoder().encodeToString(criptografada);
	}

	public static String descriptografa(SecretKey secretKey, String mensagemCriptografada) throws Exception {
		Cipher cipher = Cipher.getInstance(algoritmo);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);

		byte[] mensagemCriptografadaBytes = Base64.getDecoder().decode(mensagemCriptografada);
		byte[] descriptografada = cipher.doFinal(mensagemCriptografadaBytes);

		return new String(descriptografada, StandardCharsets.UTF_8);
	}
}