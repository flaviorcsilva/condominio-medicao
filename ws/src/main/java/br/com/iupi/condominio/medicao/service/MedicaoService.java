package br.com.iupi.condominio.medicao.service;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Singleton;

import br.com.iupi.condominio.medicao.leitura.modelo.Leitura;

@Singleton
public class MedicaoService {

	private static final HashMap<Integer, Leitura> medicoes = new HashMap<Integer, Leitura>();
	private static AtomicInteger sequencial = new AtomicInteger(0);

	public MedicaoService() {
		// initial content
		//addLeitura(new Leitura());
	}

	public static int incrementaSequencial() {
		return sequencial.incrementAndGet();
	}

	public static int addLeitura(Leitura leitura) {
		int id = incrementaSequencial();

		medicoes.put(id, leitura);

		return id;
	}

	public static int getQuantidade() {
		return medicoes.size();
	}

	public static Leitura getLeitura(int id) {
		return medicoes.get(id);
	}

	public static int getSequencial() {
		return sequencial.get();
	}
}