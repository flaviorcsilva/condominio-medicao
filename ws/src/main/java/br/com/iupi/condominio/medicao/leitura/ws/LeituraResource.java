package br.com.iupi.condominio.medicao.leitura.ws;

import java.time.LocalDate;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.iupi.condominio.medicao.helper.DataHelper;
import br.com.iupi.condominio.medicao.leitura.service.LeituraService;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedidor;

@Path("/leitura")
@Stateless
public class LeituraResource {

	@GET
	@Path("/{unidade}")
	@Produces(MediaType.APPLICATION_JSON)
	public String leitura(@PathParam("unidade") String unidade, @QueryParam("data") String data,
			@QueryParam("tipo") Integer tipo, @QueryParam("medido") Integer medido) {

		LocalDate dataLeitura = DataHelper.convertStringToLocalDate(data);
		TipoMedidor tipoMedidor = TipoMedidor.get(tipo);

		LeituraService.insereLeitura(unidade, dataLeitura, tipoMedidor, medido);

		String msg = "Leitura de " + tipo + " da unidade " + unidade + " em " + dataLeitura + " foi " + medido;
		System.out.println(msg);

		return msg;

		// return Response.created(new URI(PATH +
		// leitura.getId())).entity(leitura).type(MediaType.APPLICATION_JSON_TYPE).build();
	}
}