package br.com.condominioalerta.medicao.consumo.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import br.com.condominioalerta.medicao.comum.helper.NumeroHelper;
import br.com.condominioalerta.medicao.consumo.model.ConsumoCondominio;

@XmlRootElement
@XmlType(propOrder = { "mes", "ano", "tipo", "medido", "faturado", "valorM3"})
public class ConsumoCondominioDTO {

	private ConsumoCondominio consumoCondominio;

	public ConsumoCondominioDTO() {
	}

	public ConsumoCondominioDTO(ConsumoCondominio consumoCondominio) {
		this.consumoCondominio = consumoCondominio;
	}

	public Integer getMes() {
		return consumoCondominio.getMes();
	}

	public Integer getAno() {
		return consumoCondominio.getAno();
	}

	public Integer getTipo() {
		if (consumoCondominio.getTipoMedicao() != null) {
			return consumoCondominio.getTipoMedicao().getChave();
		}

		return 0;
	}

	public Integer getMedido() {
		return consumoCondominio.getValorMedidoFatura();
	}

	public String getFaturado() {
		return NumeroHelper.formataNumeroTo2Decimais(consumoCondominio.getValorTotalFaturado());
	}

	public String getValorM3() {
		return NumeroHelper.formataNumeroTo5Decimais(consumoCondominio.getValorM3());
	}

	@Override
	public String toString() {
		return getMes() + ";" + getAno() + ";" + getTipo() + ";" + getMedido() + ";" + getFaturado() + ";"
				+ getValorM3();
	}
}
