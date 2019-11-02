package pro335.lab1.controller;

import pro335.lab1.model.Customer;
import pro335.lab1.model.OrderLines;
import pro335.lab1.model.Orders;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Driver {

    Connection connection;

    public void run() {

        String path = "test.xml";

        List<Customer> customerList = xmlCustomerParse(path);
        List<Orders> orderList;
        List<OrderLines> orderLineList;

        for (Customer customer : customerList) {
            System.out.println(customer.toString());
        }
    }

    private List<Customer> xmlCustomerParse(String filePath) {

        List<Customer> customers = new ArrayList<>();
        Customer customer = null;
        int id = -1;

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(filePath));
            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    if (startElement.getName().getLocalPart().equals("Customer")) {
                        customer = new Customer();
                    } else if (startElement.getName().getLocalPart().equals("Age")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        System.out.println(Integer.parseInt(xmlEvent.asCharacters().getData()));
                        customer.setAge(Integer.parseInt(xmlEvent.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals("CustomerId")) {
                        if (id == -1) {
                            xmlEvent = xmlEventReader.nextEvent();
                            System.out.println(Integer.parseInt(xmlEvent.asCharacters().getData()));
                            id = Integer.parseInt(xmlEvent.asCharacters().getData());
                            customer.setCustomerId(id);
                        }
                    } else if (startElement.getName().getLocalPart().equals("Email")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        System.out.println(xmlEvent.asCharacters().getData());
                        customer.setEmail(xmlEvent.asCharacters().getData());
                    } else if (startElement.getName().getLocalPart().equals("Name")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        System.out.println(xmlEvent.asCharacters().getData());
                        customer.setName(xmlEvent.asCharacters().getData());
                    } else if (startElement.getName().getLocalPart().equals("Orders")) {
                        xmlEvent = xmlEventReader.nextEvent();
                    }
                }
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("Customer")) {
                        customers.add(customer);
                        id = -1;
                    }
                }

            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return customers;
    }

    private List<Orders> xmlOrdersParse(String filePath) {
        return null;
    }

    private List<OrderLines> xmlOrderLinesParse(String filePath) {
        return null;
    }

    private void databaseEntry(List<Customer> customerList, List<Orders> ordersList, List<OrderLines> orderLinesList) {

    }

    private void connectToDb() {
        try {
            connection = DriverManager.getConnection("jdbc://sqlserver://localhost;databaseName=customers", "lab1", "lab1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertCustomersToDb(List<Customer> customers) {
        String sql = "INSERT INTO Customers (CustomerID, Name, Email, Age) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Customer customer : customers) {
                statement.setInt(1, customer.getCustomerId());
                statement.setString(2, customer.getName());
                statement.setString(3, customer.getEmail());
                statement.setInt(4, customer.getAge());

                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertOrderssToDb(List<Orders> orders) {
        String sql = "INSERT INTO Orders (OrderId, CustomerId, Total) " +
                "VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Orders order : orders) {
                statement.setInt(1, order.getOrderID());
                statement.setInt(2, order.getCustomerID());
                statement.setLong(3, order.getTotal());

                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
