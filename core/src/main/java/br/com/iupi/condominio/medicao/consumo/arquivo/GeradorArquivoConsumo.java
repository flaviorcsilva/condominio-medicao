package br.com.iupi.condominio.medicao.consumo.arquivo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;

import br.com.iupi.condominio.medicao.comum.execao.NegocioException;
import br.com.iupi.condominio.medicao.comum.helper.ArquivoHelper;
import br.com.iupi.condominio.medicao.condominio.modelo.Condominio;

@Stateless
public class GeradorArquivoConsumo {

	private final Logger LOGGER = Logger.getLogger(GeradorArquivoConsumo.class.getName());

	public void geraArquivo(Condominio condominio, List<String> conteudo, Integer mes, Integer ano)
			throws NegocioException {
		String mesAno = mes + "-" + ano;

		String nomeDoArquivo = "Rateio de Consumo do " + condominio.getNome() + " - Mês-Ano " + mesAno + ".csv";
		String diretorio = "\\condominio";

		FileWriter fw = ArquivoHelper.getArquivo(diretorio, nomeDoArquivo);

		String nomeCompletoDoArquivo = ArquivoHelper.getNomeCompletoDoArquivo(diretorio, nomeDoArquivo);
		LOGGER.info("Gerando o conteúdo do arquivo: " + nomeCompletoDoArquivo);

		BufferedWriter bw = new BufferedWriter(fw);
		try {
			LOGGER.log(Level.INFO,
					"Gera o arquivo de consumo do condomínio " + condominio.getNome() + " do mes/ano {0}", mesAno);
			String cabecalho = "Rateio de Consumo do " + condominio.getNome() + " do Mês/Ano " + mesAno + "\n";
			bw.write(cabecalho);
			bw.write("\n");

			for (String linha : conteudo) {
				bw.write(linha + "\n");
			}

			bw.close();
			fw.close();
			LOGGER.log(Level.INFO, "Finalizado a geração do arquivo de consumo do condomínio " + condominio.getNome()
					+ " do mes/ano {0}", mesAno);
		} catch (IOException ioe) {
			// gerar exceção String mensagem =
			// Mensagem.CGI_ERRO_GERACAO_ARQUIVO.getMensagem(nomeCompletoDoArquivo,
			// fabricante);
			ioe.printStackTrace();
		}
	}
}