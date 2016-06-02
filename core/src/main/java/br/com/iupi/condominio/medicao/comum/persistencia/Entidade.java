package br.com.iupi.condominio.medicao.comum.persistencia;

public class Entidade<PK> {

	private PK id;

	public PK getId() {
		return id;
	}

	public void setId(PK id) {
		this.id = id;
	}
}
