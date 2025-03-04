package com.yaksha.assignment.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParserUtils {

	/**
	 * Parses the given XML file and checks if the required beans and properties are
	 * present.
	 *
	 * @param filePath              Full path to the XML configuration file (e.g.,
	 *                              "src/main/resources/applicationContext.xml").
	 * @param expectedBeanId        The expected bean id to be found in the XML.
	 * @param expectedProperty      The expected property name in the bean (e.g.,
	 *                              title).
	 * @param expectedPropertyValue The expected value for the property (e.g., "The
	 *                              Great Gatsby").
	 * @return true if all checks pass, false otherwise.
	 */
	public static boolean checkXMLStructure(String filePath, String expectedBeanId, String expectedProperty,
			String expectedPropertyValue) {

		try {
			// Parse the XML file
			File xmlFile = new File(filePath);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);
			document.getDocumentElement().normalize();

			// Check for the required bean ID
			NodeList beanNodes = document.getElementsByTagName("bean");
			List<String> beanIds = new ArrayList<>();
			for (int i = 0; i < beanNodes.getLength(); i++) {
				Node beanNode = beanNodes.item(i);
				if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) beanNode;
					String beanId = element.getAttribute("id");
					beanIds.add(beanId);

					// Check for the expected property in the bean
					if (beanId.equals(expectedBeanId)) {
						NodeList propertyNodes = element.getElementsByTagName("property");
						for (int j = 0; j < propertyNodes.getLength(); j++) {
							Node propertyNode = propertyNodes.item(j);
							if (propertyNode.getNodeType() == Node.ELEMENT_NODE) {
								Element propertyElement = (Element) propertyNode;
								String propertyName = propertyElement.getAttribute("name");
								String propertyValue = propertyElement.getAttribute("value");

								// Check if the expected property and value match
								if (propertyName.equals(expectedProperty)
										&& propertyValue.equals(expectedPropertyValue)) {
									return true;
								}
							}
						}
					}
				}
			}

			// If no matching bean or property is found, return false
			return false;

		} catch (Exception e) {
			System.out.println("Error parsing the XML file: " + filePath);
			e.printStackTrace();
			return false;
		}
	}

	public static boolean checkBeanScope(String filePath, String beanId, String expectedScope) {
		try {
			File xmlFile = new File(filePath);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);
			document.getDocumentElement().normalize();

			NodeList beanNodes = document.getElementsByTagName("bean");
			for (int i = 0; i < beanNodes.getLength(); i++) {
				Node beanNode = beanNodes.item(i);
				if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) beanNode;
					String beanIdValue = element.getAttribute("id");
					String scope = element.getAttribute("scope");

					// If scope is missing or not 'prototype', fail the test
					if (beanId.equals(beanId)) {
						if (scope.isEmpty()) {
							// If scope is missing, fail the test
							System.out.println("Failure: 'scope' for bean '" + beanId + "' is missing.");
							return false;
						}
						if (!scope.equals(expectedScope)) {
							// If scope is not 'prototype', fail the test
							System.out.println("Failure: 'scope' for bean '" + beanId
									+ "' is not set to 'prototype'. Found: " + scope);
							return false;
						}
						return true; // Scope is correct
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false; // Bean not found or scope mismatch
	}

	public static boolean checkPropertyExists(String filePath, String beanId, String propertyName,
			String expectedValue) {
		try {
			File xmlFile = new File(filePath);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);
			document.getDocumentElement().normalize();

			// Get all bean nodes from the XML
			NodeList beanNodes = document.getElementsByTagName("bean");

			// Loop through each bean to check for the required properties
			for (int i = 0; i < beanNodes.getLength(); i++) {
				Node beanNode = beanNodes.item(i);
				if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) beanNode;
					String beanIdValue = element.getAttribute("id");

					// Check if the current bean matches the required beanId
					if (beanId.equals(beanIdValue)) {
						NodeList propertyNodes = element.getElementsByTagName("property");

						// Loop through all properties of the bean
						for (int j = 0; j < propertyNodes.getLength(); j++) {
							Node propertyNode = propertyNodes.item(j);
							if (propertyNode.getNodeType() == Node.ELEMENT_NODE) {
								Element propertyElement = (Element) propertyNode;
								String propertyNameValue = propertyElement.getAttribute("name");
								String propertyValue = propertyElement.getAttribute("value");

								// Check if the property name and value match the expected values
								if (propertyName.equals(propertyNameValue)) {
									if (propertyValue.equals(expectedValue)) {
										return true; // Property exists with the expected value
									} else {
										// Property value mismatch
										System.out.println("Property value mismatch for " + propertyName
												+ ". Expected: " + expectedValue + " but found: " + propertyValue);
										return false; // Property value mismatch
									}
								}
							}
						}

						// If no matching property is found
						System.out.println("Property " + propertyName + " not found for bean " + beanId);
						return false; // Property does not exist
					}
				}
			}

			// If no matching bean is found
			System.out.println("Bean " + beanId + " not found.");
			return false; // Bean does not exist
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false; // In case of any exception
	}

}
