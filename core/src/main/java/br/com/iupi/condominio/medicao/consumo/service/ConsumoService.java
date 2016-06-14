package br.com.iupi.condominio.medicao.consumo.service;

import java.time.YearMonth;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.iupi.condominio.medicao.comum.helper.DataHelper;
import br.com.iupi.condominio.medicao.consumo.modelo.Consumo;
import br.com.iupi.condominio.medicao.leitura.modelo.Leitura;
import br.com.iupi.condominio.medicao.leitura.service.LeituraService;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedicao;
import br.com.iupi.condominio.medicao.unidade.modelo.UnidadeConsumidora;

@Stateless
public class ConsumoService {

	@Inject
	private LeituraService leituraService;
	
	public Consumo consultaConsumo(UnidadeConsumidora unidadeConsumidora, TipoMedicao tipoMedicao, Integer mes, Integer ano) {
		Double valorM3 = 0.0; // TODO
		
		if (tipoMedicao.equals(TipoMedicao.GAS)) {
			valorM3 = 3.20;
		} else {
			valorM3 = 5.95649695;
		}

		YearMonth mesAtual = DataHelper.converteIntegerToYearMonth(mes, ano);
		YearMonth mesAnterior = mesAtual.minusMonths(1);

		Leitura leituraAnterior = leituraService.consultaLeitura(mesAnterior, unidadeConsumidora, tipoMedicao);
		Leitura leituraAtual = leituraService.consultaLeitura(mesAtual, unidadeConsumidora, tipoMedicao);

		return new Consumo(leituraAnterior, leituraAtual, valorM3);
	}
}