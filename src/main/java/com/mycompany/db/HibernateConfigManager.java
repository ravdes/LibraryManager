package com.mycompany.db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HibernateConfigManager {

	private final EnvParser envParser;
	private final Logger log = LoggerFactory.getLogger(HibernateConfigManager.class);
	private final Configuration hibernateConfiguration;
	private SessionFactory sessionFactory;


	public HibernateConfigManager(Configuration configuration, EnvParser envParser) {
		this.hibernateConfiguration = configuration;
		this.envParser = envParser;
		this.envParser.parseDotEnvFile();

	}


	public void setHibernateConfig() {
		this.hibernateConfiguration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
		this.hibernateConfiguration.setProperty("hibernate.connection.url",
				String.format("jdbc:mysql://localhost:%s/%s", this.envParser.getUserPort(),
						this.envParser.getDbName()));
		this.hibernateConfiguration.setProperty("hibernate.connection.username", "root");
		this.hibernateConfiguration.setProperty("hibernate.connection.password",
				String.format("%s", this.envParser.getRootPassword()));
		this.hibernateConfiguration.setProperty("hibernate.connection.pool_size", "1");
		this.hibernateConfiguration.setProperty("hibernate.show_sql", "true");
		this.hibernateConfiguration.setProperty("hibernate.hbm2ddl.auto", "update");
		this.hibernateConfiguration.addAnnotatedClass(Book.class);
	}

	public void createNewTable() {
		this.hibernateConfiguration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		this.sessionFactory = this.hibernateConfiguration.buildSessionFactory();
	}


	public void buildSessionFactoryInCfgObject() {

		try {

			this.sessionFactory = this.hibernateConfiguration.buildSessionFactory();

		} catch (Exception e) {
			log.error("Exception occurred ", e);
		}
	}


	public Session openSessionInCfgObject() {
		try {
			return this.sessionFactory.openSession();
		} catch (Exception e) {

			throw new HibernateException("Failed to open the session" + e.getMessage());

		}

	}

}











