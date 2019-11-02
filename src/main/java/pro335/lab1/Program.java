package pro335.lab1;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;

public class Program {

    private List<Customer> customerList = new ArrayList<>();
    private List<Orders> orderList = new ArrayList<>();
    private List<OrderLines> orderLineList = new ArrayList<>();

    private Connection connection;

    public static void main(String[] args) {

        String path = "\\customers.xml";


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
        try() {

        }
    }
}
