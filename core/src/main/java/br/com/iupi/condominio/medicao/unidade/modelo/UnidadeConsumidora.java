package br.com.iupi.condominio.medicao.unidade.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.iupi.condominio.medicao.comum.persistencia.Entidade;
import br.com.iupi.condominio.medicao.condominio.modelo.Condominio;

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

	@Override
	public int compareTo(UnidadeConsumidora o) {
		return getId().compareTo(o.getId());
	}
}