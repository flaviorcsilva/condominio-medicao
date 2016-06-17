package br.com.iupi.condominio.medicao.app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import br.com.iupi.condominio.medicao.comum.filter.AutorizacaoFilter;
import br.com.iupi.condominio.medicao.consumo.ws.ConsumoResource;
import br.com.iupi.condominio.medicao.leitura.ws.LeituraResource;
import br.com.iupi.condominio.medicao.medidor.ws.MedidorResource;

@ApplicationPath("/medicao")
public class AplicacaoWS extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		HashSet<Class<?>> classes = new HashSet<>();

		classes.add(LeituraResource.class);
		classes.add(ConsumoResource.class);
		classes.add(MedidorResource.class);

		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<>();

		singletons.add(new AutorizacaoFilter());

		return singletons;
	}
}