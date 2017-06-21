package br.com.condominioalerta.medicao.leitura.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.condominioalerta.medicao.comum.execao.Mensagem;
import br.com.condominioalerta.medicao.comum.execao.NegocioException;
import br.com.condominioalerta.medicao.comum.helper.DataHelper;
import br.com.condominioalerta.medicao.comum.validacao.Assert;
import br.com.condominioalerta.medicao.leitura.dao.LeituraDAO;
import br.com.condominioalerta.medicao.leitura.model.Leitura;
import br.com.condominioalerta.medicao.medidor.model.Medidor;
import br.com.condominioalerta.medicao.medidor.model.TipoMedicao;
import br.com.condominioalerta.medicao.medidor.service.MedidorService;
import br.com.condominioalerta.medicao.unidade.model.UnidadeConsumidora;
import br.com.condominioalerta.medicao.unidade.service.UnidadeConsumidoraService;

@Stateless
public class LeituraService {

	@Inject
	private LeituraDAO dao;

	@Inject
	private UnidadeConsumidoraService unidadeConsumidoraService;

	@Inject
	private MedidorService medidorService;

	public Leitura registraLeitura(String condominio, String unidade, LocalDate dataLeitura, TipoMedicao tipoMedicao,
			Integer medido, byte[] foto) {
		validaAntesDeInserir(unidade, dataLeitura, tipoMedicao, medido);

		// verifica se a undiade existe nesse condomínio
		UnidadeConsumidora unidadeConsumidora = unidadeConsumidoraService.consultaUnidadeConsumidora(condominio,
				unidade);

		// verifica se já existe leitura realizada para o mês desta unidade
		YearMonth mesAno = YearMonth.of(dataLeitura.getYear(), dataLeitura.getMonth());
		Leitura leituraNoMes = this.consultaLeitura(unidadeConsumidora, tipoMedicao, mesAno);

		if (leituraNoMes != null) {
			throw new NegocioException(Mensagem.LEITURA_DATA_JA_EXISTE_MEDICAO);
		}

		// obtém o medidor da unidade
		Medidor medidor = medidorService.consultaMedidor(unidadeConsumidora, tipoMedicao);

		Leitura leitura = new Leitura(medidor, DataHelper.converteLocalDateToDate(dataLeitura), medido, foto);
		dao.insert(leitura);

		return leitura;
	}

	public void atualizaLeitura(Leitura leitura) {
		validaAntesDeAtualizar(leitura.getMedido());

		dao.update(leitura);
	}

	private void validaAntesDeInserir(String unidade, LocalDate dataLeitura, TipoMedicao tipoMedicao, Integer medido) {
		/* Validação de campos obrigatórios. */
		Assert.notBlank(unidade, Mensagem.LEITURA_UNIDADE_OBRIGATORIA);
		Assert.notNull(dataLeitura, Mensagem.LEITURA_DATA_OBRIGATORIA);
		Assert.notNull(tipoMedicao, Mensagem.LEITURA_TIPO_INVALIDO);
		Assert.notNull(medido, Mensagem.LEITURA_VALOR_MEDIDO_OBRIGATORIO);
	}

	private void validaAntesDeAtualizar(Integer medido) {
		Assert.notNull(medido, Mensagem.LEITURA_VALOR_MEDIDO_OBRIGATORIO);
	}

	public Leitura consultaLeitura(Long id) {
		Leitura leitura = dao.consultaPorId(id);

		if (leitura == null) {
			throw new NegocioException(Mensagem.LEITURA_NAO_EXISTENTE);
		}

		return leitura;
	}

	public List<Leitura> consultaLeituras(UnidadeConsumidora unidadeConsumidora, YearMonth mesAno) {
		LocalDate inicioMes = DataHelper.getInicioDeMes(mesAno);
		LocalDate finalMes = DataHelper.getFinalDeMes(mesAno);

		return this.consultaLeituras(unidadeConsumidora, inicioMes, finalMes);
	}

	public List<Leitura> consultaLeituras(UnidadeConsumidora unidadeConsumidora, LocalDate inicioMes,
			LocalDate finalMes) {

		List<Leitura> leituras = dao.consultaPorUnidadePeriodo(unidadeConsumidora,
				DataHelper.converteLocalDateToDate(inicioMes), DataHelper.converteLocalDateToDate(finalMes));

		return leituras;
	}

	public List<Leitura> consultaLeituras(String condominio, String unidade, LocalDate inicioMes, LocalDate finalMes) {
		UnidadeConsumidora unidadeConsumidora = unidadeConsumidoraService.consultaUnidadeConsumidora(condominio,
				unidade);

		return this.consultaLeituras(unidadeConsumidora, inicioMes, finalMes);
	}

	public List<Leitura> consultaLeituras(String condominio, LocalDate inicioMes, LocalDate finalMes) {
		List<Leitura> leituras = dao.consultaPorPeriodo(DataHelper.converteLocalDateToDate(inicioMes),
				DataHelper.converteLocalDateToDate(finalMes));

		return leituras;
	}

	public Leitura consultaLeitura(UnidadeConsumidora unidadeConsumidora, TipoMedicao tipoMedicao, YearMonth mesAno) {
		LocalDate inicioMes = DataHelper.getInicioDeMes(mesAno);
		LocalDate finalMes = DataHelper.getFinalDeMes(mesAno);

		Leitura leitura = dao.consultaPorUnidadeTipoPeriodo(unidadeConsumidora, tipoMedicao,
				DataHelper.converteLocalDateToDate(inicioMes), DataHelper.converteLocalDateToDate(finalMes));

		if (leitura == null) {
			// throw new
		}

		return leitura;
	}
}