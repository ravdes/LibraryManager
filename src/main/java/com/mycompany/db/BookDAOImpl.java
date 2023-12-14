package com.mycompany.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BookDAOImpl implements BookDAO {
	private final Logger log = LoggerFactory.getLogger(BookDAOImpl.class);

	private final HibernateConfigManager hibernateCfgManager;
	private Session session;


	public BookDAOImpl(HibernateConfigManager cfg) {
		this.hibernateCfgManager = cfg;
		this.hibernateCfgManager.setHibernateConfig();
		this.hibernateCfgManager.buildSessionFactoryInCfgObject();

	}

	public void createSession() {
		this.session = hibernateCfgManager.openSessionInCfgObject();
	}


	public ObservableList<Book> getAllBooks() {
		ObservableList<Book> bookList = FXCollections.observableArrayList();
		createSession();
		List<Book> books = this.session.createQuery("from Book", Book.class).getResultList();
		bookList.addAll(books);
		this.session.close();
		return bookList;


	}

	public void createAndDropNewTable() {
		hibernateCfgManager.createNewTable();
	}

	@Override
	public void insertBook(String author, String title, String genre, int pages) {
		try {
			createSession();
			Book elementToAdd = new Book(author, title, genre, pages);
			this.session.beginTransaction();
			this.session.persist(elementToAdd);
			this.session.getTransaction().commit();
			this.session.close();
		} catch (Exception e) {
			log.error("Exception occurred", e.getMessage());
		}
	}


	public void updateBook(int id, String title, String author, String genre, int pages) {
		try {
			createSession();
			this.session.beginTransaction();
			Book bookToUpdate = this.session.get(Book.class, id);
			bookToUpdate.setTitle(title);
			bookToUpdate.setAuthor(author);
			bookToUpdate.setGenre(genre);
			bookToUpdate.setPages(pages);
			this.session.merge(bookToUpdate);
			this.session.getTransaction().commit();
			this.session.close();

		} catch (Exception e) {
			log.error("Exception occurred", e);
		}
	}

	public void deleteBook(int id) {
		try {
			createSession();
			this.session.beginTransaction();
			Book bookToDelete = this.session.get(Book.class, id);
			this.session.remove(bookToDelete);
			this.session.getTransaction().commit();
			this.session.close();

		} catch (Exception e) {
			log.error("Exception occurred", e);

		}


	}

}


