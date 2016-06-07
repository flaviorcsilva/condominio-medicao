package br.com.iupi.condominio.medicao.leitura.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Singleton;

import br.com.iupi.condominio.medicao.comum.execao.Mensagem;
import br.com.iupi.condominio.medicao.comum.execao.NegocioException;
import br.com.iupi.condominio.medicao.comum.validacao.Assert;
import br.com.iupi.condominio.medicao.leitura.modelo.Leitura;
import br.com.iupi.condominio.medicao.medidor.modelo.Medidor;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedidor;
import br.com.iupi.condominio.medicao.medidor.service.MedidorService;
import br.com.iupi.condominio.medicao.unidade.service.UnidadeService;

@Singleton
public class LeituraService {

	private static final HashMap<Integer, Leitura> medicoes = new HashMap<Integer, Leitura>();

	private static AtomicInteger sequencial = new AtomicInteger(0);

	public static Integer registraLeitura(String unidade, LocalDate dataLeitura, TipoMedidor tipoMedidor, Integer medido) {
		/* Validação de campos obrigatórios. */
		Assert.notBlank(unidade, Mensagem.LEITURA_UNIDADE_OBRIGATORIA);
		Assert.notNull(dataLeitura, Mensagem.LEITURA_DATA_OBRIGATORIA);
		Assert.notNull(tipoMedidor, Mensagem.LEITURA_TIPO_INVALIDO);
		Assert.notNull(medido, Mensagem.LEITURA_VALOR_MEDIDO_OBRIGATORIO);

		// verifica se a undiade existe nesse condomínio
		if (!UnidadeService.existeUnidade(unidade)) {
			throw new NegocioException(Mensagem.UNIDADE_NAO_EXISTENTE);
		}

		// verifica se já existe leitura realizada para o mês desta unidade
		if (existeLeituraParaData(unidade, dataLeitura, tipoMedidor)) {
			throw new NegocioException(Mensagem.LEITURA_DATA_JA_EXISTE_MEDICAO);
		}

		// obtém o medidor da unidade
		Medidor medidor = MedidorService.consultaMedidor(unidade, tipoMedidor);

		Leitura leitura = new Leitura(medidor, dataLeitura, medido);

		Integer id = incrementaSequencial();
		leitura.setId(id.longValue());
		medicoes.put(id, leitura);
		
		return id;
	}

	private static boolean existeLeituraParaData(String unidade, LocalDate dataLeitura, TipoMedidor tipoMedidor) {
		for (Leitura leitura : medicoes.values()) {
			if (leitura.getMedidor().getUnidade().getUnidade().equals(unidade)) {

			}
		}

		return false;
	}

	protected void validaAntesDeInserir() {

	}

	public static Integer incrementaSequencial() {
		return sequencial.incrementAndGet();
	}

	public static Integer getQuantidade() {
		return medicoes.size();
	}

	public static Integer getSequencial() {
		return sequencial.get();
	}

	public static Leitura consultaLeitura(Integer id) {
		return medicoes.get(id);
	}
}