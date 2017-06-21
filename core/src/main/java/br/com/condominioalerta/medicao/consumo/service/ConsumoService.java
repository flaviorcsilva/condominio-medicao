package br.com.condominioalerta.medicao.consumo.service;

import java.io.File;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.condominioalerta.medicao.comum.email.EmailService;
import br.com.condominioalerta.medicao.comum.execao.Mensagem;
import br.com.condominioalerta.medicao.comum.execao.NegocioException;
import br.com.condominioalerta.medicao.comum.helper.ArquivoHelper;
import br.com.condominioalerta.medicao.comum.helper.DataHelper;
import br.com.condominioalerta.medicao.comum.helper.NumeroHelper;
import br.com.condominioalerta.medicao.comum.validacao.Assert;
import br.com.condominioalerta.medicao.condominio.model.Condominio;
import br.com.condominioalerta.medicao.condominio.service.CondominioService;
import br.com.condominioalerta.medicao.consumo.dao.ConsumoCondominioDAO;
import br.com.condominioalerta.medicao.consumo.dto.ConsumoDTO;
import br.com.condominioalerta.medicao.consumo.model.ConsumoCondominio;
import br.com.condominioalerta.medicao.consumo.model.ConsumoUnidade;
import br.com.condominioalerta.medicao.leitura.model.Leitura;
import br.com.condominioalerta.medicao.leitura.service.LeituraService;
import br.com.condominioalerta.medicao.medidor.model.TipoMedicao;
import br.com.condominioalerta.medicao.unidade.model.UnidadeConsumidora;
import br.com.condominioalerta.medicao.unidade.service.UnidadeConsumidoraService;

@Stateless
public class ConsumoService {

	private final Logger LOGGER = Logger.getLogger(ConsumoService.class.getName());

	@Inject
	private LeituraService leituraService;

	@Inject
	private UnidadeConsumidoraService unidadeConsumidoraService;

	@Inject
	private CondominioService condominioService;

	@Inject
	private ConsumoCondominioDAO dao;

	@Inject
	private ArquivoConsumoService arquivoConsumoService;

	@Inject
	private EmailService emailService;

	public List<ConsumoUnidade> calculaConsumos(UnidadeConsumidora unidadeConsumidora, Integer mes, Integer ano) {
		List<ConsumoUnidade> consumos = new ArrayList<>();

		ConsumoUnidade consumo = null;
		for (TipoMedicao tipoMedicao : TipoMedicao.values()) {
			consumo = this.calculaConsumo(unidadeConsumidora, tipoMedicao, mes, ano);

			if (consumo != null) {
				consumos.add(consumo);
			}
		}

		return new ArrayList<ConsumoUnidade>(consumos);
	}

	public ConsumoUnidade calculaConsumo(UnidadeConsumidora unidadeConsumidora, TipoMedicao tipoMedicao, Integer mes,
			Integer ano) {
		YearMonth mesAtual = DataHelper.converteIntegerToYearMonth(mes, ano);
		YearMonth mesAnterior = mesAtual.minusMonths(1);

		/* Obtém as leituras atual e anterior */
		Leitura leituraAnterior = leituraService.consultaLeitura(unidadeConsumidora, tipoMedicao, mesAnterior);
		Leitura leituraAtual = leituraService.consultaLeitura(unidadeConsumidora, tipoMedicao, mesAtual);

		if (leituraAnterior == null || leituraAtual == null) {
			return null;
		}

		ConsumoCondominio consumoCondominio = this
				.consultaConsumoCondominio(unidadeConsumidora.getCondominio().getCodigo(), tipoMedicao, mes, ano);

		return new ConsumoUnidade(leituraAnterior, leituraAtual, consumoCondominio.getValorM3());
	}

