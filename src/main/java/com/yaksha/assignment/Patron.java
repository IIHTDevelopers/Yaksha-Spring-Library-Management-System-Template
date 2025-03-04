package com.yaksha.assignment;

public class Patron {

	private String name;
	private String id;

	// Default constructor
	public Patron() {
	}

	// Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// Method to simulate borrowing a book
	public void borrowBook(Book book) {
		System.out.println(name + " borrowed the book: " + book.getTitle());
	}
}
