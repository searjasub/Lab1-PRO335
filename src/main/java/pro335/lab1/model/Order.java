package pro335.lab1.model;

public class Order {

    private int OrderID;
    private int CustomerID;
    private long total;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    @Override
    public String toString() {
        return "Customer ID: " + getCustomerID() + " Order ID: " + getOrderID() + " Total: " + getTotal();
    }
}
