package br.com.condominioalerta.medicao.usuario.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.condominioalerta.medicao.comum.persistencia.Entidade;

@Entity
@Cacheable
@Table(name = "usuario")
public class Usuario extends Entidade {

	@Id
	@Column(name = "cd_login", nullable = false)
	private String login;

	@Column(name = "tx_senha", nullable = false)
	private String senha;

	@Column(name = "tp_perfil")
	@Enumerated(EnumType.ORDINAL)
	private PerfilUsuario perfil;

	@Column(name = "st_estado")
	@Enumerated(EnumType.ORDINAL)
	private EstadoUsuario estado;

	@Column(name = "nu_tentativas_incorretas_autenticacao")
	private Integer tentativasIncorretasAutenticacao;

	@Column(name = "cd_condominio", nullable = false)
	private String condominio;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public PerfilUsuario getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilUsuario perfil) {
		this.perfil = perfil;
	}

	public EstadoUsuario getEstado() {
		return estado;
	}

	public String getCondominio() {
		return condominio;
	}

	public void setCondominio(String condominio) {
		this.condominio = condominio;
	}

	public void novoBloqueado() {
		this.estado = EstadoUsuario.NOVO_BLOQUEADO;
	}

	public void suspenso() {
		this.estado = EstadoUsuario.SUSPENSO;
	}

	public Integer getTentativasIncorretasAutenticacao() {
		return tentativasIncorretasAutenticacao;
	}

	public void addTentativaIncorretaAutenticacao() {
		this.tentativasIncorretasAutenticacao = tentativasIncorretasAutenticacao + 1;
	}

	public void zeraTentativasIncorretasAutenticacao() {
		this.tentativasIncorretasAutenticacao = 0;
	}

	public boolean podeAutenticar() {
		if (estado.equals(EstadoUsuario.BLOQUEADO) || estado.equals(EstadoUsuario.SUSPENSO)) {
			return false;
		}

		return true;
	}

	public boolean podeBloquear() {
		return estado.equals(EstadoUsuario.ATIVO);
	}

	public boolean podeDesbloquear() {
		return estado.equals(EstadoUsuario.BLOQUEADO);
	}

	public boolean podeRedefinirSenha() {
		return estado.equals(EstadoUsuario.ATIVO);
	}
}