package com.yaksha.assignment.functional;

import static com.yaksha.assignment.utils.TestUtils.businessTestFile;
import static com.yaksha.assignment.utils.TestUtils.currentTest;
import static com.yaksha.assignment.utils.TestUtils.testReport;
import static com.yaksha.assignment.utils.TestUtils.yakshaAssert;

import java.io.IOException;

import org.junit.After;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yaksha.assignment.Book;
import com.yaksha.assignment.Patron;
import com.yaksha.assignment.utils.XMLParserUtils;

public class SpringContextControllerTest {

	@After
	public void afterAll() {
		testReport();
	}

	@Test
	public void testClassPathXmlContextLoadsBookAndPatronCorrectly() throws IOException {
		// Load the context from the classpath-based XML configuration
		ApplicationContext contextClasspath = new ClassPathXmlApplicationContext("applicationContext.xml");

		// Retrieve the beans from the context
		Book book1 = contextClasspath.getBean("book1", Book.class);
		Patron patron1 = contextClasspath.getBean("patron1", Patron.class);

		// Assert that the beans are correctly instantiated
		boolean bookNotNull = book1 != null;
		boolean patronNotNull = patron1 != null;

		// Assert that the 'title' and 'name' properties are set correctly
		boolean bookDetailsCorrect = "The Great Gatsby".equals(book1.getTitle())
				&& "F. Scott Fitzgerald".equals(book1.getAuthor());
		boolean patronDetailsCorrect = "John Doe".equals(patron1.getName()) && "12345".equals(patron1.getId());

		// Console logging for debugging
		if (!bookNotNull) {
			System.out.println("Failure: 'book1' bean is not instantiated or found in the context.");
		}
		if (!patronNotNull) {
			System.out.println("Failure: 'patron1' bean is not instantiated or found in the context.");
		}
		if (!bookDetailsCorrect) {
			System.out.println(
					"Failure: 'book1' properties are incorrect. Expected 'title' as 'The Great Gatsby' and 'author' as 'F. Scott Fitzgerald'.");
		}
		if (!patronDetailsCorrect) {
			System.out.println(
					"Failure: 'patron1' properties are incorrect. Expected 'name' as 'John Doe' and 'id' as '12345'.");
		}

		// Use yakshaAssert to validate the test result
		yakshaAssert(currentTest(), bookNotNull && patronNotNull && bookDetailsCorrect && patronDetailsCorrect,
				businessTestFile);
	}

	@Test
	public void testApplicationContextXMLContainsRequiredBeanAndProperty() throws IOException {
		String filePath = "src/main/resources/applicationContext.xml"; // Path to the XML file

		// Check if the 'book1' bean is defined and if the 'title' property is set to
		// "The Great Gatsby"
		boolean result = XMLParserUtils.checkXMLStructure(filePath, "book1", // The expected bean id in
																				// applicationContext.xml
				"title", // The expected property name in the bean
				"The Great Gatsby"); // The expected property value

		// Console logging for debugging
		if (!result) {
			System.out.println(
					"Failure: Bean 'book1' with 'title' property set to 'The Great Gatsby' is missing or misconfigured.");
		}

		// Using yakshaAssert to validate the test result
		yakshaAssert(currentTest(), result, businessTestFile);
	}

	@Test
	public void testBookBeanPropertyFields() throws IOException {
		String filePath = "src/main/resources/applicationContext.xml";

		// Check if the "title" property exists and has the correct value
		boolean titlePropertyExists = XMLParserUtils.checkPropertyExists(filePath, "book1", "title",
				"The Great Gatsby");

		// Check if the "author" property exists and has the correct value
		boolean authorPropertyExists = XMLParserUtils.checkPropertyExists(filePath, "book1", "author",
				"F. Scott Fitzgerald");

		// Check if the 'scope' of the Book bean is 'prototype'
		boolean scopeIsPrototype = XMLParserUtils.checkBeanScope(filePath, "book1", "prototype");

		// Validate the results using yakshaAssert
		if (!titlePropertyExists) {
			System.out.println("Failure: 'title' property for 'book1' is either missing or has the wrong value.");
		}
		if (!authorPropertyExists) {
			System.out.println("Failure: 'author' property for 'book1' is either missing or has the wrong value.");
		}
		if (!scopeIsPrototype) {
			System.out.println("Failure: 'scope' for 'book1' is not set to 'prototype'.");
		}

		yakshaAssert(currentTest(), titlePropertyExists && authorPropertyExists && scopeIsPrototype, businessTestFile);
	}

	@Test
	public void testPatronBeanPropertyFields() throws IOException {
		String filePath = "src/main/resources/applicationContext.xml";

		// Check if the "name" property exists and has the correct value
		boolean namePropertyExists = XMLParserUtils.checkPropertyExists(filePath, "patron1", "name", "John Doe");

		// Check if the "id" property exists and has the correct value
		boolean idPropertyExists = XMLParserUtils.checkPropertyExists(filePath, "patron1", "id", "12345");

		// Check if the 'scope' of the Patron bean is 'prototype'
		boolean scopeIsPrototype = XMLParserUtils.checkBeanScope(filePath, "patron1", "prototype");

		// Validate the results using yakshaAssert
		if (!namePropertyExists) {
			System.out.println("Failure: 'name' property for 'patron1' is either missing or has the wrong value.");
		}
		if (!idPropertyExists) {
			System.out.println("Failure: 'id' property for 'patron1' is either missing or has the wrong value.");
		}
		if (!scopeIsPrototype) {
			System.out.println("Failure: 'scope' for 'patron1' is not set to 'prototype'.");
		}

		yakshaAssert(currentTest(), namePropertyExists && idPropertyExists && scopeIsPrototype, businessTestFile);
	}
}
