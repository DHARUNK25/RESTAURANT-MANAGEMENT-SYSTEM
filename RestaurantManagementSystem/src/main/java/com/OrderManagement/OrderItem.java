package com.OrderManagement;

public class OrderItem {
    private int orderItemID;
    private int customerId; 
    private int quantity;
    private String specialRequest;
    public OrderItem(int orderItemID, int customerId, int quantity, String specialRequest) {
        this.orderItemID = orderItemID;
        this.customerId = customerId;
        this.quantity = quantity;
        this.specialRequest = specialRequest;
    }
    public int getOrderItemID() {
        return orderItemID;
    }
    public void setOrderItemID(int orderItemID) {
        this.orderItemID = orderItemID;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getSpecialRequest() {
        return specialRequest;
    }
    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }
    @Override
    public String toString() {
        return "OrderItem{" +
                "orderItemID=" + orderItemID +
                ", customerId=" + customerId +
                ", quantity=" + quantity +
                ", specialRequest='" + specialRequest + '\'' +
                '}';
    }
    
}
