package com.madeatfareoffice.william.objects;

import lombok.Data;

@Data
public class ErrorResponse
{
	private String error;

	public ErrorResponse() {}

	public ErrorResponse(String error) {
		this.error = error;
	}
}
