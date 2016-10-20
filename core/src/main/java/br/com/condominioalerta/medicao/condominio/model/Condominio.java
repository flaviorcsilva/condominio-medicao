package br.com.condominioalerta.medicao.condominio.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.condominioalerta.medicao.comum.persistencia.Entidade;

@Entity
@Cacheable
@Table(name = "condominio")
public class Condominio extends Entidade {

	@Id
	@Column(name = "cd_condominio", nullable = false)
	private String codigo;

	@Column(name = "nm_condominio", nullable = false)
	private String nome;

	@Column(name = "tk_condominio")
	private String token;
	
	@Column(name = "cd_email")
	private String email;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNomeFormatado() {
		return nome.replace(" ", "-").toLowerCase();
	}
}