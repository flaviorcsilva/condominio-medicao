package br.com.iupi.condominio.medicao.app;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;

/**
 * Filters to be applied to Requests WebServices (REST).
 */
@Provider
public class ResourcesRequestFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String token = requestContext.getHeaderString("Authorization");

		String clientID = StringUtils.isEmpty(token) ? null : "privilege-noroeste";

		requestContext.getHeaders().add("Client-ID", clientID);
	}
}