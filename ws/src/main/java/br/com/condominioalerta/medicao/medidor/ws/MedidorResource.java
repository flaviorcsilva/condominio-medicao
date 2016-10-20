package br.com.condominioalerta.medicao.medidor.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.condominioalerta.medicao.medidor.dto.MedidorDTO;
import br.com.condominioalerta.medicao.medidor.model.Medidor;
import br.com.condominioalerta.medicao.medidor.service.MedidorService;

@Path("/medidor")
@Stateless
public class MedidorResource {

	@Inject
	private MedidorService service;

	@GET
	@Path("/{unidade}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response medidores(@HeaderParam("Condominio-ID") String condominio, @PathParam("unidade") String unidade) {
		List<Medidor> medidores = service.consultaMedidores(condominio, unidade);

		List<MedidorDTO> lista = new ArrayList<MedidorDTO>();
		for (Medidor medidor : medidores) {
			lista.add(new MedidorDTO(medidor));
		}

		return Response.ok().entity(lista).build();
	}
}