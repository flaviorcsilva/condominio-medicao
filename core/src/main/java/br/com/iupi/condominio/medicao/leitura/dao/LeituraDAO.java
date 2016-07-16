package br.com.iupi.condominio.medicao.leitura.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.iupi.condominio.medicao.comum.persistencia.AbstractGenericDAO;
import br.com.iupi.condominio.medicao.leitura.modelo.Leitura;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedicao;
import br.com.iupi.condominio.medicao.unidade.modelo.UnidadeConsumidora;

public class LeituraDAO extends AbstractGenericDAO<Leitura> {

	@PersistenceContext(unitName = "condominio-pu")
	private EntityManager entityManager;

	public LeituraDAO() {
		super(Leitura.class);
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Leitura consultaPorId(Long id) {
		return entityManager.find(Leitura.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Leitura> consultaPorUnidadePeriodo(UnidadeConsumidora unidadeConsumidora, Date inicioMes,
			Date finalMes) {
		StringBuilder sql = new StringBuilder();

		sql.append(" FROM " + Leitura.class.getName() + " as leitura ");
		sql.append("WHERE leitura.medidor.unidadeConsumidora = :unidadeConsumidora ");
		sql.append("  AND leitura.dataLeitura BETWEEN :inicioMes AND :finalMes ");

		Query query = entityManager.createQuery(sql.toString());

		query.setParameter("unidadeConsumidora", unidadeConsumidora);
		query.setParameter("inicioMes", inicioMes);
		query.setParameter("finalMes", finalMes);

		return query.getResultList();
	}

	public Leitura consultaPorUnidadeTipoPeriodo(UnidadeConsumidora unidadeConsumidora, TipoMedicao tipoMedicao,
			Date inicioMes, Date finalMes) {
		StringBuilder sql = new StringBuilder();

		sql.append(" FROM " + Leitura.class.getName() + " as leitura ");
		sql.append("WHERE leitura.medidor.unidadeConsumidora = :unidadeConsumidora ");
		sql.append("  AND leitura.medidor.tipo = :tipo ");
		sql.append("  AND leitura.dataLeitura BETWEEN :inicioMes AND :finalMes ");

		Query query = entityManager.createQuery(sql.toString());

		query.setParameter("unidadeConsumidora", unidadeConsumidora);
		query.setParameter("tipo", tipoMedicao);
		query.setParameter("inicioMes", inicioMes);
		query.setParameter("finalMes", finalMes);

		return getSingleResult(query);
	}
}