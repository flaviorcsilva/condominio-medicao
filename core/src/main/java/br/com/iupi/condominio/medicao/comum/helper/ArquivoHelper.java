package br.com.iupi.condominio.medicao.comum.helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import br.com.iupi.condominio.medicao.comum.execao.Mensagem;
import br.com.iupi.condominio.medicao.comum.execao.NegocioException;

public class ArquivoHelper {

	public static FileWriter getArquivo(String diretorio, String nomeDoArquivo) throws NegocioException {
		FileWriter fw = null;
		File path = new File(diretorio);

		try {
			if (!path.exists()) {
				path.mkdirs();
			}

			fw = new FileWriter(new File(getNomeCompletoDoArquivo(diretorio, nomeDoArquivo)));
		} catch (IOException e) {
			throw new NegocioException(Mensagem.ARQUIVO_ERRO_AO_ACESSAR,
					getNomeCompletoDoArquivo(diretorio, nomeDoArquivo));
		}

		return fw;
	}

	public static String getNomeCompletoDoArquivo(String diretorio, String nomeDoArquivo) {
		return diretorio + File.separator + nomeDoArquivo;
	}
}