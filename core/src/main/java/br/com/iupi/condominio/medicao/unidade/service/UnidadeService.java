package br.com.iupi.condominio.medicao.unidade.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.iupi.condominio.medicao.comum.execao.Mensagem;
import br.com.iupi.condominio.medicao.comum.execao.NegocioException;
import br.com.iupi.condominio.medicao.unidade.dao.UnidadeDAO;
import br.com.iupi.condominio.medicao.unidade.modelo.Unidade;

@Stateless
public class UnidadeService {

	@Inject
	private UnidadeDAO dao;

	public Unidade consultaUnidade(String unidade) {
		Unidade unidadeCondominio = dao.consultaPorUnidadeCondominio(unidade, "privilege");

		if (unidadeCondominio == null) {
			throw new NegocioException(Mensagem.UNIDADE_NAO_EXISTENTE);
		}

		return unidadeCondominio;
	}

	public boolean existeUnidade(String unidade) {
		Unidade u = consultaUnidade(unidade);

		if (u == null) {
			return false;
		}

		return true;
	}
}