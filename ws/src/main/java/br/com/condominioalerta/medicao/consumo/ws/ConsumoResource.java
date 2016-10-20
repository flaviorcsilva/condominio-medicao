package br.com.condominioalerta.medicao.consumo.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.condominioalerta.medicao.comum.execao.NegocioException;
import br.com.condominioalerta.medicao.consumo.dto.ConsumoDTO;
import br.com.condominioalerta.medicao.consumo.model.ConsumoUnidade;
import br.com.condominioalerta.medicao.consumo.service.ConsumoService;
import br.com.condominioalerta.medicao.unidade.model.UnidadeConsumidora;
import br.com.condominioalerta.medicao.unidade.service.UnidadeConsumidoraService;

@Path("/consumo")
@Stateless
public class ConsumoResource {

	@Inject
	private ConsumoService service;

	@Inject
	private UnidadeConsumidoraService unidadeConsumidoraService;

	@GET
	@Path("/{unidade}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consumo(@HeaderParam("Condominio-ID") String condominio, @PathParam("unidade") String unidade,
			@QueryParam("mes") Integer mes, @QueryParam("ano") Integer ano) {
		UnidadeConsumidora unidadeConsumidora = unidadeConsumidoraService.consultaUnidadeConsumidora(condominio,
				unidade);

		List<ConsumoUnidade> consumosUnidade = service.calculaConsumosDoMesAnoPorUnidade(unidadeConsumidora, mes, ano);

		List<ConsumoDTO> consumos = new ArrayList<ConsumoDTO>();
		for (ConsumoUnidade consumoUnidade : consumosUnidade) {
			consumos.add(new ConsumoDTO(consumoUnidade));
		}

		return Response.ok().entity(consumos).build();
	}

	@GET
	@Path("/unidades")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consumos(@HeaderParam("Condominio-ID") String condominio, @QueryParam("mes") Integer mes,
			@QueryParam("ano") Integer ano) {
		List<ConsumoUnidade> consumosUnidade = service.calculaConsumos(condominio, mes, ano);

		List<ConsumoDTO> consumos = new ArrayList<ConsumoDTO>();
		for (ConsumoUnidade consumo : consumosUnidade) {
			consumos.add(new ConsumoDTO(consumo));
		}

		return Response.ok().entity(consumos).build();
	}

	@GET
	@Path("/arquivo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response arquivo(@HeaderParam("Condominio-ID") String condominio, @QueryParam("mes") Integer mes,
			@QueryParam("ano") Integer ano) {
		service.geraArquivo(condominio, mes, ano);

		return Response.ok().build();
	}

	@GET
	@Path("/{unidade}/envia")
	@Produces(MediaType.APPLICATION_JSON)
	public Response envia(@HeaderParam("Condominio-ID") String condominio, @PathParam("unidade") String unidade,
			@QueryParam("mes") Integer mes, @QueryParam("ano") Integer ano) {
		try {
			UnidadeConsumidora unidadeConsumidora = unidadeConsumidoraService.consultaUnidadeConsumidora(condominio,
					unidade);

			service.enviaConsumo(unidadeConsumidora, mes, ano);
		} catch (NegocioException ne) {
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
					.entity(ne.getMensagem().getMensagem()).encoding("UTF-8").build());
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).encoding("UTF-8").build());
		}

		return Response.ok().build();
	}
}