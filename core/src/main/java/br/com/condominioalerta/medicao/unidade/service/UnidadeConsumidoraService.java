package br.com.condominioalerta.medicao.unidade.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.condominioalerta.medicao.comum.execao.Mensagem;
import br.com.condominioalerta.medicao.comum.execao.NegocioException;
import br.com.condominioalerta.medicao.unidade.dao.UnidadeConsumidoraDAO;
import br.com.condominioalerta.medicao.unidade.model.UnidadeConsumidora;

@Stateless
public class UnidadeConsumidoraService {

	@Inject
	private UnidadeConsumidoraDAO dao;

	public UnidadeConsumidora consultaUnidadeConsumidora(String condominio, String unidade) {
		UnidadeConsumidora uc = dao.consultaPorUnidadeCondominio(condominio, unidade);

		if (uc == null) {
			throw new NegocioException(Mensagem.UNIDADE_NAO_EXISTENTE);
		}

		return uc;
	}

	public List<UnidadeConsumidora> consultaUnidadesConsumidorasPorCondominio(String condominio) {
		List<UnidadeConsumidora> unidades = dao.consultaPorCondominio(condominio);

		if (unidades.isEmpty()) {
			throw new NegocioException(Mensagem.UNIDADE_NAO_EXISTE_UNIDADES);
		}

		return unidades;
	}

	public List<String> consultaListaDeUnidadesPorCondominio(String condominio) {
		List<String> unidades = dao.consultaListaPorCondominio(condominio);

		if (unidades.isEmpty()) {
			throw new NegocioException(Mensagem.UNIDADE_NAO_EXISTE_UNIDADES);
		}

		return unidades;
	}
}