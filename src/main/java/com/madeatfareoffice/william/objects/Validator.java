package com.madeatfareoffice.william.objects;

import org.joda.time.LocalDate;
import org.joda.time.YearMonth;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Validator
{
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

	private Validator() {}

	public static LocalDate parseDate(String date) {
		return DATE_FORMATTER.parseLocalDate(date);
	}

	public static String formatDate(LocalDate date) {
		return DATE_FORMATTER.print(date);
	}

	public static YearMonth parseYearMonth(String year, String month) {
		if (year == null || month == null) {
			throw new IllegalArgumentException("missing year and/or month value");
		}
		try {
			return new YearMonth(Integer.parseInt(year), Integer.parseInt(month));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("invalid numeric year and/or month values", e);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("invalid values for year and/or month", e);
		}
	}
}
