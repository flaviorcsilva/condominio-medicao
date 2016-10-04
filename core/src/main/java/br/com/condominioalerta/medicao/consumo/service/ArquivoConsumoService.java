package br.com.iupi.condominio.medicao.consumo.arquivo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;

import br.com.iupi.condominio.medicao.comum.helper.ArquivoHelper;
import br.com.iupi.condominio.medicao.condominio.modelo.Condominio;
import br.com.iupi.condominio.medicao.consumo.dto.ConsumoDTO;
import br.com.iupi.condominio.medicao.consumo.modelo.ConsumoCondominio;
import br.com.iupi.condominio.medicao.consumo.modelo.ConsumoUnidade;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedicao;
import br.com.iupi.condominio.medicao.unidade.modelo.UnidadeConsumidora;

@Stateless
public class GeradorArquivoConsumo {

	private final Logger LOGGER = Logger.getLogger(GeradorArquivoConsumo.class.getName());
	private final String cabecalhoUnidade = "Unidade;Tipo Medicao;Leitura Anterior;Medicao Anterior;Leitura Atual;Medicao Atual;Consumo;Fator;Valor (m3);Valor A Pagar\n";
	private final String linhaEmBranco = "\n";
	private final DecimalFormat df = new DecimalFormat("##0.00");

	public void geraArquivo(Map<UnidadeConsumidora, List<ConsumoUnidade>> consumosUnidade, Condominio condominio,
			ConsumoCondominio consumoCondominio, Integer mes, Integer ano) {
		String mesAno = mes + "-" + ano;

		String nomeDoArquivo = "Rateio de Consumo do " + condominio.getNome() + " - MES-ANO " + mesAno + ".csv";
		String diretorio = "//Users/flavio/Documents/condominio";

		BufferedWriter bw = ArquivoHelper.getArquivo(diretorio, nomeDoArquivo);

		String nomeCompletoDoArquivo = ArquivoHelper.getNomeCompletoDoArquivo(diretorio, nomeDoArquivo);
		LOGGER.info("Gerando o conteúdo do arquivo: " + nomeCompletoDoArquivo);

		try {
			LOGGER.log(Level.INFO,
					"Gera o arquivo de consumo do condomínio " + condominio.getNome() + " do mes/ano {0}", mesAno);
			String cabecalho = "Rateio de Consumo do " + condominio.getNome() + " do MES/ANO " + mesAno + "\n";
			bw.write(cabecalho);
			bw.write("\n");

			Double valorTotalRateadoAgua = 0.0;
			Double valorTotalRateadoGas = 0.0;
			Double valorTotalUnidade = 0.0;

			for (UnidadeConsumidora unidade : consumosUnidade.keySet()) {
				bw.write(cabecalhoUnidade);
				valorTotalUnidade = 0.0;

				for (ConsumoUnidade consumo : consumosUnidade.get(unidade)) {
					valorTotalUnidade = valorTotalUnidade + consumo.getValor();

					if (consumo.getTipoMedicao().equals(TipoMedicao.GAS)) {
						valorTotalRateadoGas = valorTotalRateadoGas + consumo.getValor();
					} else {
						valorTotalRateadoAgua = valorTotalRateadoAgua + consumo.getValor();
					}

					bw.write(new ConsumoDTO(consumo).toString() + "\n");
				}

				/* Adiciona o rodápe de totalização da unidade */
				bw.write("TOTAL;;;;;;;;;" + df.format(valorTotalUnidade) + "\n");
				bw.write(linhaEmBranco);
			}

			Double valorTotalFaturadoAgua = consumoCondominio.getValorTotalFaturado();
			Double valorTotalCondominioAgua = valorTotalFaturadoAgua - valorTotalRateadoAgua;

			/* Gerando o total do valor rateado de água pelas unidades */
			bw.write(linhaEmBranco);
			bw.write("TOTAL DA AGUA RATEADA;;;;;;;;;" + df.format(valorTotalRateadoAgua));
			bw.write(linhaEmBranco);
			bw.write("TOTAL DA AGUA CONDOMINIO;;;;;;;;;" + df.format(valorTotalCondominioAgua));
			bw.write(linhaEmBranco);
			bw.write("TOTAL DA AGUA FATURADA;;;;;;;;;" + df.format(valorTotalFaturadoAgua));
			bw.write(linhaEmBranco);
			bw.write(linhaEmBranco);

			/* Gerando o total do valor rateado de gás pelas unidades */
			bw.write("TOTAL DO GAS RATEADO;;;;;;;;;" + df.format(valorTotalRateadoGas));
			bw.write(linhaEmBranco);

			bw.close();
			// fw.close();
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