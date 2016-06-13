package br.com.iupi.condominio.medicao.medidor.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.iupi.condominio.medicao.comum.persistencia.Entidade;
import br.com.iupi.condominio.medicao.unidade.modelo.UnidadeConsumidora;

@Entity
public class Medidor extends Entidade {

	@Id
	@Column(name = "nu_medidor")
	private String numero;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "tp_medidor")
	private TipoMedidor tipo;

	@ManyToOne
	@JoinColumn(name = "id_unidade")
	private UnidadeConsumidora unidadeConsumidora;

	public Medidor() {
		// Construtor padrão
	}

	public Medidor(String numero, TipoMedidor tipo, UnidadeConsumidora unidadeConsumidora) {
		super();
		this.numero = numero;
		this.tipo = tipo;
		this.unidadeConsumidora = unidadeConsumidora;
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

	public UnidadeConsumidora getUnidadeConsumidora() {
		return unidadeConsumidora;
	}

	public void setUnidadeConsumidora(UnidadeConsumidora unidadeConsumidora) {
		this.unidadeConsumidora = unidadeConsumidora;
	}
}