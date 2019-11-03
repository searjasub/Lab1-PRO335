package pro335.lab1.model;

public class OrderLine {

    private int orderLineId;
    private int orderId;
    private int qty;
    private int price;
    private int total;
    private int productId;


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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Order ID: " + getOrderId() + " OrderLine ID: " + getOrderLineId() + " Product ID: " + getProductId() + " Price: " + getPrice() + " Qty: " + getQty() + " Total: " + getTotal();
    }
}
