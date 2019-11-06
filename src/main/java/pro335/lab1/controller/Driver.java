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

        String path = "customers.xml";

        List<Customer> customerList = xmlCustomerParse(path);
        List<Order> orderList = xmlOrdersParse(path);
        List<OrderLine> orderLineList = xmlOrderLinesParse(path);

        for (int i = 0; i < orderLineList.size(); i++) {
            if (orderLineList.get(i).getOrderId() != 0) {
                continue;
            } else if (orderLineList.get(i + 1).getOrderId() == 0) {
                orderLineList.get(i).setOrderId(orderLineList.get(i - 1).getOrderId() + 1);
            } else {
                orderLineList.get(i).setOrderId(orderLineList.get(i + 1).getOrderId());
            }
        }

        connectToDb();
        bulkInsertCustomer(customerList);
        bulkInsertOrder(orderList);
        bulkInsertOrderLine(orderLineList);
    }

    private List<Customer> xmlCustomerParse(String filePath) {

        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        int id = -1;

        System.out.println("Parsing customers");
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


    private void connectToDb() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=customers;user=lab1;password=lab1");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void bulkInsertCustomer(List<Customer> customers) {
        String sql = "INSERT INTO Customers (CustomerID, Name, Email, Age) " +
                "VALUES (?, ?, ?, ?)";
        int customerCounter = 0;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            for (Customer customer : customers) {
                statement.setInt(1, customer.getCustomerId());
                statement.setString(2, customer.getName());
                statement.setString(3, customer.getEmail());
                statement.setInt(4, customer.getAge());

                statement.addBatch();
                customerCounter++;

                if (customerCounter > 1000) {
                    statement.executeBatch();
                    customerCounter = 0;
                }
            }
            statement.executeBatch();
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

    private void bulkInsertOrder(List<Order> orders) {
        String sql = "INSERT INTO Orders (OrderId, CustomerId, Total) " +
                "VALUES (?, ?, ?)";
        int orderCounter = 0;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Order order : orders) {
                statement.setInt(1, order.getOrderID());
                statement.setInt(2, order.getCustomerID());
                statement.setLong(3, order.getTotal());

                statement.addBatch();
                orderCounter++;

                if (orderCounter > 1000) {
                    statement.executeBatch();
                    orderCounter = 0;
                }
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void bulkInsertOrderLine(List<OrderLine> orderLines) {
        String sql = "INSERT INTO OrderLines (OrderLineId, OrderId, Qty, Price, ProductId) " +
                "VALUES (?, ?, ?, ?, ?)";
        int orderLineCounter = 0;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (OrderLine orderLine : orderLines) {
                statement.setInt(1, orderLine.getOrderLineId());
                statement.setInt(2, orderLine.getOrderId());
                statement.setInt(3, orderLine.getQty());
                statement.setInt(4, orderLine.getPrice());
                statement.setInt(5, orderLine.getProductId());

                statement.addBatch();
                orderLineCounter++;

                if (orderLineCounter > 1000) {
                    statement.executeBatch();
                    orderLineCounter = 0;
                }
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
