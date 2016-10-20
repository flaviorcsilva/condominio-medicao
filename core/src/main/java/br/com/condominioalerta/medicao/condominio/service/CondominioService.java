package br.com.condominioalerta.medicao.condominio.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.condominioalerta.medicao.comum.execao.Mensagem;
import br.com.condominioalerta.medicao.comum.execao.NegocioException;
import br.com.condominioalerta.medicao.condominio.dao.CondominioDAO;
import br.com.condominioalerta.medicao.condominio.model.Condominio;

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