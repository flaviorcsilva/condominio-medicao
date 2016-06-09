package br.com.iupi.condominio.medicao.medidor.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.iupi.condominio.medicao.comum.persistencia.AbstractGenericDAO;
import br.com.iupi.condominio.medicao.medidor.modelo.Medidor;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedidor;
import br.com.iupi.condominio.medicao.unidade.modelo.Unidade;

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

	public Medidor consultaPorUnidadeTipo(Unidade unidade, TipoMedidor tipo) {
		String sql = "FROM " + Medidor.class.getName()
				+ " as medidor WHERE medidor.unidade = :unidade AND medidor.tipo = :tipo";

		Query query = getEntityManager().createQuery(sql);
		query.setParameter("unidade", unidade);
		query.setParameter("tipo", tipo);

		return (Medidor) query.getSingleResult();
	}
}