package br.com.iupi.condominio.medicao.consumo.service;

import java.text.DecimalFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.iupi.condominio.medicao.comum.execao.Mensagem;
import br.com.iupi.condominio.medicao.comum.execao.NegocioException;
import br.com.iupi.condominio.medicao.comum.helper.DataHelper;
import br.com.iupi.condominio.medicao.condominio.modelo.Condominio;
import br.com.iupi.condominio.medicao.condominio.service.CondominioService;
import br.com.iupi.condominio.medicao.consumo.arquivo.GeradorArquivoConsumo;
import br.com.iupi.condominio.medicao.consumo.dao.ConsumoCondominioDAO;
import br.com.iupi.condominio.medicao.consumo.dto.ConsumoDTO;
import br.com.iupi.condominio.medicao.consumo.modelo.ConsumoCondominio;
import br.com.iupi.condominio.medicao.consumo.modelo.ConsumoUnidade;
import br.com.iupi.condominio.medicao.leitura.modelo.Leitura;
import br.com.iupi.condominio.medicao.leitura.service.LeituraService;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedicao;
import br.com.iupi.condominio.medicao.unidade.modelo.UnidadeConsumidora;
import br.com.iupi.condominio.medicao.unidade.service.UnidadeConsumidoraService;

@Stateless
public class ConsumoService {

	@Inject
	private LeituraService leituraService;

	@Inject
	private UnidadeConsumidoraService unidadeConsumidoraService;

	@Inject
	private CondominioService condominioService;

	@Inject
	private ConsumoCondominioDAO dao;

	@Inject
	private GeradorArquivoConsumo geradorArquivoConsumo;

	private final DecimalFormat df = new DecimalFormat("###.##");
	private final String cabecalhoUnidade = "Unidade; Tipo Medicao;Data Leitura Anterior;Medicao Anterior;Data Leitura Atual;Medicao Atual;Consumo;Fator;Valor (m3);Valor A Pagar";
	private final String linhaEmBranco = ";;;;;;;;;";

	public ConsumoUnidade calculaConsumo(UnidadeConsumidora unidadeConsumidora, TipoMedicao tipoMedicao, Integer mes,
			Integer ano) {
		ConsumoCondominio consumoCondominio = this
				.consultaConsumoCondominio(unidadeConsumidora.getCondominio().getCodigo(), tipoMedicao, mes, ano);

		return this.calculaConsumo(unidadeConsumidora, tipoMedicao, mes, ano, consumoCondominio);
	}

	public ConsumoUnidade calculaConsumo(UnidadeConsumidora unidadeConsumidora, TipoMedicao tipoMedicao, Integer mes,
			Integer ano, ConsumoCondominio consumoCondominio) {
		YearMonth mesAtual = DataHelper.converteIntegerToYearMonth(mes, ano);
		YearMonth mesAnterior = mesAtual.minusMonths(1);

		/* Obtém as leituras atual e anterior */
		Leitura leituraAnterior = leituraService.consultaLeitura(unidadeConsumidora, tipoMedicao, mesAnterior);
		Leitura leituraAtual = leituraService.consultaLeitura(unidadeConsumidora, tipoMedicao, mesAtual);

		return new ConsumoUnidade(leituraAnterior, leituraAtual, consumoCondominio.getValorM3());
	}

