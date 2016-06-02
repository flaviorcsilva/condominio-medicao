package br.com.iupi.condominio.medicao.ws;

import java.time.LocalDate;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.iupi.condominio.medicao.helper.DataHelper;
import br.com.iupi.condominio.medicao.leitura.modelo.Leitura;
import br.com.iupi.condominio.medicao.medidor.modelo.Medidor;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedidor;
import br.com.iupi.condominio.medicao.service.MedicaoService;
import br.com.iupi.condominio.medicao.unidade.modelo.Unidade;

@Path("/medicao")
@Stateless
public class MedicaoResource {

	@GET
	@Path("leitura/{unidade}")
	@Produces(MediaType.APPLICATION_JSON)
	public String leitura(@PathParam("unidade") String unidade, @QueryParam("data") String data,
			@QueryParam("tipo") String tipo, @QueryParam("medido") Integer medido) {
		
		LocalDate dataLeitura = DataHelper.convertStringToLocalDate(data);
		
		String msg = "Leitura de " + tipo + " da unidade " + unidade + " em " + dataLeitura + " foi " + medido;
		System.out.println(msg);
		
		Integer id = MedicaoService.getSequencial();
		
		TipoMedidor tipoMedidor = TipoMedidor.valueOf(tipo);
		
		Leitura leitura = new Leitura(new Medidor(new Unidade(), tipoMedidor, "XYZ-9876"), dataLeitura, medido);
		
		MedicaoService.addLeitura(leitura);
		
		return msg;

		// return Response.created(new URI(PATH +
		// leitura.getId())).entity(leitura).type(MediaType.APPLICATION_JSON_TYPE).build();
	}
}