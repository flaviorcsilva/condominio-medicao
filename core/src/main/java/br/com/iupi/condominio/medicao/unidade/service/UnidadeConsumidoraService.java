package br.com.iupi.condominio.medicao.unidade.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.iupi.condominio.medicao.comum.execao.Mensagem;
import br.com.iupi.condominio.medicao.comum.execao.NegocioException;
import br.com.iupi.condominio.medicao.unidade.dao.UnidadeConsumidoraDAO;
import br.com.iupi.condominio.medicao.unidade.modelo.UnidadeConsumidora;

@Stateless
public class UnidadeConsumidoraService {

	@Inject
	private UnidadeConsumidoraDAO dao;

	public UnidadeConsumidora consultaUnidadeConsumidora(String unidade) {
		UnidadeConsumidora uc = dao.consultaPorUnidadeCondominio(unidade, "privilege");

		if (uc == null) {
			throw new NegocioException(Mensagem.UNIDADE_NAO_EXISTENTE);
		}

		return uc;
	}
}