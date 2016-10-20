package br.com.condominioalerta.medicao.comum.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.commons.io.IOUtils;

import br.com.condominioalerta.medicao.comum.execao.Mensagem;
import br.com.condominioalerta.medicao.comum.execao.NegocioException;

public class ArquivoHelper {

	public static BufferedWriter getArquivo(String diretorio, String nomeDoArquivo) throws NegocioException {
		BufferedWriter bw = null;
		File path = new File(diretorio);

		try {
			if (!path.exists()) {
				path.mkdirs();
			}

			File file = new File(getNomeCompletoDoArquivo(diretorio, nomeDoArquivo));

			FileOutputStream fos = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");

			bw = new BufferedWriter(osw);

		} catch (IOException e) {
			throw new NegocioException(Mensagem.ARQUIVO_ERRO_AO_ACESSAR,
					getNomeCompletoDoArquivo(diretorio, nomeDoArquivo));
		}

		return bw;
	}

	public static String getNomeCompletoDoArquivo(String diretorio, String nomeDoArquivo) {
		return diretorio + File.separator + nomeDoArquivo;
	}
	
	public static byte[] converteToByteArray(File file) {
		byte[] fileData = null;
		
        try {
            fileData = org.apache.commons.io.IOUtils.toByteArray(new FileInputStream(file));
        } catch (IOException ioe) {
        	ioe.printStackTrace();
        }
        
        return fileData;
	}
	
	public static String converteArquivoResourceToString(String fileResource) {
		String content = "";
		try {
			content = IOUtils.toString(ArquivoHelper.class.getClassLoader().getResourceAsStream(fileResource), "UTF-8");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new NegocioException(Mensagem.ARQUIVO_ERRO_AO_ACESSAR, fileResource);
		}
		
		return content;
	}
}