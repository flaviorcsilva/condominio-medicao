package br.com.condominioalerta.medicao.usuario.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.condominioalerta.medicao.comum.persistencia.AbstractGenericDAO;
import br.com.condominioalerta.medicao.usuario.model.Usuario;

public class UsuarioDAO extends AbstractGenericDAO<Usuario> {

	@PersistenceContext(unitName = "condominio-pu")
	private EntityManager entityManager;

	public UsuarioDAO() {
		super(Usuario.class);
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Usuario consultaPorLogin(String login) {
		StringBuilder sql = new StringBuilder();

		sql.append(" FROM " + Usuario.class.getName() + " as usuario ");
		sql.append("WHERE usuario.login = :login ");

		Query query = getEntityManager().createQuery(sql.toString());

		query.setParameter("login", login);

		return getSingleResult(query);
	}
}