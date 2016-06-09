package br.com.iupi.condominio.medicao.leitura.ws;

import java.net.URI;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import br.com.iupi.condominio.medicao.comum.execao.Mensagem;
import br.com.iupi.condominio.medicao.comum.execao.NegocioException;
import br.com.iupi.condominio.medicao.comum.helper.DataHelper;
import br.com.iupi.condominio.medicao.leitura.dto.LeituraDTO;
import br.com.iupi.condominio.medicao.leitura.modelo.Leitura;
import br.com.iupi.condominio.medicao.leitura.service.LeituraService;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedidor;

@Path("/leitura")
@Stateless
public class LeituraResource {

	@Inject
	private LeituraService service;

	@GET
	@Path("/{unidade}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public LeituraDTO leitura(@PathParam("unidade") String unidade, @PathParam("id") Long id) {
		Leitura leitura = service.consultaLeitura(id);

		return new LeituraDTO(leitura);
	}

	@GET
	@Path("/{unidade}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<LeituraDTO> leituras(@PathParam("unidade") String unidade, @QueryParam("mes") Integer mes,
			@QueryParam("ano") Integer ano) {
		YearMonth mesAno = YearMonth.of(ano, mes);
		LocalDate inicioMes = mesAno.atDay(1);
		LocalDate finalMes = mesAno.atEndOfMonth();

		List<Leitura> leituras = service.consultaLeituras(unidade, inicioMes, finalMes);

		List<LeituraDTO> lista = new ArrayList<LeituraDTO>();
		for (Leitura leitura : leituras) {
			lista.add(new LeituraDTO(leitura));
		}

		return lista;
	}

	@POST
	@Path("/{unidade}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response leitura(@PathParam("unidade") String unidade, @QueryParam("data") String data,
			@QueryParam("tipo") Integer tipo, @QueryParam("medido") Integer medido) {
		Leitura leitura = null;

		try {
			// valida o formato e a data informada
			LocalDate dataLeitura = DataHelper.convertStringToLocalDate(data);

			// valida o tipo da leitura
			TipoMedidor tipoMedidor = TipoMedidor.get(tipo);
			if (tipoMedidor == null) {
				throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
						.entity(Mensagem.LEITURA_TIPO_INVALIDO.getMensagem()).encoding("UTF-8").build());
			}

			Long id = service.registraLeitura(unidade, dataLeitura, tipoMedidor, medido);
			leitura = service.consultaLeitura(id);

			System.out
					.println("Leitura de " + tipo + " da unidade " + unidade + " em " + dataLeitura + " foi " + medido);
		} catch (NegocioException ne) {
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
					.entity(ne.getMensagem().getMensagem()).encoding("UTF-8").build());
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).encoding("UTF-8").build());
		}

		URI uri = UriBuilder.fromPath("leitura/" + unidade + "/{id}").build(leitura.getId().intValue());

		return Response.created(uri).entity(new LeituraDTO(leitura)).type(MediaType.APPLICATION_JSON_TYPE).build();
	}
}