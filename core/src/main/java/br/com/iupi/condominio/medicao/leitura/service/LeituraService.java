package br.com.iupi.condominio.medicao.leitura.service;

import java.time.LocalDate;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.iupi.condominio.medicao.comum.execao.Mensagem;
import br.com.iupi.condominio.medicao.comum.validacao.Assert;
import br.com.iupi.condominio.medicao.leitura.dao.LeituraDAO;
import br.com.iupi.condominio.medicao.leitura.modelo.Leitura;
import br.com.iupi.condominio.medicao.medidor.modelo.Medidor;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedidor;
import br.com.iupi.condominio.medicao.medidor.service.MedidorService;
import br.com.iupi.condominio.medicao.unidade.modelo.Unidade;
import br.com.iupi.condominio.medicao.unidade.service.UnidadeService;

@Stateless
public class LeituraService {

	@Inject
	private LeituraDAO dao;

	@Inject
	private UnidadeService unidadeService;

	@Inject
	private MedidorService medidorService;

	public Long registraLeitura(String unidade, LocalDate dataLeitura, TipoMedidor tipoMedidor, Integer medido) {
		/* Validação de campos obrigatórios. */
		Assert.notBlank(unidade, Mensagem.LEITURA_UNIDADE_OBRIGATORIA);
		Assert.notNull(dataLeitura, Mensagem.LEITURA_DATA_OBRIGATORIA);
		Assert.notNull(tipoMedidor, Mensagem.LEITURA_TIPO_INVALIDO);
		Assert.notNull(medido, Mensagem.LEITURA_VALOR_MEDIDO_OBRIGATORIO);

		// verifica se a undiade existe nesse condomínio
		Unidade unidadeCondominio = unidadeService.consultaUnidade(unidade);

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
		return dao.consultaPorId(id);
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