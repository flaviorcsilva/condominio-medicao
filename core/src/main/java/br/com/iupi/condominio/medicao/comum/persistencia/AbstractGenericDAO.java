package br.com.iupi.condominio.medicao.comum.persistencia;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Class to implement the methods of access to the database
 * 
 * @param <ENTITY>
 *            Entidade
 * @param <PK>
 *            Primary Key of the entity
 */
public abstract class AbstractGenericDAO<ENTITY extends Entidade> {

	private final String SQL_FIND_ALL = " SELECT e FROM :entity e";
	private final String SQL_RECORDS_COUNT = "SELECT COUNT(e) FROM :entity e";
	private final String PARAMETER_ENTITY = ":entity";

	protected Class<ENTITY> classEntity;

	/**
	 * Abstract method for ( injecting through the @link PersistenceContext) the
	 * EntityManager in the concrete class
	 * 
	 * @return EntityManager
	 */
	public abstract EntityManager getEntityManager();
	
	public AbstractGenericDAO(Class<ENTITY> classEntity) {
		this.classEntity = classEntity;
	}

	public ENTITY insert(ENTITY entity) {
		getEntityManager().persist(entity);

		return entity;
	}

	public List<ENTITY> insert(List<ENTITY> entities) {
		for (ENTITY entity : entities) {
			insert(entity);
		}

		return entities;
	}

	public ENTITY update(ENTITY entity) {
		getEntityManager().merge(entity);

		return entity;
	}

	public List<ENTITY> update(List<ENTITY> entities) {
		for (ENTITY entity : entities) {
			update(entity);
		}

		return entities;
	}

	public void remove(ENTITY entity) {
		getEntityManager().remove(entity);
	}

	public void remove(List<ENTITY> entities) {
		for (ENTITY entity : entities) {
			remove(entity);
		}
	}

	private TypedQuery<ENTITY> buildQueryFindAll() {
		TypedQuery<ENTITY> query = getEntityManager()
				.createQuery(SQL_FIND_ALL.replace(PARAMETER_ENTITY, classEntity.getSimpleName()), classEntity);

		return query;
	}

	public List<ENTITY> findAll() {
		return buildQueryFindAll().getResultList();
	}

	public List<ENTITY> findAll(int maxResult, int firstResult) {
		TypedQuery<ENTITY> query = buildQueryFindAll().setFirstResult(firstResult).setMaxResults(maxResult);

		return query.getResultList();
	}

	public BigDecimal getRecordsCount() {
		TypedQuery<BigDecimal> query = getEntityManager().createQuery(
				SQL_RECORDS_COUNT.replace(PARAMETER_ENTITY, classEntity.getSimpleName()), BigDecimal.class);

		if (query.getResultList().isEmpty()) {
			return new BigDecimal(0);
		}

		return query.getResultList().get(0);
	}
}