package br.com.iupi.condominio.medicao.consumo.service;

import java.time.YearMonth;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.iupi.condominio.medicao.comum.helper.DataHelper;
import br.com.iupi.condominio.medicao.consumo.modelo.Consumo;
import br.com.iupi.condominio.medicao.leitura.modelo.Leitura;
import br.com.iupi.condominio.medicao.leitura.service.LeituraService;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedidor;
import br.com.iupi.condominio.medicao.unidade.modelo.UnidadeConsumidora;

@Stateless
public class ConsumoService {

	@Inject
	private LeituraService leituraService;
	
	public Consumo consultaConsumo(UnidadeConsumidora unidadeConsumidora, TipoMedidor tipoLeitura, Integer mes, Integer ano) {
		Double valorM3 = 3.89; // TODO

		YearMonth mesAtual = DataHelper.converteIntegerToYearMonth(mes, ano);
		YearMonth mesAnterior = mesAtual.minusMonths(1);

		Leitura leituraAnterior = leituraService.consultaLeitura(mesAnterior, unidadeConsumidora, tipoLeitura);
		Leitura leituraAtual = leituraService.consultaLeitura(mesAtual, unidadeConsumidora, tipoLeitura);

		return new Consumo(leituraAnterior, leituraAtual, valorM3);
	}
}