package pro335.lab1.controller;

import pro335.lab1.model.Customer;
import pro335.lab1.model.Order;
import pro335.lab1.model.OrderLine;

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
        List<Order> orderList = xmlOrdersParse(path);
        List<OrderLine> orderLineList = xmlOrderLinesParse(path);

        System.out.println("******* CUSTOMERS *******");
        for (Customer customer : customerList) {
            System.out.println(customer.toString());
        }

        System.out.println("******* ORDERS *******");
        for (Order order : orderList) {
            System.out.println(order.toString());
        }

        for (int i = 0; i < orderLineList.size(); i++) {
            if (orderLineList.get(i).getOrderId() != 0) {
                continue;
            } else if (orderLineList.get(i + 1).getOrderId() == 0) {
                orderLineList.get(i).setOrderId(orderLineList.get(i -1).getOrderId() + 1);
            } else {
                orderLineList.get(i).setOrderId(orderLineList.get(i + 1).getOrderId());
            }
        }

        System.out.println("******* ORDER LINES *******");
        for (OrderLine orderLine : orderLineList) {
            System.out.println(orderLine.toString());
        }

//        connectToDb();
//        insertCustomersToDb(customerList);
    }

    private List<Customer> xmlCustomerParse(String filePath) {

        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        int id = -1;

        System.out.println("Parsing customers");
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(filePath));
            int counter = 0;
            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    if (startElement.getName().getLocalPart().equals("Customer")) {
                        customer = new Customer();
                    } else if (startElement.getName().getLocalPart().equals("Age")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        customer.setAge(Integer.parseInt(xmlEvent.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals("CustomerId")) {
                        if (id == -1) {
                            xmlEvent = xmlEventReader.nextEvent();
                            id = Integer.parseInt(xmlEvent.asCharacters().getData());
                            customer.setCustomerId(id);
                        }
                    } else if (startElement.getName().getLocalPart().equals("Email")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        customer.setEmail(xmlEvent.asCharacters().getData());
                    } else if (startElement.getName().getLocalPart().equals("Name")) {
                        xmlEvent = xmlEventReader.nextEvent();
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
//                System.out.println(counter);
//                counter++;

            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return customers;
    }

    private List<Order> xmlOrdersParse(String filePath) {
        List<Order> orders = new ArrayList<>();
        Order order = new Order();

        System.out.println("Parsing orders");
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(filePath));
            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    if (startElement.getName().getLocalPart().equals("Order")) {
                        order = new Order();
                    } else if (startElement.getName().getLocalPart().equals("CustomerId")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        String data = xmlEvent.asCharacters().getData();
                        int customerId = Integer.parseInt(data);
                        order.setCustomerID(customerId);
                    } else if (startElement.getName().getLocalPart().equals("Lines")) {
                        xmlEvent = xmlEventReader.nextEvent();
                    } else if (startElement.getName().getLocalPart().equals("OrderId")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        order.setOrderID(Integer.parseInt(xmlEvent.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals("Total")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        order.setTotal(Integer.parseInt(xmlEvent.asCharacters().getData()));
                    }
                }
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("Order")) {
                        orders.add(order);
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private List<OrderLine> xmlOrderLinesParse(String filePath) {
        List<OrderLine> orderLines = new ArrayList<>();
        OrderLine orderLine = new OrderLine();

        System.out.println("Parsing order lines");

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(filePath));
            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    if (startElement.getName().getLocalPart().equals("OrderLine")) {
                        orderLine = new OrderLine();
                    } else if (startElement.getName().getLocalPart().equals("OrderId")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        orderLine.setOrderId(Integer.parseInt(xmlEvent.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals("OrderLineId")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        orderLine.setOrderLineId(Integer.parseInt(xmlEvent.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals("Price")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        orderLine.setPrice(Integer.parseInt(xmlEvent.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals("ProductId")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        orderLine.setProductId(Integer.parseInt(xmlEvent.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals("Qty")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        orderLine.setQty(Integer.parseInt(xmlEvent.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals("Total")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        orderLine.setTotal(Integer.parseInt(xmlEvent.asCharacters().getData()));
                    }
                }
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("OrderLine")) {
                        orderLines.add(orderLine);
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return orderLines;
    }

    private void databaseEntry(List<Customer> customerList, List<Order> ordersList, List<OrderLine> orderLinesList) {

    }

    private void connectToDb() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection("jdbc://sqlserver://localhost;databaseName=customers;user=lab1;password=lab1");
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

    private void insertOrdersToDb(List<Order> orders) {
        String sql = "INSERT INTO Orders (OrderId, CustomerId, Total) " +
                "VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Order order : orders) {
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
