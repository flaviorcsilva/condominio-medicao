package br.com.iupi.condominio.medicao.medidor.modelo;

public enum TipoMedidor {

	AGUA_FRIA(1, "Água Fria"), AGUA_QUENTE(2, "Água Quente"), GAS(3, "Gás");

	private Integer chave;

	private String valor;

	private TipoMedidor(Integer chave, String valor) {
		this.chave = chave;
		this.setValor(valor);
	}

	public static TipoMedidor get(Integer chave) {
		TipoMedidor tipo = null;
		for (TipoMedidor tipoMedidor : TipoMedidor.values()) {
			if (tipoMedidor.getChave().equals(chave)) {
				tipo = tipoMedidor;

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