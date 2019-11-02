package pro335.lab1.controller;

import pro335.lab1.model.Customer;
import pro335.lab1.model.OrderLines;
import pro335.lab1.model.Orders;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Driver {

    Connection connection;

    public void run() {

        String path = "\\customers.xml";

        List<Customer> customerList;
        List<Orders> orderList;
        List<OrderLines> orderLineList;

    }

    private List<Customer> xmlCustomerParse(String filePath) {
        return null;
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
            connection = DriverManager.getConnection("jdbc://sqlserver://localhost;databaseName=customers", "lab1", "lab1";
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertCustomersToDb(List<Customer> customers) {
        String sql = "INSERT INTO Customers (CustomerID, Name, Email, Age) " +
                "VALUES (?, ?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            for(Customer customer : customers) {
                statement.setInt(1, customer.setCustomerId());
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

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            for(Orders order : orders) {
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
