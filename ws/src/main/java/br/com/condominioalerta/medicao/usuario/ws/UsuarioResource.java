package br.com.condominioalerta.medicao.usuario.ws;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/usuario")
@Stateless
public class UsuarioResource {
	
	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public void login() {
		//Tratado no filter de autenticação
	}
}