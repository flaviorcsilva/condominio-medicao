package br.com.condominioalerta.medicao.comum.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

/**
 * Filters to be applied to WebServices (REST).
 * 
 * @author flaviocruz
 */
@Provider
public class ResourcesResponseFilter implements ContainerResponseFilter {
	
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {

		MultivaluedMap<String, Object> headers = responseContext.getHeaders();

		// *(allow from all servers) OR http://visent.com.br/ OR
		// http://example.com/
		headers.add("Access-Control-Allow-Origin", "*");

		// As a part of the response to a request, which HTTP methods
		// can be used during the actual request.
		headers.add("Access-Control-Allow-Methods", "GET");

		// As part of the response to a request, which HTTP headers can
		// be used during the actual request.
		headers.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type");

		// How long the results of a request can be cached in a result
		// cache.
		headers.add("Access-Control-Max-Age", "151200");
	}
}