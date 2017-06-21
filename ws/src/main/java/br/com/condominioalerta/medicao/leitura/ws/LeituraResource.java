package br.com.condominioalerta.medicao.leitura.ws;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import br.com.condominioalerta.medicao.comum.execao.Mensagem;
import br.com.condominioalerta.medicao.comum.helper.DataHelper;
import br.com.condominioalerta.medicao.leitura.dto.LeituraDTO;
import br.com.condominioalerta.medicao.leitura.model.Leitura;
import br.com.condominioalerta.medicao.leitura.service.LeituraService;
import br.com.condominioalerta.medicao.medidor.model.TipoMedicao;

@Path("/leitura")
@Stateless
public class LeituraResource {

	@Inject
	private LeituraService service;

	@POST
	@Path("/{unidade}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response registraLeitura(@HeaderParam("Condominio-ID") String condominio,
			@PathParam("unidade") String unidade, @QueryParam("data") String data, @QueryParam("tipo") Integer tipo,
			@QueryParam("medido") Integer medido, byte[] foto) {
		Leitura leitura = null;

		try {
			// valida o formato e a data informada
			LocalDate dataLeitura = DataHelper.converteStringToLocalDate(data);

			// valida o tipo da leitura
			TipoMedicao tipoMedicao = TipoMedicao.get(tipo);
			if (tipoMedicao == null) {
				throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
						.entity(Mensagem.LEITURA_TIPO_INVALIDO.getMensagem()).build());
			}

			leitura = service.registraLeitura(condominio, unidade, dataLeitura, tipoMedicao, medido, foto);

			System.out
					.println("Leitura de " + tipo + " da unidade " + unidade + " em " + dataLeitura + " foi " + medido);
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build());
		}

		URI uri = UriBuilder.fromPath("leitura/" + unidade + "/{id}").build(leitura.getId().intValue());

		return Response.created(uri).entity(new LeituraDTO(leitura)).type(MediaType.APPLICATION_JSON_TYPE).build();
	}

	@PUT
	@Path("/{id}")
	public Response atualizaLeitura(@HeaderParam("Condominio-ID") String condominio, @PathParam("id") Long id,
			@QueryParam("medido") Integer medido, byte[] foto) {
		Leitura leitura = null;

		try {
			leitura = service.consultaLeitura(id);
			leitura.setMedido(medido);
			leitura.setFoto(foto);

			service.atualizaLeitura(leitura);
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build());
		}

		return Response.ok().build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaLeitura(@PathParam("id") Long id) {
		Leitura leitura = service.consultaLeitura(id);

		LeituraDTO dto = new LeituraDTO(leitura);

		return Response.ok(dto).build();
	}
	
	@GET
	@Path("/foto/{id}")
	@Produces("image/*")
	public Response consultaFotoLeitura(@PathParam("id") Long id) {
		Leitura leitura = service.consultaLeitura(id);

		return Response.ok(leitura.getFoto()).type("image/png").build();
	}

	@GET
	@Path("/{unidade}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaLeiturasPorUnidade(@HeaderParam("Condominio-ID") String condominio,
			@PathParam("unidade") String unidade, @QueryParam("mes") Integer mes, @QueryParam("ano") Integer ano) {
		LocalDate inicioMes = DataHelper.getInicioDeMes(mes, ano);
		LocalDate finalMes = DataHelper.getFinalDeMes(mes, ano);

		List<Leitura> leituras = service.consultaLeituras(condominio, unidade, inicioMes, finalMes);

		List<LeituraDTO> lista = new ArrayList<LeituraDTO>();
		for (Leitura leitura : leituras) {
			lista.add(new LeituraDTO(leitura));
		}

		return Response.ok().entity(lista).build();
	}

	@GET
	@Path("/periodo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaLeiturasPorPeriodo(@HeaderParam("Condominio-ID") String condominio,
			@QueryParam("mes") Integer mes, @QueryParam("ano") Integer ano) {
		LocalDate inicioMes = DataHelper.getInicioDeMes(mes, ano);
		LocalDate finalMes = DataHelper.getFinalDeMes(mes, ano);

		List<Leitura> leituras = service.consultaLeituras(condominio, inicioMes, finalMes);

		List<LeituraDTO> lista = new ArrayList<LeituraDTO>();
		for (Leitura leitura : leituras) {
			lista.add(new LeituraDTO(leitura));
		}

		return Response.ok().entity(lista).build();
	}

	@POST
	@Path("/periodo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response registraLeiturasPorPeriodo(@HeaderParam("Condominio-ID") String condominio,
			@QueryParam("mes") Integer mes, @QueryParam("ano") Integer ano, List<LeituraDTO> leituras) {

		return null;
	}
}