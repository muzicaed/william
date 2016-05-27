package com.madeatfareoffice.william.objects;

import org.joda.time.LocalDate;
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
}
