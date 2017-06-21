package br.com.condominioalerta.medicao.unidade.ws;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import br.com.condominioalerta.medicao.unidade.model.UnidadeConsumidora;
import br.com.condominioalerta.medicao.unidade.service.UnidadeConsumidoraService;

@Path("/unidade")
@Stateless
public class UnidadeResource {

	@Inject
	private UnidadeConsumidoraService service;

	@GET
	@Path("/lista")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaLista(@HeaderParam("Condominio-ID") String condominio) {
		List<String> unidades = new ArrayList<String>();

		try {
			unidades = service.consultaListaDeUnidadesPorCondominio(condominio);
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build());
		}

		return Response.ok().entity(unidades).build();
	}

	@POST
	@Path("/{unidade}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response registraUnidade(@HeaderParam("Condominio-ID") String condominio,
			@PathParam("unidade") String unidade, @QueryParam("responsavel") String responsavel,
			@QueryParam("email") String email, @QueryParam("tipo") Integer tipo) {
		UnidadeConsumidora unidadeConsumidora = new UnidadeConsumidora();

		try {

		} catch (Exception e) {
			throw new WebApplicationException(
					Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build());
		}

		URI uri = UriBuilder.fromPath("unidade/" + unidade + "/{id}").build(unidadeConsumidora.getId().intValue());

		return Response.created(uri).entity(unidadeConsumidora).type(MediaType.APPLICATION_JSON_TYPE).build();
	}
}