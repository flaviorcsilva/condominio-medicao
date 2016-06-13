package br.com.iupi.condominio.medicao.comum.helper;

import java.time.LocalDate;
import java.time.YearMonth;
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
			throw new NegocioException(Mensagem.DATA_ERRO_CONVERSAO_FORMATO_DATA);
		}

		return date;
	}

	public static YearMonth converteIntegerToYearMonth(Integer mes, Integer ano) {
		YearMonth mesAno = null;

		try {
			mesAno = YearMonth.of(ano, mes);
		} catch (Exception e) {
			throw new NegocioException(Mensagem.DATA_ERRO_CONVERSAO_MES_ANO);
		}

		return mesAno;
	}

	public static LocalDate getInicioDeMes(Integer mes, Integer ano) {
		YearMonth mesAno = converteIntegerToYearMonth(mes, ano);

		return mesAno.atDay(1);
	}

	public static LocalDate getInicioDeMes(YearMonth mesAno) {
		return mesAno.atDay(1);
	}

	public static LocalDate getFinalDeMes(Integer mes, Integer ano) {
		YearMonth mesAno = converteIntegerToYearMonth(mes, ano);

		return mesAno.atEndOfMonth();
	}

	public static LocalDate getFinalDeMes(YearMonth mesAno) {
		return mesAno.atEndOfMonth();
	}
}