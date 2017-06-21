package br.com.condominioalerta.medicao.consumo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.condominioalerta.medicao.comum.persistencia.AbstractGenericDAO;
import br.com.condominioalerta.medicao.consumo.model.ConsumoCondominio;
import br.com.condominioalerta.medicao.medidor.model.TipoMedicao;

public class ConsumoCondominioDAO extends AbstractGenericDAO<ConsumoCondominio> {

	@PersistenceContext(unitName = "condominio-pu")
	private EntityManager entityManager;

	public ConsumoCondominioDAO() {
		super(ConsumoCondominio.class);
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public ConsumoCondominio consultaPorId(Long id) {
		return entityManager.find(ConsumoCondominio.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<ConsumoCondominio> consultaPorCondominio(String condominio) {
		StringBuilder sql = new StringBuilder();

		sql.append(" FROM " + ConsumoCondominio.class.getName() + " as cc ");
		sql.append("WHERE cc.condominio = :condominio ");
		
		Query query = getEntityManager().createQuery(sql.toString());
		query.setParameter("condominio", condominio);
		
		return query.getResultList();
	}

	public ConsumoCondominio consultaPorCondominioTipoMedicaoAnoMes(String condominio, TipoMedicao tipoMedicao,
			String anoMes) {
		StringBuilder sql = new StringBuilder();

		sql.append(" FROM " + ConsumoCondominio.class.getName() + " as cc ");
		sql.append("WHERE cc.condominio = :condominio ");
		sql.append("  AND cc.anoMes = :anoMes ");
		sql.append("  AND cc.tipoMedicao = :tipoMedicao ");

		Query query = getEntityManager().createQuery(sql.toString());

		query.setParameter("condominio", condominio);
		query.setParameter("anoMes", anoMes);
		query.setParameter("tipoMedicao", tipoMedicao);

		return getSingleResult(query);
	}
}