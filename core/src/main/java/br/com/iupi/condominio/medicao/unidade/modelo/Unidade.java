package br.com.iupi.condominio.medicao.unidade.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import br.com.iupi.condominio.medicao.comum.persistencia.Entidade;
import br.com.iupi.condominio.medicao.medidor.modelo.Medidor;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedidor;

@Entity
public class Unidade extends Entidade {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_unidade")
	private Long id;

	@Column(name = "cd_condominio")
	private String condominio;

	@Column(name = "cd_unidade")
	private String unidade;

	@Column(name = "ds_unidade")
	private String descricao;

	@OneToMany(mappedBy = "numero")
	private List<Medidor> medidores = new ArrayList<Medidor>();

	public Unidade() {
		//
	}

	public Unidade(String unidade, String descricao) {
		super();
		this.condominio = "Privilege Noroeste";
		this.unidade = unidade;
		this.descricao = descricao;
		this.medidores = new ArrayList<Medidor>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCondominio() {
		return condominio;
	}

	public void setCondominio(String condominio) {
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

	public List<Medidor> getMedidores() {
		return medidores;
	}

	public Medidor getMedidor(TipoMedidor tipo) {
		for (Medidor medidor : medidores) {
			if (medidor.getTipo().equals(tipo)) {
				return medidor;
			}
		}

		return null;
	}

	public void addMedidor(Medidor medidor) {
		/* Verificar se o numero do medidor já está contido na lista */
		/* Não se pode adicionar mais de um medidor do mesmo tipo */
		if (!medidores.contains(medidor)) {
			medidores.add(medidor);
		}
	}
}
