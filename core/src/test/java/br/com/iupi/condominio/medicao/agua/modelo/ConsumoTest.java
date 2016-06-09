package br.com.iupi.condominio.medicao.agua.modelo;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.iupi.condominio.medicao.leitura.modelo.Consumo;
import br.com.iupi.condominio.medicao.leitura.modelo.Leitura;
import br.com.iupi.condominio.medicao.medidor.modelo.Medidor;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedidor;
import br.com.iupi.condominio.medicao.unidade.modelo.Unidade;

public class ConsumoTest {

	private static Double precoM3Agua = 6.8405;
	//private static Double precoM3Gas = 3.20;
	private static Medidor medidorAguaFria;
	private static Medidor medidorAguaQuente;
	private static Medidor medidorGas;

	@BeforeClass
	public static void setUp() {
		Unidade unidade = new Unidade();
		unidade.setCondominio("Privilege Noroeste");
		unidade.setUnidade("212");

		medidorAguaFria = new Medidor();
		medidorAguaFria.setUnidade(unidade);
		medidorAguaFria.setTipo(TipoMedidor.AGUA_FRIA);
		medidorAguaFria.setNumero("A14E012523");

		medidorAguaQuente = new Medidor();
		medidorAguaQuente.setUnidade(unidade);
		medidorAguaQuente.setTipo(TipoMedidor.AGUA_QUENTE);
		medidorAguaQuente.setNumero("A13F011417");

		medidorGas = new Medidor();
		medidorGas.setUnidade(unidade);
		medidorGas.setTipo(TipoMedidor.GAS);
		medidorGas.setNumero("B14D0001552D");

		unidade.addMedidor(medidorAguaFria);
		unidade.addMedidor(medidorAguaQuente);
		unidade.addMedidor(medidorGas);
	}

	@AfterClass
	public static void tearDown() {

	}

	@Test
	public void deveCalcularOConsumo() {
		LocalDate mesAnterior = LocalDate.now().minusMonths(1);
		LocalDate mesAtual = LocalDate.now();

		Leitura leituraAnterior = new Leitura();
		leituraAnterior.setMedidor(medidorAguaFria);
		leituraAnterior.setDataLeitura(mesAnterior);
		leituraAnterior.setMedido(12406);

		Leitura leituraAtual = new Leitura();
		leituraAtual.setMedidor(medidorAguaFria);
		leituraAtual.setDataLeitura(mesAtual);
		leituraAtual.setMedido(13672);

		assertEquals(leituraAnterior.getMedidor().getNumero(), leituraAtual.getMedidor().getNumero());

		/* Calculo do consumo */
		Consumo consumo = new Consumo(leituraAnterior, leituraAtual);
		consumo.setPreco(precoM3Agua);

		assertEquals(1266, consumo.getMedido(), 0);
		assertEquals(8660.07, consumo.getTotalAPagar(), 0.1);

		System.out.println("Total a Pagar: " + consumo.getTotalAPagar() + " pelo consumo de " + consumo.getMedido()
				+ "m3 da unidade " + medidorAguaFria.getUnidade().getUnidade() + " do "
				+ medidorAguaFria.getUnidade().getCondominio());
	}

}
