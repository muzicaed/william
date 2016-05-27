package com.madeatfareoffice.william.objects;

import lombok.Data;

@Data
public class ResourceIdResponse
{
	private String id;

	public ResourceIdResponse() {}

	public ResourceIdResponse(String id) {
		this.id = id;
	}
}
