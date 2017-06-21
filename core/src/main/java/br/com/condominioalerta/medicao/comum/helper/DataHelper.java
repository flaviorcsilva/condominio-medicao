package br.com.condominioalerta.medicao.comum.helper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

import br.com.condominioalerta.medicao.comum.execao.Mensagem;
import br.com.condominioalerta.medicao.comum.execao.NegocioException;

public class DataHelper {

	private static DateTimeFormatter anoMesFormatter = DateTimeFormatter.ofPattern("yyyyMM");
	private static DateTimeFormatter diaMesAnoFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");

	public static LocalDate converteStringToLocalDate(String strDate) {
		// DateTimeFormatter formatter =
		// DateTimeFormatter.ofPattern("ddMMyyyy").withLocale(new
		// Locale("pt-br"));

		LocalDate date = null;

		try {
			date = LocalDate.parse(strDate, diaMesAnoFormatter);
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

	public static String converteMesAnoToStringAnoMes(Integer mes, Integer ano) {
		YearMonth mesAno = DataHelper.converteIntegerToYearMonth(mes, ano);

		return mesAno.format(anoMesFormatter);
	}

	public static Date converteLocalDateToDate(LocalDate localDate) {
		Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();

		return Date.from(instant);
	}

	public static Date converteLocalDateTimeToDate(LocalDateTime localDateTime) {
		Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

		return Date.from(instant);
	}

	public static LocalDate converteDateToLocalDate(Date date) {
		Instant instant = Instant.ofEpochMilli(date.getTime());

		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
	}

	public static LocalDateTime converteDateToLocalDateTime(Date date) {
		Instant instant = Instant.ofEpochMilli(date.getTime());

		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
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

	public static String getMesPorExtenso(Integer mes, Integer ano) {
		YearMonth mesAno = converteIntegerToYearMonth(mes, ano);

		return mesAno.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()).toUpperCase();
	}
}