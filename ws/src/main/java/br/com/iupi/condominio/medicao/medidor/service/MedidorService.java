package br.com.iupi.condominio.medicao.medidor.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Singleton;

import br.com.iupi.condominio.medicao.medidor.modelo.Medidor;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedidor;
import br.com.iupi.condominio.medicao.unidade.service.UnidadeService;

@Singleton
public class MedidorService {

	private static final HashMap<String, Medidor> medidores = new HashMap<String, Medidor>();
	
	static {
		medidores.put("AGF-212", new Medidor("AGF-212", TipoMedidor.AGUA_FRIA, UnidadeService.consultaUnidade("212"))); //
		medidores.put("AGQ-212", new Medidor("AGQ-212", TipoMedidor.AGUA_QUENTE, UnidadeService.consultaUnidade("212"))); //
		medidores.put("GAS-212", new Medidor("GAS-212", TipoMedidor.GAS, UnidadeService.consultaUnidade("212"))); //
		medidores.put("AGF-213", new Medidor("AGF-213", TipoMedidor.AGUA_FRIA, UnidadeService.consultaUnidade("213"))); //
		medidores.put("AGQ-213", new Medidor("AGQ-213", TipoMedidor.AGUA_QUENTE, UnidadeService.consultaUnidade("213"))); //
		medidores.put("GAS-213", new Medidor("GAS-213", TipoMedidor.GAS, UnidadeService.consultaUnidade("213")));
		medidores.put("AGF-GERAL", new Medidor("AGF-GERAL", TipoMedidor.AGUA_FRIA, UnidadeService.consultaUnidade("geral"))); //
		medidores.put("GAS-GERAL", new Medidor("GAS-GERAL", TipoMedidor.GAS, UnidadeService.consultaUnidade("geral"))); //
	}

	public MedidorService() {
	}

	public static Medidor consultaMedidor(String numero) {
		return medidores.get(numero);
	}

	public static List<Medidor> consultaMedidores(String unidade) {
		List<Medidor> lista = new ArrayList<Medidor>();

		for (Medidor medidor : medidores.values()) {
			if (medidor != null && medidor.getUnidade() != null && medidor.getUnidade().getUnidade().equals(unidade)) {
				lista.add(medidor);
			}
		}

		return lista;
	}

	public static Medidor consultaMedidor(String unidade, TipoMedidor tipoMedidor) {
		for (Medidor medidor : medidores.values()) {
			if (medidor != null && medidor.getUnidade() != null && medidor.getUnidade().getUnidade().equals(unidade)
					&& medidor.getTipo().equals(tipoMedidor)) {
				return medidor;
			}
		}

		return null;
	}
}