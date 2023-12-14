package com.mycompany.db;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvParser {
	private String dbName;
	private String rootPassword;
	private String userPort;

	public void parseDotEnvFile() {
		Dotenv dotenv = Dotenv.load();
		this.dbName = dotenv.get("MYSQL_DB_NAME");
		this.rootPassword = dotenv.get("MYSQL_ROOT_PASSWORD");
		this.userPort = dotenv.get("MYSQL_PORT");
	}

	public String getDbName() {
		return dbName;
	}

	public String getRootPassword() {
		return rootPassword;
	}

	public String getUserPort() {
		return userPort;
	}
}
