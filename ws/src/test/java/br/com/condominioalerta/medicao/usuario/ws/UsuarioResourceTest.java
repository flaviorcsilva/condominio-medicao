package br.com.condominioalerta.medicao.usuario.ws;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.openejb.jee.SingletonBean;
//import org.apache.openejb.junit.ApplicationComposer;
//import org.apache.openejb.testing.EnableServices;
//import org.apache.openejb.testing.Module;
import org.junit.Ignore;
//import org.junit.runner.RunWith;

//@EnableServices(value = "jarxs")
//@RunWith(ApplicationComposer.class)
public class UsuarioResourceTest {

	//@Module
	public SingletonBean app() {
		return (SingletonBean) new SingletonBean(UsuarioResource.class).localBean();
	}

	@Ignore
	public void deveConsultarUsuarioConectado() throws IOException {
		final String response = WebClient.create("http://localhost:4204").path("/UsuarioResourceTest/usuario/conectado")
				.accept(MediaType.APPLICATION_JSON_TYPE).get(String.class);

		assertEquals("Teste", response);
	}
}