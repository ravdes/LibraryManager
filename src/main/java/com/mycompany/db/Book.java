package com.mycompany.db;

import jakarta.persistence.*;

@Entity

@Table(name = "books")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "Title")
	private String title;
	@Column(name = "Author")
	private String author;
	@Column(name = "Genre")
	private String genre;

	@Column(name = "Pages")
	private int pages;

	public Book(String title, String author, String genre, int pages) {

		this.title = title;
		this.author = author;
		this.genre = genre;
		this.pages = pages;
	}

	public Book() {

	}

	public int getId() {
		return id;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}
}
