package br.com.condominioalerta.medicao.app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import br.com.condominioalerta.medicao.comum.filter.AutorizacaoFilter;
import br.com.condominioalerta.medicao.consumo.ws.ConsumoResource;
import br.com.condominioalerta.medicao.leitura.ws.LeituraResource;
import br.com.condominioalerta.medicao.medidor.ws.MedidorResource;
import br.com.condominioalerta.medicao.usuario.ws.UsuarioResource;

@ApplicationPath("/medicao")
public class AplicacaoWS extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		HashSet<Class<?>> classes = new HashSet<>();

		classes.add(LeituraResource.class);
		classes.add(ConsumoResource.class);
		classes.add(MedidorResource.class);
		classes.add(UsuarioResource.class);
		classes.add(HelloResource.class);

		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<>();

		singletons.add(new AutorizacaoFilter());

		return singletons;
	}
}