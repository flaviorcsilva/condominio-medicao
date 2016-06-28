package br.com.iupi.condominio.medicao.medidor.modelo;

public enum TipoMedicao {

	AGUA_FRIA(1, "Agua Fria"), AGUA_QUENTE(2, "Agua Quente"), GAS(3, "Gas");

	private Integer chave;

	private String valor;

	private TipoMedicao(Integer chave, String valor) {
		this.chave = chave;
		this.setValor(valor);
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
}