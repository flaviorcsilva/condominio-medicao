package br.com.condominioalerta.medicao.unidade.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.condominioalerta.medicao.comum.persistencia.Entidade;
import br.com.condominioalerta.medicao.condominio.model.Condominio;

@Entity
@Table(name = "unidade")
public class UnidadeConsumidora extends Entidade implements Comparable<UnidadeConsumidora> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_unidade")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "cd_condominio")
	private Condominio condominio;

	@Column(name = "cd_unidade")
	private String unidade;

	@Column(name = "ds_unidade")
	private String descricao;
	
	@Column(name = "nm_responsavel")
	private String nomeResponsavel;

	@Column(name = "cd_email_responsavel")
	private String emailResponsavel;

	@Column(name = "nu_telefone_responsavel")
	private String telefoneResponsavel;
	
	@Column(name = "tp_unidade")
	@Enumerated(EnumType.ORDINAL)
	private TipoUnidade tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public String getEmailResponsavel() {
		return emailResponsavel;
	}

	public void setEmailResponsavel(String emailResponsavel) {
		this.emailResponsavel = emailResponsavel;
	}

	public String getTelefoneResponsavel() {
		return telefoneResponsavel;
	}

	public void setTelefoneResponsavel(String telefoneResponsavel) {
		this.telefoneResponsavel = telefoneResponsavel;
	}

	public TipoUnidade getTipo() {
		return tipo;
	}

	public void setTipo(TipoUnidade tipo) {
		this.tipo = tipo;
	}

	@Override
	public int compareTo(UnidadeConsumidora o) {
		return getId().compareTo(o.getId());
	}
}