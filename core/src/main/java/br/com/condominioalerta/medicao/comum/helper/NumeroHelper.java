package br.com.condominioalerta.medicao.comum.helper;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumeroHelper {

	private static final DecimalFormat FORMATO_2_INTEIROS = new DecimalFormat("00");
	private static final DecimalFormat FORMATO_2_DECIMAIS = new DecimalFormat("##0.00");
	private static final DecimalFormat FORMATO_5_DECIMAIS = new DecimalFormat("##0.00###");
	
	public static String formataNumeroTo2Inteiros(Integer valor) {
		return FORMATO_2_INTEIROS.format(valor);
	}

	public static String formataNumeroTo2Decimais(Double valor) {
		return FORMATO_2_DECIMAIS.format(valor);
	}

	public static String formataNumeroTo5Decimais(Double valor) {
		return FORMATO_5_DECIMAIS.format(valor);
	}
	
	public static double arredonda(double valor, int numeroDeDecimais) {
		BigDecimal bd = new BigDecimal(Double.toString(valor));
		
		bd = bd.setScale(numeroDeDecimais, BigDecimal.ROUND_HALF_UP);

		return bd.doubleValue();
	}
}