package br.com.iupi.condominio.medicao.leitura.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Singleton;

import br.com.iupi.condominio.medicao.comum.execao.Mensagem;
import br.com.iupi.condominio.medicao.comum.validacao.Assert;
import br.com.iupi.condominio.medicao.leitura.modelo.Leitura;
import br.com.iupi.condominio.medicao.medidor.modelo.Medidor;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedidor;
import br.com.iupi.condominio.medicao.medidor.service.MedidorService;

@Singleton
public class LeituraService {

	private static final HashMap<Integer, Leitura> medicoes = new HashMap<Integer, Leitura>();

	private static AtomicInteger sequencial = new AtomicInteger(0);

	public static void registraLeitura(String unidade, LocalDate dataLeitura, TipoMedidor tipoMedidor, Integer medido) {
		/* Validação de campos obrigatórios. */
		Assert.notBlank(unidade, Mensagem.LEITURA_UNIDADE_OBRIGATORIA);
		Assert.notNull(dataLeitura, Mensagem.LEITURA_DATA_OBRIGATORIA);
		Assert.notNull(tipoMedidor, Mensagem.LEITURA_TIPO_INVALIDO);
		Assert.notNull(medido, Mensagem.LEITURA_VALOR_MEDIDO_OBRIGATORIO);

		// verifica se já existe leitura realizada para o mês desta unidade

		// obtém o medidor da unidade
		Medidor medidor = MedidorService.consultaMedidor(unidade, tipoMedidor);

		Leitura leitura = new Leitura(medidor, dataLeitura, medido);

		int id = incrementaSequencial();
		medicoes.put(id, leitura);
	}

	protected void validaAntesDeInserir() {

	}

	public static int incrementaSequencial() {
		return sequencial.incrementAndGet();
	}

	public static int getQuantidade() {
		return medicoes.size();
	}

	public static int getSequencial() {
		return sequencial.get();
	}

	public static Leitura consultaLeitura(int id) {
		return medicoes.get(id);
	}
}