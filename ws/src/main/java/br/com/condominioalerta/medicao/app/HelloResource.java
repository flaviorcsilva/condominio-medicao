package br.com.condominioalerta.medicao.app;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
@Stateless
public class HelloResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response hello(@HeaderParam("Condominio-ID") String condominio) {
		String hello = "Hello " + condominio;
		
		return Response.ok().entity(hello).build();
	}
}