package br.com.iupi.condominio.medicao.unidade.service;

import java.util.HashMap;

import javax.inject.Singleton;

import br.com.iupi.condominio.medicao.unidade.modelo.Unidade;

@Singleton
public class UnidadeService {

	private static final HashMap<String, Unidade> unidades = new HashMap<String, Unidade>();

	static {
		unidades.put("211", new Unidade("211", "Apartamento 211"));
		unidades.put("212", new Unidade("212", "Apartamento 212"));
		unidades.put("213", new Unidade("213", "Apartamento 213"));
		unidades.put("214", new Unidade("214", "Apartamento 214"));
		unidades.put("geral", new Unidade("geral", "Condominio"));
	}

	public UnidadeService() {
	}

	public static Unidade consultaUnidade(String unidade) {
		return unidades.get(unidade);
	}
}