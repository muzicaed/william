package com.madeatfareoffice.william.objects;

public abstract class Action
{
	private String action_uuid;
	private String ota;
	private String plo;
	private String date;


	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getPlo()
	{
		return plo;
	}

	public void setPlo(String plo)
	{
		this.plo = plo;
	}

	public String getOta()
	{
		return ota;
	}

	public void setOta(String ota)
	{
		this.ota = ota;
	}

	public void setAction_uuid(String action_uuid)
	{
		this.action_uuid = action_uuid;
	}
}
