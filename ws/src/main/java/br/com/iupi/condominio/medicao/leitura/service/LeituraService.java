package br.com.iupi.condominio.medicao.leitura.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Singleton;

import br.com.iupi.condominio.medicao.leitura.modelo.Leitura;
import br.com.iupi.condominio.medicao.medidor.modelo.Medidor;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedidor;
import br.com.iupi.condominio.medicao.medidor.service.MedidorService;

@Singleton
public class LeituraService {

	private static final HashMap<Integer, Leitura> medicoes = new HashMap<Integer, Leitura>();
	private static AtomicInteger sequencial = new AtomicInteger(0);

	public LeituraService() {
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

	public static void insereLeitura(String unidadeMedida, LocalDate dataLeitura, TipoMedidor tipoMedidor,
			Integer medido) {
		Medidor medidor = MedidorService.consultaMedidor(unidadeMedida, tipoMedidor);

		Leitura leitura = new Leitura(medidor, dataLeitura, medido);

		int id = incrementaSequencial();
		medicoes.put(id, leitura);
	}

	public static Leitura consultaLeitura(int id) {
		return medicoes.get(id);
	}
}