package pro335.lab1.controller;

import pro335.lab1.model.Customer;
import pro335.lab1.model.OrderLines;
import pro335.lab1.model.Orders;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Driver {

    public void run() {

        String path = "\\customers.xml";

        List<Customer> customerList = xmlCustomerParse(path);
        List<Orders> orderList = xmlOrdersParse(path);
        List<OrderLines> orderLineList = xmlOrderLinesParse(path);

    }

    private List<Customer> xmlCustomerParse(String filePath) {
        return null;
    }

    private List<Orders> xmlOrdersParse(String filePath) {
        return null;
    }

    private List<OrderLines> xmlOrderLinesParse(String filePath) {
        List<OrderLines> orderLinesList = new ArrayList<>();
        OrderLines orderLines = new OrderLines();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(filePath));

            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();

                if(xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();

                    if(startElement.getName().getLocalPart().equals("OrderLineID")) {
                        orderLines.setOrderLineId(Integer.parseInt(xmlEvent.asCharacters().getData()));

                    }
                }
            }

        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void databaseEntry(List<Customer> customerList, List<Orders> ordersList, List<OrderLines> orderLinesList) {

    }
}
