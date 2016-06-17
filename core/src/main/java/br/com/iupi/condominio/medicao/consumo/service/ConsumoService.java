package br.com.iupi.condominio.medicao.consumo.service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.iupi.condominio.medicao.comum.helper.DataHelper;
import br.com.iupi.condominio.medicao.consumo.dao.ConsumoCondominioDAO;
import br.com.iupi.condominio.medicao.consumo.modelo.ConsumoCondominio;
import br.com.iupi.condominio.medicao.consumo.modelo.ConsumoUnidade;
import br.com.iupi.condominio.medicao.leitura.modelo.Leitura;
import br.com.iupi.condominio.medicao.leitura.service.LeituraService;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedicao;
import br.com.iupi.condominio.medicao.unidade.modelo.UnidadeConsumidora;

@Stateless
public class ConsumoService {

	@Inject
	private LeituraService leituraService;

	@Inject
	private ConsumoCondominioDAO dao;

	public ConsumoUnidade calculaConsumo(UnidadeConsumidora unidadeConsumidora, TipoMedicao tipoMedicao, Integer mes,
			Integer ano) {
		YearMonth mesAtual = DataHelper.converteIntegerToYearMonth(mes, ano);
		YearMonth mesAnterior = mesAtual.minusMonths(1);

		/* Obtém as leituras atual e anterior */
		Leitura leituraAnterior = leituraService.consultaLeitura(unidadeConsumidora, tipoMedicao, mesAnterior);
		Leitura leituraAtual = leituraService.consultaLeitura(unidadeConsumidora, tipoMedicao, mesAtual);

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

		ConsumoCondominio consumoCondominio = dao
				.consultaPorCondominioTipoMedicaoMesAno(unidadeConsumidora.getCondominio().getCodigo(), tipo, mesAno);

		return new ConsumoUnidade(leituraAnterior, leituraAtual, consumoCondominio.getValorM3());
	}
}