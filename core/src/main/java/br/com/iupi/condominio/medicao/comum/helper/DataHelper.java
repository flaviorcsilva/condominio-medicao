package br.com.iupi.condominio.medicao.comum.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import br.com.iupi.condominio.medicao.comum.execao.Mensagem;
import br.com.iupi.condominio.medicao.comum.execao.NegocioException;

public class DataHelper {

	public static LocalDate convertStringToLocalDate(String strDate) {
		// DateTimeFormatter formatter =
		// DateTimeFormatter.ofPattern("ddMMyyyy").withLocale(new
		// Locale("pt-br"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

		LocalDate date = null;

		try {
			date = LocalDate.parse(strDate, formatter);
		} catch (DateTimeParseException e) {
			throw new NegocioException(Mensagem.DATA_ERRO_CONVERSAO);
		}

		return date;
	}
}