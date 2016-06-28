package br.com.iupi.condominio.medicao.consumo.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.iupi.condominio.medicao.consumo.dto.ConsumoDTO;
import br.com.iupi.condominio.medicao.consumo.modelo.ConsumoUnidade;
import br.com.iupi.condominio.medicao.consumo.service.ConsumoService;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedicao;
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
	public List<ConsumoDTO> consumo(@HeaderParam("Condominio-ID") String condominio,
			@PathParam("unidade") String unidade, @QueryParam("mes") Integer mes, @QueryParam("ano") Integer ano) {
		UnidadeConsumidora unidadeConsumidora = unidadeConsumidoraService.consultaUnidadeConsumidora(condominio,
				unidade);

		ConsumoUnidade consumoAguaFria = service.calculaConsumo(unidadeConsumidora, TipoMedicao.AGUA_FRIA, mes, ano);
		ConsumoUnidade consumoAguaQuente = service.calculaConsumo(unidadeConsumidora, TipoMedicao.AGUA_QUENTE, mes,
				ano);
		ConsumoUnidade consumoGas = service.calculaConsumo(unidadeConsumidora, TipoMedicao.GAS, mes, ano);

		List<ConsumoDTO> consumos = new ArrayList<ConsumoDTO>();
		consumos.add(new ConsumoDTO(consumoAguaFria));
		consumos.add(new ConsumoDTO(consumoAguaQuente));
		consumos.add(new ConsumoDTO(consumoGas));

		return consumos;
	}

	@GET
	@Path("/unidades")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ConsumoDTO> consumos(@HeaderParam("Condominio-ID") String condominio, @QueryParam("mes") Integer mes,
			@QueryParam("ano") Integer ano) {
		List<ConsumoUnidade> consumosUnidade = service.calculaConsumos(condominio, mes, ano);

		List<ConsumoDTO> consumos = new ArrayList<ConsumoDTO>();
		for (ConsumoUnidade consumo : consumosUnidade) {
			consumos.add(new ConsumoDTO(consumo));
		}

		return consumos;
	}
}