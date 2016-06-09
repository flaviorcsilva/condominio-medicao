package br.com.iupi.condominio.medicao.leitura.dao;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.iupi.condominio.medicao.comum.persistencia.AbstractGenericDAO;
import br.com.iupi.condominio.medicao.leitura.modelo.Leitura;
import br.com.iupi.condominio.medicao.unidade.modelo.Unidade;

@Stateless
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
	public List<Leitura> consultaPorUnidadeMesAno(Unidade unidadeCondominio, LocalDate inicioMes, LocalDate finalMes) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("FROM " + Leitura.class.getName() + " as leitura ");
		sql.append("WHERE leitura.medidor.unidade = :unidade ");
		sql.append("AND leitura.dataLeitura BETWEEN :inicioMes AND :finalMes");

		Query query = entityManager.createQuery(sql.toString());

		query.setParameter("unidade", unidadeCondominio);
		query.setParameter("inicioMes", inicioMes);
		query.setParameter("finalMes", finalMes);

		return query.getResultList();
	}
}