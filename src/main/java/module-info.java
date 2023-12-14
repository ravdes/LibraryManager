module com.mycompany.java_first_project {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.base;
	requires jakarta.persistence;
	requires org.hibernate.orm.core;
	requires io.github.cdimascio.dotenv.java;
	requires java.naming;
	requires org.slf4j;


	opens com.mycompany.librarymanagement to javafx.fxml;
	opens com.mycompany.db to org.hibernate.orm.core;

	exports com.mycompany.librarymanagement;
	exports com.mycompany.db;
}
