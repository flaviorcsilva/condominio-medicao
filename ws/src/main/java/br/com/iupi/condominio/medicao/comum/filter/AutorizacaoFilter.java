package br.com.iupi.condominio.medicao.comum.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;

/**
 * Filters to be applied to Requests WebServices (REST).
 */
@Provider
public class AutorizacaoFilter implements ContainerRequestFilter {
	
	private Response unauthorized = Response.status(Status.UNAUTHORIZED).build();

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		/*
		if (requestContext.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS)) {
			requestContext.abortWith(Response.ok().build());
			return;
		}

		String autorizacao = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		if (StringUtils.isEmpty(autorizacao) || autorizacao.startsWith("Basic ") == false) {
			requestContext.abortWith(unauthorized);
			return;
		}

		String token = new String(Base64.getDecoder().decode(autorizacao.substring("Basic ".length())),
				StandardCharsets.UTF_8);

		String[] login = token.split(":");

		requestContext.getHeaders().add("Condominio-ID", login[1]);
		*/
	}
}