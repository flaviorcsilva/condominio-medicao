package br.com.iupi.condominio.medicao.medidor.dto;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

import br.com.iupi.condominio.medicao.medidor.modelo.Medidor;

@XmlRootElement
public class MedidorDTO implements Comparable<MedidorDTO> {

	private static final String NI = "--";
	private Medidor medidor;

	public MedidorDTO() {
	}

	public MedidorDTO(Medidor medidor) {
		this.medidor = medidor;
	}

	public String getNumero() {
		if (StringUtils.isNotBlank(medidor.getNumero())) {
			return medidor.getNumero();
		}

		return NI;
	}

	public String getTipo() {
		if (medidor.getTipo() != null) {
			return medidor.getTipo().toString();
		}

		return NI;
	}

	public String getUnidade() {
		if (medidor.getUnidadeConsumidora() != null && StringUtils.isNotBlank(medidor.getUnidadeConsumidora().getUnidade())) {
			return medidor.getUnidadeConsumidora().getUnidade();
		}

		return NI;
	}

	public int compareTo(MedidorDTO o) {
		return getNumero().compareTo(o.getNumero());
	}
}
