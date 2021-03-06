package br.com.condominioalerta.medicao.unidade.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.condominioalerta.medicao.comum.persistencia.AbstractGenericDAO;
import br.com.condominioalerta.medicao.unidade.model.UnidadeConsumidora;

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
		sql.append("  AND uc.condominio.codigo = :condominio ");

		Query query = getEntityManager().createQuery(sql.toString());

		query.setParameter("unidade", unidade);
		query.setParameter("condominio", condominio);

		return getSingleResult(query);
	}

	@SuppressWarnings("unchecked")
	public List<UnidadeConsumidora> consultaPorCondominio(String condominio) {
		StringBuilder sql = new StringBuilder();

		sql.append(" FROM " + UnidadeConsumidora.class.getName() + " as uc ");
		sql.append("WHERE uc.condominio.codigo = :condominio ");
		sql.append("ORDER BY uc.unidade ");

		Query query = getEntityManager().createQuery(sql.toString());

		query.setParameter("condominio", condominio);

		return query.getResultList();
	}

	public List<String> consultaListaPorCondominio(String condominio) {
		String sql = "SELECT cd_unidade FROM unidade WHERE cd_condominio = '" + condominio + "' ORDER BY cd_unidade";

		@SuppressWarnings("unchecked")
		List<Object> objetos = (List<Object>) getEntityManager().createNativeQuery(sql).getResultList();

		return buildResult(objetos);
	}

	private List<String> buildResult(List<Object> objects) {
		List<String> list = new ArrayList<String>();

		for (Object object : objects) {
			Object data = (Object) object;

			list.add((String) data);
		}

		return list;
	}
}