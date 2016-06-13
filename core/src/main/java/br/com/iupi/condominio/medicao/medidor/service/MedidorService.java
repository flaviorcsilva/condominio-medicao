package br.com.iupi.condominio.medicao.medidor.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.iupi.condominio.medicao.comum.execao.Mensagem;
import br.com.iupi.condominio.medicao.comum.execao.NegocioException;
import br.com.iupi.condominio.medicao.medidor.dao.MedidorDAO;
import br.com.iupi.condominio.medicao.medidor.modelo.Medidor;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedidor;
import br.com.iupi.condominio.medicao.unidade.modelo.UnidadeConsumidora;
import br.com.iupi.condominio.medicao.unidade.service.UnidadeConsumidoraService;

@Stateless
public class MedidorService {

	@Inject
	private MedidorDAO dao;

	@Inject
	private UnidadeConsumidoraService unidadeConsumidoraService;

	public Medidor consultaMedidor(String numero) {
		return dao.consultaPorNumero(numero);
	}

	public Medidor consultaMedidor(UnidadeConsumidora unidadeConsumidora, TipoMedidor tipo) {
		Medidor medidor = dao.consultaPorUnidadeTipo(unidadeConsumidora, tipo);

		if (medidor == null) {
			throw new NegocioException(Mensagem.MEDIDOR_NAO_EXISTENTE);
		}

		return medidor;
	}

	public List<Medidor> consultaMedidores(String unidade) {
		UnidadeConsumidora unidadeCondominio = unidadeConsumidoraService.consultaUnidadeConsumidora(unidade);

		return unidadeCondominio.getMedidores();
	}
}