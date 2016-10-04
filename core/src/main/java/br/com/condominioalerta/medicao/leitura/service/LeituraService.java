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
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedicao;
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

	public Leitura registraLeitura(String condominio, String unidade, LocalDate dataLeitura, TipoMedicao tipoMedicao,
			Integer medido) {
		/* Validação de campos obrigatórios. */
		Assert.notBlank(unidade, Mensagem.LEITURA_UNIDADE_OBRIGATORIA);
		Assert.notNull(dataLeitura, Mensagem.LEITURA_DATA_OBRIGATORIA);
		Assert.notNull(tipoMedicao, Mensagem.LEITURA_TIPO_INVALIDO);
		Assert.notNull(medido, Mensagem.LEITURA_VALOR_MEDIDO_OBRIGATORIO);

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

		Leitura leitura = new Leitura(medidor, DataHelper.convertLocalDateToDate(dataLeitura), medido);

		dao.insert(leitura);

		return leitura;
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
				DataHelper.convertLocalDateToDate(inicioMes), DataHelper.convertLocalDateToDate(finalMes));

		return leituras;
	}

	public List<Leitura> consultaLeituras(String condominio, String unidade, LocalDate inicioMes, LocalDate finalMes) {
		UnidadeConsumidora unidadeConsumidora = unidadeConsumidoraService.consultaUnidadeConsumidora(condominio,
				unidade);

		return this.consultaLeituras(unidadeConsumidora, inicioMes, finalMes);
	}

	public Leitura consultaLeitura(UnidadeConsumidora unidadeConsumidora, TipoMedicao tipoMedicao, YearMonth mesAno) {
		LocalDate inicioMes = DataHelper.getInicioDeMes(mesAno);
		LocalDate finalMes = DataHelper.getFinalDeMes(mesAno);

		Leitura leitura = dao.consultaPorUnidadeTipoPeriodo(unidadeConsumidora, tipoMedicao,
				DataHelper.convertLocalDateToDate(inicioMes), DataHelper.convertLocalDateToDate(finalMes));

		if (leitura == null) {
			// throw new
		}

		return leitura;
	}
}