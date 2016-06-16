package br.com.iupi.condominio.medicao.app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import br.com.iupi.condominio.medicao.consumo.ws.ConsumoResource;
import br.com.iupi.condominio.medicao.leitura.ws.LeituraResource;
import br.com.iupi.condominio.medicao.medidor.ws.MedidorResource;

@ApplicationPath("/medicao")
public class AplicacaoWS extends Application {

	Set<Object> singletons;

	@Override
	public Set<Class<?>> getClasses() {
		HashSet<Class<?>> clazzes = new HashSet<>();

		clazzes.add(LeituraResource.class);
		clazzes.add(ConsumoResource.class);
		clazzes.add(MedidorResource.class);

		return clazzes;
	}

	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<>();

		singletons.add(new ResourcesRequestFilter());

		return singletons;
	}
}