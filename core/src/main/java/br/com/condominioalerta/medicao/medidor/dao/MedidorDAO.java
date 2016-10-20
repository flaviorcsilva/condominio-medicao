package br.com.condominioalerta.medicao.medidor.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.condominioalerta.medicao.comum.persistencia.AbstractGenericDAO;
import br.com.condominioalerta.medicao.medidor.model.Medidor;
import br.com.condominioalerta.medicao.medidor.model.TipoMedicao;
import br.com.condominioalerta.medicao.unidade.model.UnidadeConsumidora;

public class MedidorDAO extends AbstractGenericDAO<Medidor> {

	@PersistenceContext(unitName = "condominio-pu")
	private EntityManager entityManager;

	public MedidorDAO() {
		super(Medidor.class);
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Medidor consultaPorNumero(String numero) {
		return entityManager.find(Medidor.class, numero);
	}

	public Medidor consultaPorUnidadeTipo(UnidadeConsumidora unidadeConsumidora, TipoMedicao tipoMedicao) {
		StringBuilder sql = new StringBuilder();

		sql.append(" FROM " + Medidor.class.getName() + " as medidor ");
		sql.append("WHERE medidor.unidadeConsumidora = :unidadeConsumidora ");
		sql.append("  AND medidor.tipo = :tipoMedicao");

		Query query = entityManager.createQuery(sql.toString());

		query.setParameter("unidadeConsumidora", unidadeConsumidora);
		query.setParameter("tipoMedicao", tipoMedicao);

		return getSingleResult(query);
	}

	@SuppressWarnings("unchecked")
	public List<Medidor> consultaPorUnidade(UnidadeConsumidora unidadeConsumidora) {
		StringBuilder sql = new StringBuilder();

		sql.append(" FROM " + Medidor.class.getName() + " as medidor ");
		sql.append("WHERE medidor.unidadeConsumidora = :unidadeConsumidora ");

		Query query = entityManager.createQuery(sql.toString());

		query.setParameter("unidadeConsumidora", unidadeConsumidora);

		return query.getResultList();
	}
}