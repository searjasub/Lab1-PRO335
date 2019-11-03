package pro335.lab1.model;

public class OrderLine {

    private int orderLineId;
    private int orderId;
    private int qty;
    private double price;
    private double total;
    private int productId;

    public OrderLine(int orderLineId, int orderId, int qty, double price, double total, int productId) {
        this.setOrderLineId(orderLineId);
        this.setOrderId(orderId);
        this.setQty(qty);
        this.setPrice(price);
        this.setTotal(total);
        this.setProductId(productId);
    }

    public int getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(int orderLineId) {
        this.orderLineId = orderLineId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
