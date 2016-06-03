package br.com.iupi.condominio.medicao.leitura.ws;

import java.time.LocalDate;

import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.iupi.condominio.medicao.helper.DataHelper;
import br.com.iupi.condominio.medicao.leitura.service.LeituraService;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedidor;
import br.com.iupi.condominio.medicao.unidade.service.UnidadeService;

@Path("/leitura")
@Stateless
public class LeituraResource {

	@POST
	@Path("/{unidade}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response leitura(@PathParam("unidade") String unidade, @QueryParam("data") String data,
			@QueryParam("tipo") Integer tipo, @QueryParam("medido") Integer medido) {

		if (UnidadeService.consultaUnidade(unidade) == null) {
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
					.entity("Unidade que foi medida não existe nesse condomínio.").build());
		}

		// valida o formato e a data informada
		LocalDate dataLeitura = DataHelper.convertStringToLocalDate(data);

		// valida o tipo da leitura
		TipoMedidor tipoMedidor = TipoMedidor.get(tipo);
		if (tipoMedidor == null) {
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
					.entity("Tipo da leitura inválida. Tipos de Medição: 1 - Água Fria, 2 - Água Quente e 3 - Gás.")
					.build());
		}

		LeituraService.registraLeitura(unidade, dataLeitura, tipoMedidor, medido);

		System.out.println("Leitura de " + tipo + " da unidade " + unidade + " em " + dataLeitura + " foi " + medido);

		return null;
		//return Response.created(new URI(PATH + leitura.getId())).entity(leitura).type(MediaType.APPLICATION_JSON_TYPE).build();
	}
}