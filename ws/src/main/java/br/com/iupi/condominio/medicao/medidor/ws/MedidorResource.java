package br.com.iupi.condominio.medicao.medidor.ws;

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

import br.com.iupi.condominio.medicao.medidor.dto.MedidorDTO;
import br.com.iupi.condominio.medicao.medidor.modelo.Medidor;
import br.com.iupi.condominio.medicao.medidor.service.MedidorService;

@Path("/medidor")
@Stateless
public class MedidorResource {

	@Inject
	private MedidorService service;

	@GET
	@Path("/{unidade}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<MedidorDTO> medidores(@HeaderParam("Condominio-ID") String condominio,
			@PathParam("unidade") String unidade) {
		List<Medidor> medidores = service.consultaMedidores(condominio, unidade);

		List<MedidorDTO> lista = new ArrayList<MedidorDTO>();
		for (Medidor medidor : medidores) {
			lista.add(new MedidorDTO(medidor));
		}

		return lista;
	}
}