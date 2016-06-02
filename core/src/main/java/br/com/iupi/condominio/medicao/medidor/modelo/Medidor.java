package br.com.iupi.condominio.medicao.medidor.modelo;

import br.com.iupi.condominio.medicao.comum.persistencia.Entidade;
import br.com.iupi.condominio.medicao.unidade.modelo.Unidade;

public class Medidor extends Entidade<String> {

	private String numero;

	private TipoMedidor tipo;

	private Unidade unidade;

	public Medidor() {
		// Construtor padrão
	}

	public Medidor(String numero, TipoMedidor tipo, Unidade unidade) {
		super();
		this.numero = numero;
		this.tipo = tipo;
		this.unidade = unidade;
	}

	public String getId() {
		return numero;
	}

	public void setId(String id) {
		this.numero = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public TipoMedidor getTipo() {
		return tipo;
	}

	public void setTipo(TipoMedidor tipo) {
		this.tipo = tipo;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
}