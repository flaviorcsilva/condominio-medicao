package br.com.condominioalerta.medicao.consumo.model;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.condominioalerta.medicao.medidor.model.TipoMedicao;

public class ConsumoCondominioTest {
	
	private static ConsumoCondominio consumoCondominio = null;


	@BeforeClass
	public static void setUp() {
		consumoCondominio = new ConsumoCondominio();
		consumoCondominio.setCondominio("privilege-noroeste");
		consumoCondominio.setAnoMes("201609");
		consumoCondominio.setTipoMedicao(TipoMedicao.GAS);
		consumoCondominio.setValorMedidoFatura(500);
		consumoCondominio.setValorTotalFaturado(2041.93);
	}

	@AfterClass
	public static void tearDown() {

	}

	@Test
	public void deveObterOMesEAnoDoConsumo() {
		assertEquals(9, consumoCondominio.getMes(), 0);
		assertEquals(2016, consumoCondominio.getAno(), 0);
	}
	
	@Test
	public void deveCalcularOM3DoConsumo() {
		assertEquals(4.08386, consumoCondominio.getValorM3(), 0.01);
	}
}