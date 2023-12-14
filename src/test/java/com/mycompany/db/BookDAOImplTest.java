package com.mycompany.db;

import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookDAOImplTest {
	@Mock
	private HibernateConfigManager hibernateConfigManager;

	@Mock
	private Session session;

	@Mock
	private Transaction transaction;
	@Mock
	private Query<Book> query;
	@InjectMocks
	private BookDAOImpl bookDAO;

	@Test
	void shouldCreateSession() {
		bookDAO.createSession();
		verify(hibernateConfigManager).openSessionInCfgObject();

	}

	@Test
	void shouldGetAllBooks() {
		when(this.hibernateConfigManager.openSessionInCfgObject()).thenReturn(this.session);
		when(this.session.createQuery("from Book", Book.class)).thenReturn(this.query);
		List<Book> expectedBooks = new ArrayList<>();

		when(query.getResultList()).thenReturn(expectedBooks);

		ObservableList<Book> actualBooks = bookDAO.getAllBooks();

		assertEquals(expectedBooks.size(), actualBooks.size());
		verify(hibernateConfigManager).openSessionInCfgObject();
		verify(session).createQuery("from Book", Book.class);
		verify(query).getResultList();
		verify(session).close();
	}

	@Test
	void shouldCreateAndDropTable() {
		bookDAO.createAndDropNewTable();
		verify(hibernateConfigManager).createNewTable();
	}

	@Test
	void shouldInsertBook() {

		when(this.hibernateConfigManager.openSessionInCfgObject()).thenReturn(this.session);
		when(this.session.beginTransaction()).thenReturn(this.transaction);
		when(this.session.getTransaction()).thenReturn(this.transaction);


		bookDAO.insertBook("testAuthor", "testTitle", "testGenre", 50);


		verify(hibernateConfigManager).openSessionInCfgObject();
		verify(session).beginTransaction();
		verify(session).getTransaction();
		verify(transaction).commit();
		verify(session).close();
	}

	@Test
	void shouldUpdateBook() {
		when(this.hibernateConfigManager.openSessionInCfgObject()).thenReturn(this.session);
		when(this.session.beginTransaction()).thenReturn(this.transaction);
		when(this.session.getTransaction()).thenReturn(this.transaction);

		Book existingBook = new Book("testAuthor", "testTitle", "testGenre", 50);
		when(this.session.get(Book.class, 1)).thenReturn(existingBook);

		this.bookDAO.updateBook(1, "testAuthor", "testTitle", "testGenre", 50);

		verify(this.hibernateConfigManager).openSessionInCfgObject();
		verify(this.session).beginTransaction();
		verify(this.session).get(Book.class, 1);
		verify(this.session).merge(existingBook);
		verify(this.session).getTransaction();
		verify(this.transaction).commit();
		verify(this.session).close();


	}

	@Test
	void shouldDeleteBook() {
		when(this.hibernateConfigManager.openSessionInCfgObject()).thenReturn(this.session);
		when(this.session.beginTransaction()).thenReturn(this.transaction);
		when(this.session.getTransaction()).thenReturn(this.transaction);


		Book existingBook = new Book("testAuthor", "testTitle", "testGenre", 50);
		when(this.session.get(Book.class, 0)).thenReturn(existingBook);

		this.bookDAO.deleteBook(0);

		verify(this.session).beginTransaction();
		verify(this.session).remove(existingBook);
		verify(this.session).getTransaction();
		verify(this.transaction).commit();
		verify(this.session).close();

	}
}