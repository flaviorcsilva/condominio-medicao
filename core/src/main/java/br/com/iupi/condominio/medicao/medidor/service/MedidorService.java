package br.com.iupi.condominio.medicao.medidor.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.iupi.condominio.medicao.comum.execao.Mensagem;
import br.com.iupi.condominio.medicao.comum.execao.NegocioException;
import br.com.iupi.condominio.medicao.medidor.dao.MedidorDAO;
import br.com.iupi.condominio.medicao.medidor.modelo.Medidor;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedidor;
import br.com.iupi.condominio.medicao.unidade.modelo.Unidade;

@Stateless
public class MedidorService {

	@Inject
	private MedidorDAO dao;

	public Medidor consultaMedidor(String numero) {
		return dao.consultaPorNumero(numero);
	}

	public Medidor consultaMedidor(Unidade unidade, TipoMedidor tipo) {
		Medidor medidor = dao.consultaPorUnidadeTipo(unidade, tipo);

		if (medidor == null) {
			throw new NegocioException(Mensagem.MEDIDOR_NAO_EXISTENTE);
		}

		return medidor;
	}

	/*
	 * public static List<Medidor> consultaMedidores(String unidade) {
	 * List<Medidor> lista = new ArrayList<Medidor>();
	 * 
	 * for (Medidor medidor : medidores.values()) { if (medidor != null &&
	 * medidor.getUnidade() != null &&
	 * medidor.getUnidade().getUnidade().equals(unidade)) { lista.add(medidor);
	 * } }
	 * 
	 * return lista; }
	 * 
	 */
}