	public void enviaConsumo(final String condominio, Integer mes, Integer ano) {
		List<UnidadeConsumidora> unidades = unidadeConsumidoraService
				.consultaUnidadesConsumidorasPorCondominio(condominio);

		for (UnidadeConsumidora unidadeConsumidora : unidades) {
			try {
				enviaConsumo(unidadeConsumidora, mes, ano);
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Não foi possível enviar e-mail do consumo " + mes + "-" + ano
						+ " da unidade: " + unidadeConsumidora.getUnidade(), e.getCause());
			}
		}
	}

	public void enviaConsumo(UnidadeConsumidora unidadeConsumidora, Integer mes, Integer ano) {
		String unidade = unidadeConsumidora.getUnidade();
		String mesAno = DataHelper.getMesPorExtenso(mes, ano) + "/" + ano;

		Assert.notBlank(unidadeConsumidora.getEmailResponsavel(), Mensagem.UNIDADE_NAO_EXISTE_EMAIL, unidade);

		String fileHtml = "emails/consumo.html";
		String html = ArquivoHelper.converteArquivoResourceToString(fileHtml);

		List<ConsumoUnidade> consumos = new ArrayList<>();
		consumos = this.calculaConsumos(unidadeConsumidora, mes, ano);

		if (consumos.isEmpty()) {
			throw new NegocioException(Mensagem.CONSUMO_NAO_HA_CONSUMO_PARA_UNIDADE, unidade, mesAno);
		}

		/* Realizando o BINDING para trocar as tags pelos dados do consumo */
		html = html.replace("{unidade.consumidora}", unidade);
		html = html.replace("{mes.ano}", mesAno);

		Double valorTotal = 0.0;
		for (ConsumoUnidade consumoUnidade : consumos) {
			ConsumoDTO consumo = new ConsumoDTO(consumoUnidade);

			String binding = consumoUnidade.getTipoMedicao().getBinding();

			html = html.replace("{" + binding + ".leitura.anterior}", consumo.getDataLeituraAnterior());
			html = html.replace("{" + binding + ".medicao.anterior}", consumo.getMedicaoAnterior().toString());
			html = html.replace("{" + binding + ".leitura.atual}", consumo.getDataLeituraAtual());
			html = html.replace("{" + binding + ".medicao.atual}", consumo.getMedicaoAtual().toString());
			html = html.replace("{" + binding + ".consumo}", consumo.getConsumo().toString());
			html = html.replace("{" + binding + ".valor.m3}", consumo.getValorM3());
			html = html.replace("{" + binding + ".valor.pagar}", consumo.getValorAPagar());

			valorTotal = valorTotal + consumoUnidade.getValor();
		}

		html = html.replace("{valor.total}", NumeroHelper.formataNumeroTo2Decimais(valorTotal));

		html = removeTagsNaoPopuladas(html);

		String subject = "Informativo de Consumo: " + unidade + " - " + mesAno;
		// emailService.send(unidadeConsumidora.getEmailResponsavel(), subject,
		// html, "text/html");
		emailService.sendGrid(unidadeConsumidora.getEmailResponsavel(), subject, html, "text/html");
	}

	private String removeTagsNaoPopuladas(String html) {
		for (TipoMedicao tipoMedicao : TipoMedicao.values()) {
			String vazio = "--";
			String binding = tipoMedicao.getBinding();

			html = html.replace("{" + binding + ".leitura.anterior}", vazio);
			html = html.replace("{" + binding + ".medicao.anterior}", vazio);
			html = html.replace("{" + binding + ".leitura.atual}", vazio);
			html = html.replace("{" + binding + ".medicao.atual}", vazio);
			html = html.replace("{" + binding + ".consumo}", vazio);
			html = html.replace("{" + binding + ".valor.m3}", vazio);
			html = html.replace("{" + binding + ".valor.pagar}", vazio);
		}

		return html;
	}

	public void geraArquivo(String codigoCondominio, Integer mes, Integer ano) {
		Map<UnidadeConsumidora, List<ConsumoUnidade>> consumosUnidade = new TreeMap<>();
		List<ConsumoUnidade> consumos = null;

		YearMonth mesAtual = DataHelper.converteIntegerToYearMonth(mes, ano);
		YearMonth mesAnterior = mesAtual.minusMonths(1);

		/* Cache com o valores do M3 para Água e Gás */
		Map<TipoMedicao, Double> valorM3 = criaMapaValorM3(codigoCondominio, mes, ano);

		/* Obtém todas as unidades consumidoras do condomínio */
		List<UnidadeConsumidora> unidades = unidadeConsumidoraService
				.consultaUnidadesConsumidorasPorCondominio(codigoCondominio);
		for (UnidadeConsumidora unidadeConsumidora : unidades) {

			consumos = new ArrayList<ConsumoUnidade>();
			/* Obtém as leituras atuais da unidade */
			List<Leitura> leiturasAtuais = leituraService.consultaLeituras(unidadeConsumidora, mesAtual);
			for (Leitura leituraAtual : leiturasAtuais) {
				TipoMedicao tipoMedicao = leituraAtual.getMedidor().getTipo();

				/* Obtém a leitura anterior de mesmo tipo desta unidade */
				Leitura leituraAnterior = leituraService.consultaLeitura(unidadeConsumidora, tipoMedicao, mesAnterior);

				Double precoM3 = valorM3.get(leituraAtual.getMedidor().getTipo());

				consumos.add(new ConsumoUnidade(leituraAnterior, leituraAtual, precoM3));
			}

			consumosUnidade.put(unidadeConsumidora, consumos);
		}

		Condominio condominio = condominioService.consultaCondominioPorCodigo(codigoCondominio);
		ConsumoCondominio consumoCondominio = consultaConsumoCondominio(codigoCondominio, TipoMedicao.AGUA_FRIA, mes,
				ano);

		File attachment = arquivoConsumoService.geraArquivo(consumosUnidade, condominio, consumoCondominio, mes, ano);

		String addresses = condominio.getEmail();
		String subject = "Planilha de Rateio do Consumo - " + DataHelper.getMesPorExtenso(mes, ano) + "/" + ano;
		String content = "Segue a planilha de rateio em anexo.";
		String contentType = "text/plain";
		String contentId = condominio.getNomeFormatado();

		emailService.sendGrid(addresses, subject, content, contentType, attachment, contentId);
	}

	public List<ConsumoUnidade> calculaConsumos(String codigoCondominio, Integer mes, Integer ano) {
		Map<UnidadeConsumidora, List<ConsumoUnidade>> consumosUnidade = new TreeMap<>();
		List<ConsumoUnidade> consumos = null;

		YearMonth mesAtual = DataHelper.converteIntegerToYearMonth(mes, ano);
		YearMonth mesAnterior = mesAtual.minusMonths(1);

		/* Cache com o valores do M3 para Água e Gás */
		Map<TipoMedicao, Double> valorM3 = criaMapaValorM3(codigoCondominio, mes, ano);

		/* Obtém todas as unidades consumidoras do condomínio */
		List<UnidadeConsumidora> unidades = unidadeConsumidoraService
				.consultaUnidadesConsumidorasPorCondominio(codigoCondominio);
		for (UnidadeConsumidora unidadeConsumidora : unidades) {

			consumos = new ArrayList<ConsumoUnidade>();
			/* Obtém as leituras atuais da unidade */
			List<Leitura> leiturasAtuais = leituraService.consultaLeituras(unidadeConsumidora, mesAtual);
			for (Leitura leituraAtual : leiturasAtuais) {
				TipoMedicao tipoMedicao = leituraAtual.getMedidor().getTipo();

				/* Obtém a leitura anterior de mesmo tipo desta unidade */
				Leitura leituraAnterior = leituraService.consultaLeitura(unidadeConsumidora, tipoMedicao, mesAnterior);

				Double precoM3 = valorM3.get(leituraAtual.getMedidor().getTipo());

				consumos.add(new ConsumoUnidade(leituraAnterior, leituraAtual, precoM3));
			}

			consumosUnidade.put(unidadeConsumidora, consumos);
		}

		return consumos;
	}

	public void registraConsumoCondominio(ConsumoCondominio consumoCondominio) {
		validaAntesDeInserir(consumoCondominio);

		// Verifica se já existe consumo desse tipo para o mês/ano informado.
		ConsumoCondominio consumoMesAno = dao.consultaPorCondominioTipoMedicaoAnoMes(consumoCondominio.getCondominio(),
				consumoCondominio.getTipoMedicao(), consumoCondominio.getAnoMes());
		if (consumoMesAno != null) {
			String mesAno = consumoCondominio.getMes() + "/" + consumoCondominio.getAno();
			throw new NegocioException(Mensagem.CONSUMO_CONDOMINIO_MES_ANO_JA_EXISTE_CONSUMO,
					consumoCondominio.getTipoMedicao().getValor(), mesAno);
		}

		dao.insert(consumoCondominio);
	}

	public List<ConsumoCondominio> consultaConsumosDoCondominio(String condominio) {
		List<ConsumoCondominio> consumos = dao.consultaPorCondominio(condominio);
		
		if (consumos.isEmpty()) {
			//TODO : Gerar exceção
		}
		
		return consumos;
	}

	private void validaAntesDeInserir(ConsumoCondominio consumoCondominio) {
		/* Validação de campos obrigatórios. */
		Assert.notBlank(consumoCondominio.getCondominio(), Mensagem.CONSUMO_CONDOMINIO_CONDOMINIO_OBRIGATORIO);
		Assert.notBlank(consumoCondominio.getAnoMes(), Mensagem.CONSUMO_CONDOMINIO_MES_ANO_OBRIGATORIO);
		Assert.notNull(consumoCondominio.getTipoMedicao(), Mensagem.LEITURA_TIPO_INVALIDO);
		Assert.notNull(consumoCondominio.getValorMedidoFatura(), Mensagem.CONSUMO_CONDOMINIO_VALOR_MEDIDO_OBRIGATORIO);
		Assert.notNull(consumoCondominio.getValorTotalFaturado(), Mensagem.CONSUMO_CONDOMINIO_VALOR_TOTAL_OBRIGATORIO);
	}

	private ConsumoCondominio consultaConsumoCondominio(String codigoCondominio, TipoMedicao tipoMedicao, Integer mes,
			Integer ano) {
		/*
		 * Caso a medição seja do tipo Água Quente, deve obter o consumo
		 * faturado da água fria pela concessionária
		 */
		TipoMedicao tipo = null;
		if (tipoMedicao.equals(TipoMedicao.AGUA_QUENTE)) {
			tipo = TipoMedicao.AGUA_FRIA;
		} else {
			tipo = tipoMedicao;
		}

		/* Formata o mes/ano para pesquisa */
		String anoMes = DataHelper.converteMesAnoToStringAnoMes(mes, ano);

		ConsumoCondominio consumoCondominio = dao.consultaPorCondominioTipoMedicaoAnoMes(codigoCondominio, tipo,
				anoMes);

		if (consumoCondominio == null || consumoCondominio.getValorM3() == null
				|| consumoCondominio.getValorM3() == 0.0) {
			throw new NegocioException(Mensagem.CONSUMO_CONDOMINIO_NAO_HA_REGISTRO,
					DataHelper.getMesPorExtenso(mes, ano) + "/" + ano);
		}

		return consumoCondominio;
	}

	private Map<TipoMedicao, Double> criaMapaValorM3(String codigoCondominio, Integer mes, Integer ano) {
		Map<TipoMedicao, Double> map = new HashMap<>();

		ConsumoCondominio consumoGeralAgua = this.consultaConsumoCondominio(codigoCondominio, TipoMedicao.AGUA_FRIA,
				mes, ano);
		ConsumoCondominio consumoGeralGas = this.consultaConsumoCondominio(codigoCondominio, TipoMedicao.GAS, mes, ano);

		map.put(TipoMedicao.AGUA_FRIA, consumoGeralAgua.getValorM3());
		map.put(TipoMedicao.AGUA_QUENTE, consumoGeralAgua.getValorM3());
		map.put(TipoMedicao.GAS, consumoGeralGas.getValorM3());

		return map;
	}
}