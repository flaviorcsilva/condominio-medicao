package br.com.iupi.condominio.medicao.ws;

import java.time.LocalDate;

import javax.ejb.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.iupi.condominio.medicao.leitura.dto.LeituraDTO;
import br.com.iupi.condominio.medicao.leitura.modelo.Leitura;
import br.com.iupi.condominio.medicao.medidor.modelo.Medidor;

@Path("medicao")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class MedicaoWS {

	private final static String PATH = "/medicao/";
	private Long idLeitura = 1L;

	@GET
	@Path("/leitura/{id}")
	public LeituraDTO consultaLeitura(@PathParam("id") Long id) {
		Leitura leitura = new Leitura();
		Medidor medidor = new Medidor();

		if (id == 1L) {
			medidor.setNumero("ABCDEF");
			leitura = new Leitura(medidor, LocalDate.now(), 100);
		} else if (id == 2) {
			medidor.setNumero("GHIJKL");
			leitura = new Leitura(medidor, LocalDate.now(), 200);
		} else {
			medidor.setNumero("MNOPQRS");
			leitura = new Leitura(medidor, LocalDate.now(), 300);
		}

		leitura.setId(id);

		return new LeituraDTO(leitura);
	}

	@GET
	@Path("/grava")
	public String gravaLeitura(@QueryParam("medido") Integer medido) {
		System.out.println("Leitura gravada de: " + medido);
		
		return "CRIADO";

		// return Response.created(new URI(PATH +
		// leitura.getId())).entity(leitura).type(MediaType.APPLICATION_JSON_TYPE).build();
	}
}
