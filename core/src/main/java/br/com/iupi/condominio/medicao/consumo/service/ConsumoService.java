package br.com.iupi.condominio.medicao.consumo.service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.iupi.condominio.medicao.comum.execao.Mensagem;
import br.com.iupi.condominio.medicao.comum.execao.NegocioException;
import br.com.iupi.condominio.medicao.comum.helper.DataHelper;
import br.com.iupi.condominio.medicao.condominio.modelo.Condominio;
import br.com.iupi.condominio.medicao.condominio.service.CondominioService;
import br.com.iupi.condominio.medicao.consumo.arquivo.GeradorArquivoConsumo;
import br.com.iupi.condominio.medicao.consumo.dao.ConsumoCondominioDAO;
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

	public List<ConsumoUnidade> calculaConsumos(String codigoCondominio, Integer mes, Integer ano) {
		Map<UnidadeConsumidora, List<ConsumoUnidade>> consumosUnidade = new TreeMap<>();
		List<ConsumoUnidade> consumos = null;

		YearMonth mesAtual = DataHelper.converteIntegerToYearMonth(mes, ano);
		YearMonth mesAnterior = mesAtual.minusMonths(1);

		/* Cache com o valores do M3 para Água e Gás */
		Map<TipoMedicao, Double> valorM3 = criaMapaValorM3(codigoCondominio, mes, ano);

		/* Obtém todas as unidades consumidoras do condomínio */
		List<UnidadeConsumidora> unidades = unidadeConsumidoraService
				.consultaUnidadesConsumidorasPorCondominio(codigoCondominio);
		for (UnidadeConsumidora unidadeConsumidora : unidades) {

			consumos = new ArrayList<ConsumoUnidade>();
			/* Obtém as leituras atuais da unidade */
			List<Leitura> leiturasAtuais = leituraService.consultaLeituras(unidadeConsumidora, mesAtual);
			for (Leitura leituraAtual : leiturasAtuais) {
				TipoMedicao tipoMedicao = leituraAtual.getMedidor().getTipo();

				/* Obtém a leitura anterior de mesmo tipo desta unidade */
				Leitura leituraAnterior = leituraService.consultaLeitura(unidadeConsumidora, tipoMedicao, mesAnterior);

				Double precoM3 = valorM3.get(leituraAtual.getMedidor().getTipo());

				consumos.add(new ConsumoUnidade(leituraAnterior, leituraAtual, precoM3));
			}

			consumosUnidade.put(unidadeConsumidora, consumos);
		}

		Condominio condominio = condominioService.consultaCondominioPorCodigo(codigoCondominio);
		ConsumoCondominio consumoCondominio = consultaConsumoCondominio(codigoCondominio, TipoMedicao.AGUA_FRIA, mes,
				ano);

		geradorArquivoConsumo.geraArquivo(consumosUnidade, condominio, consumoCondominio, mes, ano);

		return consumos;
	}

	private ConsumoCondominio consultaConsumoCondominio(String codigoCondominio, TipoMedicao tipoMedicao, Integer mes,
			Integer ano) {
		YearMonth mesAtual = DataHelper.converteIntegerToYearMonth(mes, ano);

		/* Formata o mes/ano para pesquisa */
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
		String anoMes = mesAtual.format(formatter);

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

		ConsumoCondominio consumoCondominio = dao.consultaPorCondominioTipoMedicaoAnoMes(codigoCondominio, tipo,
				anoMes);

		if (consumoCondominio == null || consumoCondominio.getValorM3() == null
				|| consumoCondominio.getValorM3() == 0.0) {
			throw new NegocioException(Mensagem.CONSUMO_NAO_EXISTENTE);
		}

		return consumoCondominio;
	}

	private Map<TipoMedicao, Double> criaMapaValorM3(String codigoCondominio, Integer mes, Integer ano) {
		Map<TipoMedicao, Double> map = new HashMap<>();

		ConsumoCondominio consumoGeralAgua = this.consultaConsumoCondominio(codigoCondominio, TipoMedicao.AGUA_FRIA,
				mes, ano);
		ConsumoCondominio consumoGeralGas = this.consultaConsumoCondominio(codigoCondominio, TipoMedicao.GAS, mes, ano);

		map.put(TipoMedicao.AGUA_FRIA, consumoGeralAgua.getValorM3());
		map.put(TipoMedicao.AGUA_QUENTE, consumoGeralAgua.getValorM3());
		map.put(TipoMedicao.GAS, consumoGeralGas.getValorM3());

		return map;
	}
}