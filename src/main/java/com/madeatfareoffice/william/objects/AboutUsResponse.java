package com.madeatfareoffice.william.objects;

import lombok.Data;

@Data
public class AboutUsResponse
{
	private final String teamName = "William";
	private final String[] teamMembers = new String[] {"Martin Kemani", "Mikael Hellman", "Mikael Lennholm Berg", "Lauro Schuck"};
	private final String programmingLanguage = "Java";
	private final String database = "Postgres";
	private final String framework = "Spark";
	private final String appServer = "Jetty";
}
