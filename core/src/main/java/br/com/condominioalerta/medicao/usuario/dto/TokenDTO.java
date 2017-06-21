package br.com.condominioalerta.medicao.usuario.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.StringUtils;

import br.com.condominioalerta.medicao.usuario.model.Usuario;

@XmlRootElement
@XmlType(propOrder = { "perfil", "condominio" })
public class TokenDTO {

	private Usuario usuario;

	public TokenDTO() {
	}

	public TokenDTO(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getPerfil() {
		if (StringUtils.isNotBlank(usuario.getPerfil().toString())) {
			return usuario.getPerfil().toString();
		}

		return StringUtils.EMPTY;
	}

	public String getCondominio() {
		if (StringUtils.isNotBlank(usuario.getCondominio())) {
			return usuario.getCondominio();
		}

		return StringUtils.EMPTY;
	}
}