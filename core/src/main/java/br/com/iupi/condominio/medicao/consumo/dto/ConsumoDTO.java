package br.com.iupi.condominio.medicao.consumo.dto;

import java.text.DecimalFormat;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.StringUtils;

import br.com.iupi.condominio.medicao.consumo.modelo.Consumo;

@XmlRootElement
@XmlType(propOrder = { "unidade", "tipo", "dataLeituraAnterior", "medicaoAnterior", "dataLeituraAtual", "medicaoAtual",
		"consumo", "fator", "valorM3", "valor" })
public class ConsumoDTO {

	private static final String NI = "--";
	private Consumo consumo;

	public ConsumoDTO() {
	}

	public ConsumoDTO(Consumo consumo) {
		this.consumo = consumo;
	}

	public String getUnidade() {
		if (consumo.getLeituraAtual() != null && consumo.getLeituraAtual().getMedidor() != null
				&& consumo.getLeituraAtual().getMedidor().getUnidadeConsumidora() != null && StringUtils
						.isNotBlank(consumo.getLeituraAtual().getMedidor().getUnidadeConsumidora().getUnidade())) {
			return consumo.getLeituraAtual().getMedidor().getUnidadeConsumidora().getUnidade();
		}

		return NI;
	}

	public String getTipo() {
		if (consumo.getLeituraAtual() != null && consumo.getLeituraAtual().getMedidor() != null
				&& consumo.getLeituraAtual().getMedidor().getTipo() != null) {
			return consumo.getLeituraAtual().getMedidor().getTipo().getValor();
		}

		return NI;
	}

	public String getDataLeituraAnterior() {
		if (consumo.getLeituraAnterior() != null) {
			return consumo.getLeituraAnterior().getDataLeituraFormatada();
		}

		return NI;
	}

	public Integer getMedicaoAnterior() {
		if (consumo.getLeituraAnterior() != null) {
			return consumo.getLeituraAnterior().getMedido();
		}

		return 0;
	}

	public String getDataLeituraAtual() {
		if (consumo.getLeituraAtual() != null) {
			return consumo.getLeituraAtual().getDataLeituraFormatada();
		}

		return NI;
	}

	public Integer getMedicaoAtual() {
		if (consumo.getLeituraAtual() != null) {
			return consumo.getLeituraAtual().getMedido();
		}

		return 0;
	}

	public Integer getConsumo() {
		return consumo.getMedido();

	}

	public Integer getFator() {
		return consumo.getFator();
	}

	public Double getValorM3() {
		return consumo.getValorM3();
	}

	public String getValor() {
		DecimalFormat df = new DecimalFormat("###.##");

		return df.format(consumo.getValor());
	}
}
