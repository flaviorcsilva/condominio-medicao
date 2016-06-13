package br.com.iupi.condominio.medicao.consumo.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.iupi.condominio.medicao.consumo.dto.ConsumoDTO;
import br.com.iupi.condominio.medicao.consumo.modelo.Consumo;
import br.com.iupi.condominio.medicao.consumo.service.ConsumoService;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedidor;
import br.com.iupi.condominio.medicao.unidade.modelo.UnidadeConsumidora;
import br.com.iupi.condominio.medicao.unidade.service.UnidadeConsumidoraService;

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
	public List<ConsumoDTO> consumo(@PathParam("unidade") String unidade, @QueryParam("mes") Integer mes,
			@QueryParam("ano") Integer ano) {
		UnidadeConsumidora unidadeConsumidora = unidadeConsumidoraService.consultaUnidadeConsumidora(unidade);

		Consumo consumoAguaFria = service.consultaConsumo(unidadeConsumidora, TipoMedidor.AGUA_FRIA, mes, ano);
		Consumo consumoAguaQuente = service.consultaConsumo(unidadeConsumidora, TipoMedidor.AGUA_QUENTE, mes, ano);
		Consumo consumoGas = service.consultaConsumo(unidadeConsumidora, TipoMedidor.GAS, mes, ano);

		List<ConsumoDTO> consumos = new ArrayList<ConsumoDTO>();
		consumos.add(new ConsumoDTO(consumoAguaFria));
		consumos.add(new ConsumoDTO(consumoAguaQuente));
		consumos.add(new ConsumoDTO(consumoGas));

		return consumos;
	}
}