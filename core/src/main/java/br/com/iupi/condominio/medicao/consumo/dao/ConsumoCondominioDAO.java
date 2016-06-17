package br.com.iupi.condominio.medicao.consumo.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
		return null;
	}
}