package br.com.condominioalerta.medicao.consumo.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.condominioalerta.medicao.comum.converter.TipoMedicaoConverter;
import br.com.condominioalerta.medicao.comum.persistencia.Entidade;
import br.com.condominioalerta.medicao.medidor.model.TipoMedicao;

@Entity
@Cacheable
@Table(name = "consumo")
public class ConsumoCondominio extends Entidade {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_consumo")
	private Long id;

	@Column(name = "cd_condominio", nullable = false)
	private String condominio;

	@Column(name = "dt_consumo", nullable = false)
	private String anoMes;

	@Column(name = "tp_medicao", nullable = false)
	@Convert(converter = TipoMedicaoConverter.class)
	private TipoMedicao tipoMedicao;
	
	@Column(name = "vl_medido_fatura")
	private Integer valorMedidoFatura;

	@Column(name = "vl_total_faturado")
	private Double valorTotalFaturado;

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

	public String getAnoMes() {
		return anoMes;
	}
	
	public Integer getMes() {
		return Integer.parseInt(anoMes.substring(4));
	}
	
	public Integer getAno() {
		return Integer.parseInt(anoMes.substring(0, 4));
	}

	public void setAnoMes(String anoMes) {
		this.anoMes = anoMes;
	}

	public TipoMedicao getTipoMedicao() {
		return tipoMedicao;
	}

	public void setTipoMedicao(TipoMedicao tipoMedicao) {
		this.tipoMedicao = tipoMedicao;
	}

	public Integer getValorMedidoFatura() {
		return valorMedidoFatura;
	}

	public void setValorMedidoFatura(Integer valorMedidoFatura) {
		this.valorMedidoFatura = valorMedidoFatura;
	}
	
	public Double getValorTotalFaturado() {
		return valorTotalFaturado;
	}

	public void setValorTotalFaturado(Double valorTotalFaturado) {
		this.valorTotalFaturado = valorTotalFaturado;
	}

	public Double getValorM3() {
		if (valorMedidoFatura == null || valorMedidoFatura == 0) {
			return 0.0;
		}

		return valorTotalFaturado / valorMedidoFatura;
	}
}