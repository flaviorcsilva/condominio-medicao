package br.com.condominioalerta.medicao.medidor.model;

public enum TipoMedicao {

	AGUA_FRIA(1, "Agua Fria", "agua.fria"), AGUA_QUENTE(2, "Agua Quente", "agua.quente"), GAS(3, "Gas", "gas");

	private Integer chave;

	private String valor;

	private String binding;

	private TipoMedicao(Integer chave, String valor, String binding) {
		this.chave = chave;
		this.valor = valor;
		this.binding = binding;
	}

	public static TipoMedicao get(Integer chave) {
		TipoMedicao tipo = null;
		for (TipoMedicao tipoMedicao : TipoMedicao.values()) {
			if (tipoMedicao.getChave().equals(chave)) {
				tipo = tipoMedicao;

				break;
			}
		}

		return tipo;
	}

	public Integer getChave() {
		return chave;
	}

	public void setChave(Integer chave) {
		this.chave = chave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getBinding() {
		return binding;
	}
}