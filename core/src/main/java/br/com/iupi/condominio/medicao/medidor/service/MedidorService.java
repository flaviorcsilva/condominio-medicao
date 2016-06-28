package br.com.iupi.condominio.medicao.medidor.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.iupi.condominio.medicao.comum.execao.Mensagem;
import br.com.iupi.condominio.medicao.comum.execao.NegocioException;
import br.com.iupi.condominio.medicao.medidor.dao.MedidorDAO;
import br.com.iupi.condominio.medicao.medidor.modelo.Medidor;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedicao;
import br.com.iupi.condominio.medicao.unidade.modelo.UnidadeConsumidora;
import br.com.iupi.condominio.medicao.unidade.service.UnidadeConsumidoraService;

@Stateless
public class MedidorService {

	@Inject
	private MedidorDAO dao;

	@Inject
	private UnidadeConsumidoraService unidadeConsumidoraService;

	public Medidor consultaMedidor(String numero) {
		return dao.consultaPorNumero(numero);
	}

	public Medidor consultaMedidor(UnidadeConsumidora unidadeConsumidora, TipoMedicao tipo) {
		Medidor medidor = dao.consultaPorUnidadeTipo(unidadeConsumidora, tipo);

		if (medidor == null) {
			throw new NegocioException(Mensagem.MEDIDOR_NAO_EXISTENTE);
		}

		return medidor;
	}

	public List<Medidor> consultaMedidores(String condominio, String unidade) {
		UnidadeConsumidora unidadeConsumidora = unidadeConsumidoraService.consultaUnidadeConsumidora(condominio,
				unidade);

		List<Medidor> medidores = dao.consultaPorUnidade(unidadeConsumidora);

		if (medidores.isEmpty()) {
			throw new NegocioException(Mensagem.UNIDADE_NAO_EXISTE_MEDIDORES);
		}

		return medidores;
	}
}