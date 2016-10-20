package br.com.condominioalerta.medicao.consumo.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;

import br.com.condominioalerta.medicao.comum.execao.Mensagem;
import br.com.condominioalerta.medicao.comum.execao.NegocioException;
import br.com.condominioalerta.medicao.comum.helper.ArquivoHelper;
import br.com.condominioalerta.medicao.comum.helper.NumeroHelper;
import br.com.condominioalerta.medicao.condominio.model.Condominio;
import br.com.condominioalerta.medicao.consumo.dto.ConsumoDTO;
import br.com.condominioalerta.medicao.consumo.model.ConsumoCondominio;
import br.com.condominioalerta.medicao.consumo.model.ConsumoUnidade;
import br.com.condominioalerta.medicao.medidor.model.TipoMedicao;
import br.com.condominioalerta.medicao.unidade.model.UnidadeConsumidora;

@Stateless
public class ArquivoConsumoService {

	private final Logger LOGGER = Logger.getLogger(ArquivoConsumoService.class.getName());
	private final String CABECALHO_UNIDADE = "Unidade;Tipo Medicao;Leitura Anterior;Medicao Anterior;Leitura Atual;Medicao Atual;Consumo;Fator;Valor (m3);Valor A Pagar\n";
	private final String LINHA_EM_BRANCO = "\n";

	public File geraArquivo(Map<UnidadeConsumidora, List<ConsumoUnidade>> consumosUnidade, Condominio condominio,
			ConsumoCondominio consumoCondominio, Integer mes, Integer ano) {
		String mesAno = NumeroHelper.formataNumeroTo2Inteiros(mes) + "-" + ano;

		String nomeDoArquivo = "rateio-consumo-" + condominio.getNomeFormatado() + "-mes-" + mesAno + ".csv";
		String diretorio = "/data/condominio";

		BufferedWriter bw = ArquivoHelper.getArquivo(diretorio, nomeDoArquivo);

		String nomeCompletoDoArquivo = ArquivoHelper.getNomeCompletoDoArquivo(diretorio, nomeDoArquivo);
		LOGGER.info("Gerando o conteúdo do arquivo: " + nomeCompletoDoArquivo);

		try {
			LOGGER.log(Level.INFO,
					"Gera o arquivo de consumo do condomínio " + condominio.getNome() + " do mes-ano {0}", mesAno);
			String cabecalho = "Rateio de Consumo do " + condominio.getNome() + " do MES-ANO " + mesAno + "\n";
			bw.write(cabecalho);
			bw.write("\n");

			Double valorTotalRateadoAgua = 0.0;
			Double valorTotalRateadoGas = 0.0;
			Double valorTotalUnidade = 0.0;

			for (UnidadeConsumidora unidade : consumosUnidade.keySet()) {
				bw.write(CABECALHO_UNIDADE);
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
				bw.write("TOTAL;;;;;;;;;" + NumeroHelper.formataNumeroTo2Decimais(valorTotalUnidade) + "\n");
				bw.write(LINHA_EM_BRANCO);
			}

			Double valorTotalFaturadoAgua = consumoCondominio.getValorTotalFaturado();
			Double valorTotalCondominioAgua = valorTotalFaturadoAgua - valorTotalRateadoAgua;

			/* Gerando o total do valor rateado de água pelas unidades */
			bw.write(LINHA_EM_BRANCO);
			bw.write("TOTAL DA AGUA AREA PRIVADA;;;;;;;;;"
					+ NumeroHelper.formataNumeroTo2Decimais(valorTotalRateadoAgua));
			bw.write(LINHA_EM_BRANCO);
			bw.write("TOTAL DA AGUA AREA COMUM;;;;;;;;;"
					+ NumeroHelper.formataNumeroTo2Decimais(valorTotalCondominioAgua));
			bw.write(LINHA_EM_BRANCO);
			bw.write("TOTAL DA AGUA FATURADA;;;;;;;;;" + NumeroHelper.formataNumeroTo2Decimais(valorTotalFaturadoAgua));
			bw.write(LINHA_EM_BRANCO);
			bw.write(LINHA_EM_BRANCO);

			/* Gerando o total do valor rateado de gás pelas unidades */
			bw.write(
					"TOTAL DO GAS AREA PRIVADA;;;;;;;;;" + NumeroHelper.formataNumeroTo2Decimais(valorTotalRateadoGas));
			bw.write(LINHA_EM_BRANCO);

			bw.close();
			LOGGER.log(Level.INFO, "Finalizado a geração do arquivo de consumo do condomínio " + condominio.getNome()
					+ " do mes-ano {0}", mesAno);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new NegocioException(Mensagem.CONSUMO_NAO_FOI_POSSIVEL_GERAR_ARQUIVO, mesAno);
		}

		return new File(nomeCompletoDoArquivo);
	}
}