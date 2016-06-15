package br.com.iupi.condominio.medicao.condominio.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.iupi.condominio.medicao.comum.persistencia.Entidade;

@Entity
@Table(name = "condominio")
public class Condominio extends Entidade {

	@Id
	@Column(name = "cd_condominio", nullable = false)
	private String condominio;

	@Column(name = "nm_condominio", nullable = false)
	private String nome;

	@Column(name = "nu_medidor_agua")
	private String numeroMedidorAgua;

	@Column(name = "nu_medidor_gas")
	private String numeroMedidorGas;

	@Column(name = "tk_condominio")
	private String token;

	public String getCondominio() {
		return condominio;
	}

	public void setCondominio(String condominio) {
		this.condominio = condominio;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumeroMedidorAgua() {
		return numeroMedidorAgua;
	}

	public void setNumeroMedidorAgua(String numeroMedidorAgua) {
		this.numeroMedidorAgua = numeroMedidorAgua;
	}

	public String getNumeroMedidorGas() {
		return numeroMedidorGas;
	}

	public void setNumeroMedidorGas(String numeroMedidorGas) {
		this.numeroMedidorGas = numeroMedidorGas;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}