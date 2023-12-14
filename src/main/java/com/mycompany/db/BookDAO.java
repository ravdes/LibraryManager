package com.mycompany.db;

import javafx.collections.ObservableList;


public interface BookDAO {
	ObservableList<Book> getAllBooks();

	void insertBook(String author, String title, String genre, int pages);

	void updateBook(int id, String title, String author, String genre, int pages);

	void deleteBook(int id);
}
