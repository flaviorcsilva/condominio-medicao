package br.com.condominioalerta.medicao.consumo.ws;

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

import br.com.condominioalerta.medicao.comum.execao.Mensagem;
import br.com.condominioalerta.medicao.comum.helper.DataHelper;
import br.com.condominioalerta.medicao.consumo.dto.ConsumoCondominioDTO;
import br.com.condominioalerta.medicao.consumo.dto.ConsumoDTO;
import br.com.condominioalerta.medicao.consumo.model.ConsumoCondominio;
import br.com.condominioalerta.medicao.consumo.model.ConsumoUnidade;
import br.com.condominioalerta.medicao.consumo.service.ConsumoService;
import br.com.condominioalerta.medicao.medidor.model.TipoMedicao;
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
	public Response calculaConsumoUnidade(@HeaderParam("Condominio-ID") String condominio,
			@PathParam("unidade") String unidade, @QueryParam("mes") Integer mes, @QueryParam("ano") Integer ano) {
		List<ConsumoDTO> consumos = new ArrayList<ConsumoDTO>();

		try {
			UnidadeConsumidora unidadeConsumidora = unidadeConsumidoraService.consultaUnidadeConsumidora(condominio,
					unidade);

			List<ConsumoUnidade> consumosUnidade = service.calculaConsumos(unidadeConsumidora, mes, ano);

			for (ConsumoUnidade consumoUnidade : consumosUnidade) {
				consumos.add(new ConsumoDTO(consumoUnidade));
			}
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build());
		}

		return Response.ok().entity(consumos).build();
	}

	@GET
	@Path("/unidades")
	@Produces(MediaType.APPLICATION_JSON)
	public Response calculaConsumoUnidades(@HeaderParam("Condominio-ID") String condominio,
			@QueryParam("mes") Integer mes, @QueryParam("ano") Integer ano) {
		List<ConsumoDTO> consumos = new ArrayList<ConsumoDTO>();

		try {
			List<ConsumoUnidade> consumosUnidade = service.calculaConsumos(condominio, mes, ano);

			for (ConsumoUnidade consumo : consumosUnidade) {
				consumos.add(new ConsumoDTO(consumo));
			}
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build());
		}

		return Response.ok().entity(consumos).build();
	}

	@GET
	@Path("/arquivo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response geraArquivoConsumo(@HeaderParam("Condominio-ID") String condominio, @QueryParam("mes") Integer mes,
			@QueryParam("ano") Integer ano) {
		try {
			service.geraArquivo(condominio, mes, ano);
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build());
		}

		return Response.ok().build();
	}

	@GET
	@Path("/envia")
	@Produces(MediaType.APPLICATION_JSON)
	public Response enviaConsumos(@HeaderParam("Condominio-ID") String condominio, @QueryParam("mes") Integer mes,
			@QueryParam("ano") Integer ano) {
		try {
			service.enviaConsumo(condominio, mes, ano);
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build());
		}

		return Response.ok().build();
	}

	@GET
	@Path("/{unidade}/envia")
	@Produces(MediaType.APPLICATION_JSON)
	public Response enviaConsumoUnidade(@HeaderParam("Condominio-ID") String condominio,
			@PathParam("unidade") String unidade, @QueryParam("mes") Integer mes, @QueryParam("ano") Integer ano) {
		try {
			UnidadeConsumidora unidadeConsumidora = unidadeConsumidoraService.consultaUnidadeConsumidora(condominio,
					unidade);

			service.enviaConsumo(unidadeConsumidora, mes, ano);
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build());
		}

		return Response.ok().build();
	}

	@POST
	@Path("/condominio")
	@Produces(MediaType.APPLICATION_JSON)
	public Response registraConsumo(@HeaderParam("Condominio-ID") String condominio, @QueryParam("mes") Integer mes,
			@QueryParam("ano") Integer ano, @QueryParam("tipo") Integer tipo, @QueryParam("medido") Integer medido,
			@QueryParam("faturado") Double faturado) {
		try {
			// valida o tipo da leitura
			TipoMedicao tipoMedicao = TipoMedicao.get(tipo);
			if (tipoMedicao == null) {
				throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
						.entity(Mensagem.LEITURA_TIPO_INVALIDO.getMensagem()).build());
			}

			ConsumoCondominio consumoCondominio = new ConsumoCondominio();

			consumoCondominio.setCondominio(condominio);
			
			String anoMes = DataHelper.converteMesAnoToStringAnoMes(mes, ano);
			consumoCondominio.setAnoMes(anoMes);
			
			consumoCondominio.setTipoMedicao(tipoMedicao);
			consumoCondominio.setValorMedidoFatura(medido);
			consumoCondominio.setValorTotalFaturado(faturado);

			service.registraConsumoCondominio(consumoCondominio);
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build());
		}

		return Response.ok().build();
	}
	
	@GET
	@Path("/condominio")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaConsumos(@HeaderParam("Condominio-ID") String condominio) {
		List<ConsumoCondominio> consumosCondominio = service.consultaConsumosDoCondominio(condominio);

		List<ConsumoCondominioDTO> lista = new ArrayList<ConsumoCondominioDTO>();
		for (ConsumoCondominio consumoCondominio : consumosCondominio) {
			lista.add(new ConsumoCondominioDTO(consumoCondominio));
		}

		return Response.ok().entity(lista).build();
	}
}