package br.com.iupi.condominio.medicao.leitura.dto;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

import br.com.iupi.condominio.medicao.leitura.modelo.Leitura;

@XmlRootElement
public class LeituraDTO implements Comparable<LeituraDTO> {

	private static final String NI = "--";
	private Leitura leitura;

	public LeituraDTO() {
	}

	public LeituraDTO(Leitura leitura) {
		this.leitura = leitura;
	}

	public Long getId() {
		return leitura.getId();
	}

	public String getUnidade() {
		if (leitura.getMedidor() != null && leitura.getMedidor().getUnidadeConsumidora() != null
				&& StringUtils.isNotBlank(leitura.getMedidor().getUnidadeConsumidora().getUnidade())) {
			return leitura.getMedidor().getUnidadeConsumidora().getUnidade();
		}

		return NI;
	}

	public String getTipoMedidor() {
		if (leitura.getMedidor() != null && leitura.getMedidor().getTipo() != null) {
			return leitura.getMedidor().getTipo().getValor();
		}

		return NI;
	}

	public String getNumeroMedidor() {
		if (leitura.getMedidor() != null && StringUtils.isNotBlank(leitura.getMedidor().getNumero())) {
			return leitura.getMedidor().getNumero();
		}

		return NI;
	}

	public String getDataLeitura() {
		if (StringUtils.isNotBlank(leitura.getDataLeituraFormatada())) {
			return leitura.getDataLeituraFormatada();
		}

		return StringUtils.EMPTY;
	}

	public Integer getMedido() {
		return leitura.getMedido();

	}

	public int compareTo(LeituraDTO o) {
		return getId().compareTo(o.getId());
	}
}
