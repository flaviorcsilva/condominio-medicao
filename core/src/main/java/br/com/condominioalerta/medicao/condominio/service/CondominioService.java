package br.com.iupi.condominio.medicao.condominio.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.iupi.condominio.medicao.comum.execao.Mensagem;
import br.com.iupi.condominio.medicao.comum.execao.NegocioException;
import br.com.iupi.condominio.medicao.condominio.dao.CondominioDAO;
import br.com.iupi.condominio.medicao.condominio.modelo.Condominio;

@Stateless
public class CondominioService {

	@Inject
	private CondominioDAO dao;

	public Condominio consultaCondominioPorCodigo(String codigo) {
		Condominio condominio = dao.consultaPorCodigo(codigo);

		if (condominio == null) {
			throw new NegocioException(Mensagem.CONDOMINIO_NAO_EXISTENTE);
		}

		return condominio;
	}
}