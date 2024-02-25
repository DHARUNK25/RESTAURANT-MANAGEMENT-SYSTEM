package com.TableManagement;

public class Table {
    private String tableType;
    private double price;
    private String availabilityStatus;
    public Table(String tableType, double price, String availabilityStatus) {
        this.tableType = tableType;
        this.price = price;
        this.availabilityStatus = availabilityStatus;
    }
    public String getTableType() {
        return tableType;
    }
    public void setTableType(String tableType) {
        this.tableType = tableType;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getAvailabilityStatus() {
        return availabilityStatus;
    }
    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
    @Override
    public String toString() {
        return "Table{" +
                ", Table Type='" + tableType + '\'' +
                ", Price=" + price +
                ", Availability Status='" + availabilityStatus + '\'' +
                '}';
    }
}