	public List<String> geraArquivoConsumo(String condominio, Integer mes, Integer ano) {
		List<String> consumos = new ArrayList<String>();

		YearMonth mesAtual = DataHelper.converteIntegerToYearMonth(mes, ano);
		YearMonth mesAnterior = mesAtual.minusMonths(1);

		/* Cache com o valores do M3 para Água e Gás */
		Map<TipoMedicao, Double> valorM3 = criaMapaValorM3(condominio, mes, ano);

		/* Totalizador do Valor de Rateio */
		Double totalValorRateado = 0.0;

		/* Obtém todas as unidades consumidoras do condomínio */
		List<UnidadeConsumidora> unidades = unidadeConsumidoraService
				.consultaUnidadesConsumidorasPorCondominio(condominio);
		for (UnidadeConsumidora unidadeConsumidora : unidades) {

			/* Adiciona o cabeçalho de coluna */
			consumos.add(cabecalhoUnidade);

			/* Totalizador do Consumo da Unidade */
			Double totalAPagarUnidade = 0.0;

			/* Obtém as leituras atuais da unidade */
			List<Leitura> leiturasAtuais = leituraService.consultaLeituras(unidadeConsumidora, mesAtual);
			for (Leitura leituraAtual : leiturasAtuais) {
				TipoMedicao tipoMedicao = leituraAtual.getMedidor().getTipo();

				/* Obtém a leitura anterior de mesmo tipo desta unidade */
				Leitura leituraAnterior = leituraService.consultaLeitura(unidadeConsumidora, tipoMedicao, mesAnterior);

				Double precoM3 = valorM3.get(leituraAtual.getMedidor().getTipo());

				ConsumoUnidade consumoUnidade = new ConsumoUnidade(leituraAnterior, leituraAtual, precoM3);
				totalAPagarUnidade = totalAPagarUnidade + consumoUnidade.getValor();
				totalValorRateado = totalValorRateado + consumoUnidade.getValor();

				consumos.add(new ConsumoDTO(consumoUnidade).toString());
			}

			/* Adiciona o rodápe de totalização da unidade */
			String rodapeTotalUnidade = "TOTAL;;;;;;;;;" + df.format(totalAPagarUnidade);
			consumos.add(rodapeTotalUnidade);
			consumos.add(linhaEmBranco);
		}

		/* Gerando o total do valor rateado pelas unidades */
		String rodapeTotalRateado = "TOTAL;;;;;;;;;" + df.format(totalValorRateado);
		consumos.add(rodapeTotalRateado);

		Condominio c = condominioService.consultaCondominioPorCodigo(condominio);
		geradorArquivoConsumo.geraArquivo(c, consumos, mes, ano);

		return consumos;
	}

	private ConsumoCondominio consultaConsumoCondominio(String condominio, TipoMedicao tipoMedicao, Integer mes,
			Integer ano) {
		YearMonth mesAtual = DataHelper.converteIntegerToYearMonth(mes, ano);

		/* Formata o mes/ano para pesquisa */
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
		String mesAno = mesAtual.format(formatter);

		/*
		 * Caso a medição seja do tipo Água Quente, deve obter o consumo
		 * faturado da água fria pela concessionária
		 */
		TipoMedicao tipo = null;
		if (tipoMedicao.equals(TipoMedicao.AGUA_QUENTE)) {
			tipo = TipoMedicao.AGUA_FRIA;
		} else {
			tipo = tipoMedicao;
		}

		ConsumoCondominio consumoCondominio = dao.consultaPorCondominioTipoMedicaoMesAno(condominio, tipo, mesAno);

		if (consumoCondominio == null || consumoCondominio.getValorM3() == null
				|| consumoCondominio.getValorM3() == 0.0) {
			throw new NegocioException(Mensagem.CONSUMO_NAO_EXISTENTE);
		}

		return consumoCondominio;
	}

	private Map<TipoMedicao, Double> criaMapaValorM3(String condominio, Integer mes, Integer ano) {
		Map<TipoMedicao, Double> map = new HashMap<>();

		ConsumoCondominio consumoGeralAgua = this.consultaConsumoCondominio(condominio, TipoMedicao.AGUA_FRIA, mes,
				ano);
		ConsumoCondominio consumoGeralGas = this.consultaConsumoCondominio(condominio, TipoMedicao.GAS, mes, ano);

		map.put(TipoMedicao.AGUA_FRIA, consumoGeralAgua.getValorM3());
		map.put(TipoMedicao.AGUA_QUENTE, consumoGeralAgua.getValorM3());
		map.put(TipoMedicao.GAS, consumoGeralGas.getValorM3());

		return map;
	}
}