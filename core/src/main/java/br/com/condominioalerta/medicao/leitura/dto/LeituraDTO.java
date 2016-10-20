package br.com.condominioalerta.medicao.leitura.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.StringUtils;

import br.com.condominioalerta.medicao.leitura.model.Leitura;

@XmlRootElement
@XmlType(propOrder = { "id", "data", "unidade", "tipo", "medidor", "medido", "foto" })
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

	public String getData() {
		if (StringUtils.isNotBlank(leitura.getDataLeituraFormatada())) {
			return leitura.getDataLeituraFormatada();
		}

		return StringUtils.EMPTY;
	}

	public String getUnidade() {
		if (leitura.getMedidor() != null && leitura.getMedidor().getUnidadeConsumidora() != null
				&& StringUtils.isNotBlank(leitura.getMedidor().getUnidadeConsumidora().getUnidade())) {
			return leitura.getMedidor().getUnidadeConsumidora().getUnidade();
		}

		return NI;
	}

	public Integer getTipo() {
		if (leitura.getMedidor() != null && leitura.getMedidor().getTipo() != null) {
			return leitura.getMedidor().getTipo().getChave();
		}

		return 0;
	}

	public String getMedidor() {
		if (leitura.getMedidor() != null && StringUtils.isNotBlank(leitura.getMedidor().getNumero())) {
			return leitura.getMedidor().getNumero();
		}

		return NI;
	}

	public Integer getMedido() {
		return leitura.getMedido();
	}
	
	public byte[] getFoto() {
		return leitura.getFoto();
	}

	public int compareTo(LeituraDTO o) {
		return getId().compareTo(o.getId());
	}
}