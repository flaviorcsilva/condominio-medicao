package br.com.iupi.condominio.medicao.medidor.dao;

import java.util.Map;

import br.com.iupi.condominio.medicao.comum.persistencia.GenericMapDAO;
import br.com.iupi.condominio.medicao.medidor.modelo.Medidor;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedidor;
import br.com.iupi.condominio.medicao.unidade.modelo.Unidade;

public class MedidorDAO extends GenericMapDAO<String, Medidor> {

	private Map<String, Medidor> medidores = hashMap;

	{
		Unidade unidade = new Unidade();
		unidade.setCondominio("Privilege Noroeste");
		unidade.setUnidade("212");

		Medidor medidorAguaFria = new Medidor();
		medidorAguaFria.setUnidade(unidade);
		medidorAguaFria.setTipo(TipoMedidor.AGUA_FRIA);
		medidorAguaFria.setNumero("A14E012523");

		insere(medidorAguaFria);

		Medidor medidorAguaQuente = new Medidor();
		medidorAguaQuente.setUnidade(unidade);
		medidorAguaQuente.setTipo(TipoMedidor.AGUA_QUENTE);
		medidorAguaQuente.setNumero("A13F011417");

		insere(medidorAguaQuente);

		Medidor medidorGas = new Medidor();
		medidorGas.setUnidade(unidade);
		medidorGas.setTipo(TipoMedidor.GAS);
		medidorGas.setNumero("B14D0001552D");

		insere(medidorGas);
	}

	public Medidor consultaPorTipo(TipoMedidor tipo) {
		for (Medidor medidor : medidores.values()) {
			if (medidor.getTipo().equals(tipo)) {
				return medidor;
			}
		}

		return null;
	}
}
