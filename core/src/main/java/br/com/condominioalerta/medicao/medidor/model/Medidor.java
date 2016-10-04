package br.com.iupi.condominio.medicao.medidor.modelo;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.iupi.condominio.medicao.comum.converter.TipoMedicaoConverter;
import br.com.iupi.condominio.medicao.comum.persistencia.Entidade;
import br.com.iupi.condominio.medicao.unidade.modelo.UnidadeConsumidora;

@Entity
@Table(name = "medidor")
public class Medidor extends Entidade {

	@Id
	@Column(name = "nu_medidor", nullable = false)
	private String numero;

	@Column(name = "tp_medidor", nullable = false)
	@Convert(converter = TipoMedicaoConverter.class)
	private TipoMedicao tipo;

	@Column(name = "vl_fator_medidor", nullable = false)
	private Integer fator;

	@ManyToOne
	@JoinColumn(name = "id_unidade", nullable = false)
	private UnidadeConsumidora unidadeConsumidora;

	public Medidor() {
		// Construtor padrão
	}

	public Medidor(String numero, TipoMedicao tipo, Integer fator, UnidadeConsumidora unidadeConsumidora) {
		super();
		this.numero = numero;
		this.tipo = tipo;
		this.fator = fator;
		this.unidadeConsumidora = unidadeConsumidora;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public TipoMedicao getTipo() {
		return tipo;
	}

	public void setTipo(TipoMedicao tipo) {
		this.tipo = tipo;
	}

	public Integer getFator() {
		return fator;
	}

	public void setFator(Integer fator) {
		this.fator = fator;
	}

	public UnidadeConsumidora getUnidadeConsumidora() {
		return unidadeConsumidora;
	}

	public void setUnidadeConsumidora(UnidadeConsumidora unidadeConsumidora) {
		this.unidadeConsumidora = unidadeConsumidora;
	}
}