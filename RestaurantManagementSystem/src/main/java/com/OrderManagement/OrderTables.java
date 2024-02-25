package com.OrderManagement;

public class OrderTables {
    private int orderID;
    private int customerID;
    private int tableID;
    public OrderTables(int customerID,int tableID) {
        this.customerID = customerID;
        this.tableID = tableID;
    }
    public int getCustomerID() {
        return customerID;
    }
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    public int getTableID() {
        return tableID;
    }
    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public double getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
    
    @Override
    public String toString() {
        return "Order{" +
                "CustomerID=" + customerID +
                ", TableID=" + tableID +
                ", OrderID='" + orderID + '\'' +
                '}';
    }
}

