package br.com.iupi.condominio.medicao.unidade.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.iupi.condominio.medicao.comum.persistencia.AbstractGenericDAO;
import br.com.iupi.condominio.medicao.unidade.modelo.UnidadeConsumidora;

@Stateless
public class UnidadeConsumidoraDAO extends AbstractGenericDAO<UnidadeConsumidora> {

	@PersistenceContext(unitName = "condominio-pu")
	private EntityManager entityManager;

	public UnidadeConsumidoraDAO() {
		super(UnidadeConsumidora.class);
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public UnidadeConsumidora consultaPorId(Long id) {
		return entityManager.find(UnidadeConsumidora.class, id);
	}

	public UnidadeConsumidora consultaPorUnidadeCondominio(String condominio, String unidade) {
		StringBuilder sql = new StringBuilder();
		sql.append(" FROM " + UnidadeConsumidora.class.getName() + " as uc ");
		sql.append("WHERE uc.unidade = :unidade ");
		sql.append("  AND uc.condominio = :condominio ");

		Query query = getEntityManager().createQuery(sql.toString());
		query.setParameter("unidade", unidade);
		query.setParameter("condominio", condominio);

		return getSingleResult(query);
	}
}