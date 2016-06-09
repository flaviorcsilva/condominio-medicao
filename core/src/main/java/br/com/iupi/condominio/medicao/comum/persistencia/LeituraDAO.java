package br.com.iupi.condominio.medicao.comum.persistencia;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.iupi.condominio.medicao.leitura.modelo.Leitura;

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
}