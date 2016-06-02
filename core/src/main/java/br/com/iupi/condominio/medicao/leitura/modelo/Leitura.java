package br.com.iupi.condominio.medicao.leitura.modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.iupi.condominio.medicao.comum.persistencia.Entidade;
import br.com.iupi.condominio.medicao.medidor.modelo.Medidor;

@XmlRootElement
public class Leitura extends Entidade<Long> {

	private Long id;

	private Medidor medidor;

	private LocalDate dataLeitura;

	private Integer leitura;

	public Leitura() {
	}

	public Leitura(Medidor medidor, LocalDate dataLeitura, Integer leitura) {
		super();
		this.medidor = medidor;
		this.dataLeitura = dataLeitura;
		this.leitura = leitura;
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

	public LocalDate getDataLeitura() {
		return dataLeitura;
	}

	public String getDataLeituraFormatada() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		return dataLeitura.format(formatter);
	}

	public void setDataLeitura(LocalDate dataLeitura) {
		this.dataLeitura = dataLeitura;
	}

	public Integer getLeitura() {
		return leitura;
	}

	public void setLeitura(Integer leitura) {
		this.leitura = leitura;
	}
}
