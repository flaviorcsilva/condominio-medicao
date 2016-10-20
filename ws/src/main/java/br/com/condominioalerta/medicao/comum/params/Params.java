package br.com.condominioalerta.medicao.comum.params;

public enum Params {

	CONDOMINIO_ID("Condominio-ID");

	private String param;

	private Params(String param) {
		this.param = param;
	}

	public static Params get(String param) {
		Params params = null;
		for (Params p : Params.values()) {
			if (p.getParam().equals(param)) {
				params = p;

				break;
			}
		}

		return params;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
}