package br.com.iupi.condominio.medicao.leitura.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.iupi.condominio.medicao.comum.execao.Mensagem;
import br.com.iupi.condominio.medicao.comum.execao.NegocioException;
import br.com.iupi.condominio.medicao.comum.helper.DataHelper;
import br.com.iupi.condominio.medicao.comum.validacao.Assert;
import br.com.iupi.condominio.medicao.leitura.dao.LeituraDAO;
import br.com.iupi.condominio.medicao.leitura.modelo.Leitura;
import br.com.iupi.condominio.medicao.medidor.modelo.Medidor;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedidor;
import br.com.iupi.condominio.medicao.medidor.service.MedidorService;
import br.com.iupi.condominio.medicao.unidade.modelo.UnidadeConsumidora;
import br.com.iupi.condominio.medicao.unidade.service.UnidadeConsumidoraService;

@Stateless
public class LeituraService {

	@Inject
	private LeituraDAO dao;

	@Inject
	private UnidadeConsumidoraService unidadeConsumidoraService;

	@Inject
	private MedidorService medidorService;

	public Long registraLeitura(String unidade, LocalDate dataLeitura, TipoMedidor tipoMedidor, Integer medido) {
		/* Validação de campos obrigatórios. */
		Assert.notBlank(unidade, Mensagem.LEITURA_UNIDADE_OBRIGATORIA);
		Assert.notNull(dataLeitura, Mensagem.LEITURA_DATA_OBRIGATORIA);
		Assert.notNull(tipoMedidor, Mensagem.LEITURA_TIPO_INVALIDO);
		Assert.notNull(medido, Mensagem.LEITURA_VALOR_MEDIDO_OBRIGATORIO);

		// verifica se a undiade existe nesse condomínio
		UnidadeConsumidora unidadeCondominio = unidadeConsumidoraService.consultaUnidadeConsumidora(unidade);

		/*
		 * // verifica se já existe leitura realizada para o mês desta unidade
		 * if (existeLeituraParaData(unidade, dataLeitura, tipoMedidor)) { throw
		 * new NegocioException(Mensagem.LEITURA_DATA_JA_EXISTE_MEDICAO); }
		 */

		// obtém o medidor da unidade
		Medidor medidor = medidorService.consultaMedidor(unidadeCondominio, tipoMedidor);

		Leitura leitura = new Leitura(medidor, dataLeitura, medido);

		dao.insert(leitura);

		return leitura.getId();
	}

	public Leitura consultaLeitura(Long id) {
		Leitura leitura = dao.consultaPorId(id);

		if (leitura == null) {
			throw new NegocioException(Mensagem.LEITURA_NAO_EXISTENTE);
		}

		return leitura;
	}

	public List<Leitura> consultaLeituras(String unidade, LocalDate inicioMes, LocalDate finalMes) {
		UnidadeConsumidora unidadeCondominio = unidadeConsumidoraService.consultaUnidadeConsumidora(unidade);

		List<Leitura> leituras = dao.consultaPorUnidadePeriodo(unidadeCondominio, inicioMes, finalMes);

		return leituras;
	}

	public Leitura consultaLeitura(YearMonth mesAno, UnidadeConsumidora unidadeConsumidora, TipoMedidor tipoLeitura) {
		LocalDate inicioMes = DataHelper.getInicioDeMes(mesAno);
		LocalDate finalMes = DataHelper.getFinalDeMes(mesAno);

		Leitura leitura = dao.consultaPorUnidadeTipoPeriodo(unidadeConsumidora, tipoLeitura, inicioMes, finalMes);

		if (leitura == null) {
			// throw new
		}

		return leitura;
	}

	/*
	 * private static boolean existeLeituraParaData(String unidade, LocalDate
	 * dataLeitura, TipoMedidor tipoMedidor) { for (Leitura leitura :
	 * medicoes.values()) { if
	 * (leitura.getMedidor().getUnidade().getUnidade().equals(unidade)) {
	 * 
	 * } }
	 * 
	 * return false; }
	 */
}