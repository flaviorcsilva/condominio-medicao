package br.com.iupi.condominio.medicao.medidor.modelo;

import br.com.iupi.condominio.medicao.comum.persistencia.Entidade;
import br.com.iupi.condominio.medicao.unidade.modelo.Unidade;

public class Medidor extends Entidade<String> {

	private Unidade unidade;

	private TipoMedidor tipo;

	private String numero;
	
	public Medidor() {
		// Construtor padrão
	}

	public Medidor(Unidade unidade, TipoMedidor tipo, String numero) {
		super();
		this.unidade = unidade;
		this.tipo = tipo;
		this.numero = numero;
	}
	
	public String getId() {
		return numero;
	}

	public void setId(String id) {
		this.numero = id;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public TipoMedidor getTipo() {
		return tipo;
	}

	public void setTipo(TipoMedidor tipo) {
		this.tipo = tipo;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
}
