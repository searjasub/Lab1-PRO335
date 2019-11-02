package pro335.lab1;

public class Orders {

    private int OrderID;
    private int CustomerID;
    private long total;

    public Orders(int orderID, int customerID, long total) {
        setOrderID(orderID);
        setCustomerID(customerID);
        setTotal(total);
    }

    public Orders() {}

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


}
