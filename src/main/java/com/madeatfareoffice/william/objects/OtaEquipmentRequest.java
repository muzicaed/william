package com.madeatfareoffice.william.objects;

import com.madeatfareoffice.william.OurAwesomeApp;


public class OtaEquipmentRequest extends OtaEquipment implements OurAwesomeApp.Validable
{

	private String errorMessage;

	@Override
	public boolean isValid()
	{
		if (getOta() == null)
		{
			errorMessage = "missing OTA code";
			return false;
		}
		if (getDescription() == null)
		{
			errorMessage = "missing description";
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
