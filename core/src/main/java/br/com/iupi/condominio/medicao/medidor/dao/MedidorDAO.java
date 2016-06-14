package br.com.iupi.condominio.medicao.medidor.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.iupi.condominio.medicao.comum.persistencia.AbstractGenericDAO;
import br.com.iupi.condominio.medicao.medidor.modelo.Medidor;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedicao;
import br.com.iupi.condominio.medicao.unidade.modelo.UnidadeConsumidora;

@Stateless
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
}