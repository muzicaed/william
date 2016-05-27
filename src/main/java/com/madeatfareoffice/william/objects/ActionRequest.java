package com.madeatfareoffice.william.objects;

import com.madeatfareoffice.william.OurAwesomeApp;
import org.joda.time.LocalDate;

public class ActionRequest extends Action implements OurAwesomeApp.Validable
{
	private String errorMessage;
	private LocalDate parsedDate;

	public LocalDate getParsedDate() {
		return parsedDate;
	}

	@Override
	public boolean isValid()
	{
		if (getOta() == null) {
			errorMessage = "missing OTA code";
			return false;
		}
		if (getPlo() == null) {
			errorMessage = "missing pick up location code";
			return false;
		}
		if (getDate() == null) {
			errorMessage = "missing date";
			return false;
		}
		try {
			parsedDate = Validator.parseDate(getDate());
		} catch (IllegalArgumentException e) {
			errorMessage = "invalid date: " + e.getMessage();
			return false;
		}
		return true;
	}

	@Override
	public String getErrorMessage()
	{
		return errorMessage;
	}
}
