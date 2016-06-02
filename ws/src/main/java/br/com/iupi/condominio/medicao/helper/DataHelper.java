package br.com.iupi.condominio.medicao.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class DataHelper {

	public static LocalDate convertStringToLocalDate(String strDate) {
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy").withLocale(new Locale("pt-br"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

		LocalDate date = null;

		try {
			date = LocalDate.parse(strDate, formatter);
		} catch (DateTimeParseException e) {
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
					.entity("Erro na conversão da data. Formato: ddMMyyyy.").build());
		}

		return date;
	}

}
