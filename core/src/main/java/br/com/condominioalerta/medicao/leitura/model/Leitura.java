package br.com.condominioalerta.medicao.leitura.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.condominioalerta.medicao.comum.persistencia.Entidade;
import br.com.condominioalerta.medicao.medidor.model.Medidor;

@Entity
@Table(name = "leitura")
public class Leitura extends Entidade {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_leitura")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "nu_medidor")
	private Medidor medidor;

	@Column(name = "dt_leitura", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataLeitura;

	@Column(name = "vl_medido")
	private Integer medido;
	
	@Lob
	@Column(name = "im_foto")
	private byte[] foto;

	public Leitura() {
	}

	public Leitura(Medidor medidor, Date dataLeitura, Integer medido, byte[] foto) {
		super();
		this.medidor = medidor;
		this.dataLeitura = dataLeitura;
		this.medido = medido;
		this.foto = foto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Medidor getMedidor() {
		return medidor;
	}

	public void setMedidor(Medidor medidor) {
		this.medidor = medidor;
	}

	public Date getDataLeitura() {
		return dataLeitura;
	}

	public String getDataLeituraFormatada() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		return formatter.format(dataLeitura);
	}

	public void setDataLeitura(Date dataLeitura) {
		this.dataLeitura = dataLeitura;
	}

	public Integer getMedido() {
		return medido;
	}

	public void setMedido(Integer medido) {
		this.medido = medido;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
}