package br.com.condominioalerta.medicao.comum.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.naming.InitialContext;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;

import br.com.condominioalerta.medicao.comum.params.Params;
import br.com.condominioalerta.medicao.usuario.model.Usuario;
import br.com.condominioalerta.medicao.usuario.service.UsuarioService;

/**
 * Filters to be applied to Authentication Requests WebServices (REST).
 */
@Provider
public class AutorizacaoFilter implements ContainerRequestFilter {

	private static final Response UNAUTHORIZED = Response.status(Status.UNAUTHORIZED).build();
	private static final String BASIC = "Basic ";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		if (requestContext.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS)) {
			requestContext.abortWith(Response.ok().build());
			return;
		}

		// Teste de keep alive
		if (requestContext.getUriInfo().getPath().equals("/hello")) {
			requestContext.getHeaders().add(Params.CONDOMINIO_ID.getParam(), "Condominio Alerta");
			return;
		}

		String autorizacao = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		if (StringUtils.isEmpty(autorizacao) || autorizacao.startsWith(BASIC) == false) {
			requestContext.abortWith(UNAUTHORIZED);
			return;
		}

		String token = new String(Base64.getDecoder().decode(autorizacao.substring(BASIC.length())),
				StandardCharsets.UTF_8);

		String[] dados = token.split(":");
		String login = dados[0];
		String senha = dados[1];

		String condominio = null;
		UsuarioService service = null;
		InitialContext context;
		try {
			context = new InitialContext();
			service = (UsuarioService) context.lookup(
					"java:global/medicao-ear/core/UsuarioService!br.com.condominioalerta.medicao.usuario.service.UsuarioService");
			Usuario usuario = service.autentica(login, senha);

			// Caso seja apenas login
			if (requestContext.getUriInfo().getPath().equals("/usuario/login")) {
				requestContext.abortWith(Response.ok(usuario.getPerfil().toString()).build());
				//TokenDTO tokenDTO = new TokenDTO(usuario);
				//requestContext.abortWith(Response.ok(tokenDTO).build());
				return;
			} else {
				condominio = usuario.getCondominio();
			}
		} catch (Exception e) {
			e.printStackTrace();
			requestContext.abortWith(UNAUTHORIZED);
			return;
		}

		requestContext.getHeaders().add(Params.CONDOMINIO_ID.getParam(), condominio);
	}
}