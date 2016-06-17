package br.com.iupi.condominio.medicao.agua.modelo;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.iupi.condominio.medicao.condominio.modelo.Condominio;
import br.com.iupi.condominio.medicao.consumo.modelo.ConsumoUnidade;
import br.com.iupi.condominio.medicao.leitura.modelo.Leitura;
import br.com.iupi.condominio.medicao.medidor.modelo.Medidor;
import br.com.iupi.condominio.medicao.medidor.modelo.TipoMedicao;
import br.com.iupi.condominio.medicao.unidade.modelo.UnidadeConsumidora;

public class ConsumoTest {

	private static Double precoM3Agua = 6.8405;
	// private static Double precoM3Gas = 3.20;
	private static Medidor medidorAguaFria;
	private static Medidor medidorAguaQuente;
	private static Medidor medidorGas;

	@BeforeClass
	public static void setUp() {
		Condominio condominio = new Condominio();
		condominio.setCodigo("privilege-noroeste");
		condominio.setNome("Privilege Noroeste");
		
		UnidadeConsumidora unidadeConsumidora = new UnidadeConsumidora();
		unidadeConsumidora.setCondominio(condominio);
		unidadeConsumidora.setUnidade("212");

		medidorAguaFria = new Medidor();
		medidorAguaFria.setUnidadeConsumidora(unidadeConsumidora);
		medidorAguaFria.setTipo(TipoMedicao.AGUA_FRIA);
		medidorAguaFria.setNumero("A14E012523");
		medidorAguaFria.setFator(1);

		medidorAguaQuente = new Medidor();
		medidorAguaQuente.setUnidadeConsumidora(unidadeConsumidora);
		medidorAguaQuente.setTipo(TipoMedicao.AGUA_QUENTE);
		medidorAguaQuente.setNumero("A13F011417");
		medidorAguaQuente.setFator(1);

		medidorGas = new Medidor();
		medidorGas.setUnidadeConsumidora(unidadeConsumidora);
		medidorGas.setTipo(TipoMedicao.GAS);
		medidorGas.setNumero("B14D0001552D");
		medidorGas.setFator(1);

		unidadeConsumidora.addMedidor(medidorAguaFria);
		unidadeConsumidora.addMedidor(medidorAguaQuente);
		unidadeConsumidora.addMedidor(medidorGas);
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
		ConsumoUnidade consumoUnidade = new ConsumoUnidade(leituraAnterior, leituraAtual, precoM3Agua);

		assertEquals(1266, consumoUnidade.getMedido(), 0);
		assertEquals(8660.07, consumoUnidade.getValor(), 0.1);

		System.out.println("Total a Pagar: " + consumoUnidade.getValor() + " pelo consumo de " + consumoUnidade.getMedido()
				+ "m3 da unidade " + medidorAguaFria.getUnidadeConsumidora().getUnidade() + " do "
				+ medidorAguaFria.getUnidadeConsumidora().getCondominio());
	}

}
