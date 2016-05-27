package com.madeatfareoffice.william.objects;

public abstract class OtaEquipment
{
	private String ota_uuid;
	private String ota;
	private String description;

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getOta()
	{
		return ota;
	}

	public void setOta(String ota)
	{
		this.ota = ota;
	}

	public void setOta_uuid(String ota_uuid)
	{
		this.ota_uuid = ota_uuid;
	}
}
