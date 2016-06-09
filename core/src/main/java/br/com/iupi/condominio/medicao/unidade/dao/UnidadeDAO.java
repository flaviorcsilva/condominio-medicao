package br.com.iupi.condominio.medicao.unidade.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.iupi.condominio.medicao.comum.persistencia.AbstractGenericDAO;
import br.com.iupi.condominio.medicao.unidade.modelo.Unidade;

@Stateless
public class UnidadeDAO extends AbstractGenericDAO<Unidade> {

	@PersistenceContext(unitName = "condominio-pu")
	private EntityManager entityManager;

	public UnidadeDAO() {
		super(Unidade.class);
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Unidade consultaPorId(Long id) {
		return entityManager.find(Unidade.class, id);
	}

	public Unidade consultaPorUnidadeCondominio(String unidade, String condominio) {
		String sql = "FROM " + Unidade.class.getName()
				+ " as unidade WHERE unidade.unidade = :unidade AND unidade.condominio = :condominio";

		Query query = getEntityManager().createQuery(sql);
		query.setParameter("unidade", unidade);
		query.setParameter("condominio", condominio);

		return (Unidade) query.getSingleResult();
	}
}