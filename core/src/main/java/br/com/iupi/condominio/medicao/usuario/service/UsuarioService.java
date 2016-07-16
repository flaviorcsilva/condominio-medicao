package br.com.iupi.condominio.medicao.usuario.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.iupi.condominio.medicao.comum.execao.Mensagem;
import br.com.iupi.condominio.medicao.comum.execao.NegocioException;
import br.com.iupi.condominio.medicao.comum.validacao.Assert;
import br.com.iupi.condominio.medicao.usuario.dao.UsuarioDAO;
import br.com.iupi.condominio.medicao.usuario.model.Usuario;

@Stateless
public class UsuarioService {

	@Inject
	private UsuarioDAO dao;

	public String autentica(String login, String senha) {
		validaDadosObrigatoriosParaAutenticacao(login, senha);

		Usuario usuario = this.consultaUsuarioPorLogin(login);

		if (usuario != null) {
			estadoDoUsuarioEhValidoParaAutenticacao(usuario);

			if (senha.equals(usuario.getSenha())) {
				usuario.zeraTentativasIncorretasAutenticacao();
				atualizaDadosDoAcessoDoUsuario(usuario);

				dao.update(usuario);
			} else {
				usuario.addTentativaIncorretaAutenticacao();
				bloqueiaPorTentativasDeAcessoInvalido(usuario);

				throw new NegocioException(Mensagem.USUARIO_LOGIN_SENHA_INVALIDOS);
			}
		} else {
			throw new NegocioException(Mensagem.USUARIO_LOGIN_SENHA_INVALIDOS);
		}

		return usuario.getCondominio();
	}

	private Usuario consultaUsuarioPorLogin(String login) {
		Usuario usuario = null;

		try {
			usuario = dao.consultaPorLogin(login);
		} catch (Exception e) {
			return null;
		}

		return usuario;
	}

	private void validaDadosObrigatoriosParaAutenticacao(String login, String senha) {
		Assert.notBlank(login, Mensagem.CAMPOS_OBRIGATORIOS_NAO_PREENCHIDOS);
		Assert.notBlank(senha, Mensagem.CAMPOS_OBRIGATORIOS_NAO_PREENCHIDOS);
	}

	private void estadoDoUsuarioEhValidoParaAutenticacao(Usuario usuario) {
		if (!usuario.podeAutenticar()) {
			throw new NegocioException(Mensagem.USUARIO_NAO_PODE_ACESSAR_APLICACAO);
		}
	}

	private void atualizaDadosDoAcessoDoUsuario(Usuario usuario) {
		// Atualiza os ados referentes ao novo acesso do usuário na aplicação
	}

	private void bloqueiaPorTentativasDeAcessoInvalido(Usuario usuario) {
		if (usuario.getTentativasIncorretasAutenticacao() > 5) {
			usuario.suspenso();

			dao.update(usuario);

			throw new NegocioException(Mensagem.USUARIO_SUSPENSO);
		}

	}
}