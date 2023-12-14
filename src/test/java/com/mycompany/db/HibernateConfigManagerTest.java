package com.mycompany.db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HibernateConfigManagerTest {
	@Mock
	private EnvParser envParser;
	@Mock
	private Configuration configuration;
	@Mock
	private SessionFactory sessionFactory;

	@Mock
	private Session session;
	@InjectMocks
	private HibernateConfigManager hibernateConfigManager;


	@Test
	void shouldSetHibernateConfigurationWithEnvParsedValues() {
		when(envParser.getDbName()).thenReturn("dbName");
		when(envParser.getRootPassword()).thenReturn("rootPassword");
		when(envParser.getUserPort()).thenReturn("3307");
		this.hibernateConfigManager.setHibernateConfig();
		verify(configuration).setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
		verify(configuration).setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3307/dbName");
		verify(configuration).setProperty("hibernate.connection.username", "root");
		verify(configuration).setProperty("hibernate.connection.password", "rootPassword");
		verify(configuration).setProperty("hibernate.connection.pool_size", "1");
		verify(configuration).setProperty("hibernate.show_sql", "true");
		verify(configuration).setProperty("hibernate.hbm2ddl.auto", "update");
		verify(configuration).addAnnotatedClass(Book.class);
	}

	@Test
	void shouldCreateNewTableAndBuildSessionFactory() {
		this.hibernateConfigManager.createNewTable();
		verify(configuration).setProperty("hibernate.hbm2ddl.auto", "create-drop");
		verify(configuration).buildSessionFactory();
	}

	@Test
	void shouldBuildSessionFactoryInConfigurationObject() {
		this.hibernateConfigManager.buildSessionFactoryInCfgObject();
		verify(configuration).buildSessionFactory();
	}

	@Test
	void shouldLogErrorWhenBuildingSessionFactoryFails() throws IllegalAccessException, NoSuchFieldException {
		RuntimeException exception = new RuntimeException("Exemplary exception");
		Logger loggerSpy = Mockito.spy(LoggerFactory.getLogger(HibernateConfigManager.class));

		Field logField = HibernateConfigManager.class.getDeclaredField("log");
		logField.setAccessible(true);
		logField.set(hibernateConfigManager, loggerSpy);

		doThrow(exception).when(configuration).buildSessionFactory();


		hibernateConfigManager.buildSessionFactoryInCfgObject();


		verify(configuration).buildSessionFactory();
		verify(loggerSpy).error("Exception occurred ", exception);
	}

	@Test
	void shouldOpenSessionInConfigurationObject() throws NoSuchFieldException, IllegalAccessException {

		when(sessionFactory.openSession()).thenReturn(session);

		Field sessionFactoryField = HibernateConfigManager.class.getDeclaredField("sessionFactory");
		sessionFactoryField.setAccessible(true);
		sessionFactoryField.set(hibernateConfigManager, sessionFactory);


		Session result = hibernateConfigManager.openSessionInCfgObject();


		assertNotNull(result);
	}

	@Test
	void shouldThrowErrorWhenOpeningSessionFails() throws Exception {
		when(sessionFactory.openSession()).thenThrow(new HibernateException("Failed to open the session"));

		Field sessionFactoryField = HibernateConfigManager.class.getDeclaredField("sessionFactory");
		sessionFactoryField.setAccessible(true);
		sessionFactoryField.set(hibernateConfigManager, sessionFactory);

		assertThrows(HibernateException.class, () -> hibernateConfigManager.openSessionInCfgObject());
	}

}