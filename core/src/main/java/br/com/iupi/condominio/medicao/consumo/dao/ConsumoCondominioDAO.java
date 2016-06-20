package br.com.iupi.condominio.medicao.consumo.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.iupi.condominio.medicao.comum.persistencia.AbstractGenericDAO;
import br.com.iupi.condominio.medicao.consumo.modelo.ConsumoCondominio;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedicao;

@Stateless
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

	public ConsumoCondominio consultaPorCondominioTipoMedicaoMesAno(String condominio, TipoMedicao tipoMedicao,
			String mesAno) {
		StringBuilder sql = new StringBuilder();
		sql.append(" FROM " + ConsumoCondominio.class.getName() + " as cc ");
		sql.append("WHERE cc.condominio = :condominio ");
		sql.append("  AND cc.mesAno = :mesAno ");
		sql.append("  AND cc.tipoMedicao = :tipoMedicao ");

		Query query = getEntityManager().createQuery(sql.toString());
		query.setParameter("condominio", condominio);
		query.setParameter("mesAno", mesAno);
		query.setParameter("tipoMedicao", tipoMedicao);

		return getSingleResult(query);
	}
}