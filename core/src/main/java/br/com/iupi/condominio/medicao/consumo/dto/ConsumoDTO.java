package br.com.iupi.condominio.medicao.consumo.dto;

import java.text.DecimalFormat;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.StringUtils;

import br.com.iupi.condominio.medicao.consumo.modelo.ConsumoUnidade;

@XmlRootElement
@XmlType(propOrder = { "unidade", "tipoMedicao", "dataLeituraAnterior", "medicaoAnterior", "dataLeituraAtual",
		"medicaoAtual", "consumo", "fator", "valorM3", "valorAPagar" })
public class ConsumoDTO {

	private final DecimalFormat df2casas = new DecimalFormat("###.00");
	private final DecimalFormat df5casas = new DecimalFormat("###.00###");
	private static final String NI = "--";
	private ConsumoUnidade consumoUnidade;

	public ConsumoDTO() {
	}

	public ConsumoDTO(ConsumoUnidade consumoUnidade) {
		this.consumoUnidade = consumoUnidade;
	}

	public String getUnidade() {
		if (consumoUnidade.getLeituraAtual() != null && consumoUnidade.getLeituraAtual().getMedidor() != null
				&& consumoUnidade.getLeituraAtual().getMedidor().getUnidadeConsumidora() != null
				&& StringUtils.isNotBlank(
						consumoUnidade.getLeituraAtual().getMedidor().getUnidadeConsumidora().getUnidade())) {
			return consumoUnidade.getLeituraAtual().getMedidor().getUnidadeConsumidora().getUnidade();
		}

		return NI;
	}

	public String getTipoMedicao() {
		if (consumoUnidade.getLeituraAtual() != null && consumoUnidade.getLeituraAtual().getMedidor() != null
				&& consumoUnidade.getLeituraAtual().getMedidor().getTipo() != null) {
			return consumoUnidade.getLeituraAtual().getMedidor().getTipo().getValor();
		}

		return NI;
	}

	public String getDataLeituraAnterior() {
		if (consumoUnidade.getLeituraAnterior() != null) {
			return consumoUnidade.getLeituraAnterior().getDataLeituraFormatada();
		}

		return NI;
	}

	public Integer getMedicaoAnterior() {
		if (consumoUnidade.getLeituraAnterior() != null) {
			return consumoUnidade.getLeituraAnterior().getMedido();
		}

		return 0;
	}

	public String getDataLeituraAtual() {
		if (consumoUnidade.getLeituraAtual() != null) {
			return consumoUnidade.getLeituraAtual().getDataLeituraFormatada();
		}

		return NI;
	}

	public Integer getMedicaoAtual() {
		if (consumoUnidade.getLeituraAtual() != null) {
			return consumoUnidade.getLeituraAtual().getMedido();
		}

		return 0;
	}

	public Integer getConsumo() {
		return consumoUnidade.getMedido();
	}

	public Integer getFator() {
		return consumoUnidade.getFator();
	}

	public String getValorM3() {
		return df5casas.format(consumoUnidade.getValorM3());
	}

	public String getValorAPagar() {
		return df2casas.format(consumoUnidade.getValor());
	}

	@Override
	public String toString() {
		return getUnidade() + ";" + getTipoMedicao() + ";" + getDataLeituraAnterior() + ";" + getMedicaoAnterior() + ";"
				+ getDataLeituraAtual() + ";" + getMedicaoAtual() + ";" + getConsumo() + ";" + getFator() + ";"
				+ getValorM3() + ";" + getValorAPagar();
	}
}
