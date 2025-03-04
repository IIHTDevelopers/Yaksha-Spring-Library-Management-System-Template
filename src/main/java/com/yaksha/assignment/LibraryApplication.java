package com.yaksha.assignment;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LibraryApplication {

	public static void main(String[] args) {
		// Load the Spring context from the XML configuration file
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

		// Retrieve the beans from the Spring container
		Book book1 = context.getBean("book1", Book.class);
		Patron patron1 = context.getBean("patron1", Patron.class);

		// Display book and patron details
		System.out.println("Book Details: " + book1.getTitle() + " by " + book1.getAuthor());
		System.out.println("Patron Details: " + patron1.getName() + ", ID: " + patron1.getId());

		// Perform actions (e.g., register patron, borrow books, etc.)
		patron1.borrowBook(book1); // Patron borrows the book
	}
}
