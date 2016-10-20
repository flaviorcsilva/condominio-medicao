package br.com.condominioalerta.medicao.condominio.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.condominioalerta.medicao.comum.persistencia.AbstractGenericDAO;
import br.com.condominioalerta.medicao.condominio.model.Condominio;

public class CondominioDAO extends AbstractGenericDAO<Condominio> {

	@PersistenceContext(unitName = "condominio-pu")
	private EntityManager entityManager;

	public CondominioDAO() {
		super(Condominio.class);
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Condominio consultaPorCodigo(String codigo) {
		return entityManager.find(Condominio.class, codigo);
	}
}