package com.PaymentDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import com.DriverPackage.DBConnection;
public class Bill {
    
    public static void generateBill(double amount) {
    	final String restaurantName = "DELECIOUS BITES";
        // Get the current date for the bill
        Date currentDate = new Date();
        Date billDate = new Date(currentDate.getTime());
        // Insert the bill details into the database
        final String orderIdQuery = "SELECT ORDERID  FROM DATABASE.ORDERTABLES";
        String insertQuery = "INSERT INTO DATABASE.BILL (BILLID, ORDERID, BILLDATE, TOTALAMOUNT) VALUES (?, ?, ?, ?)";
        String billIdQuery = "SELECT MAX(BILLID) AS MAXBILLID FROM DATABASE.BILL";
        try (Connection connection = DBConnection.doDBConnection()) {
            PreparedStatement orderIdStatement = connection.prepareStatement(orderIdQuery);
            ResultSet orderIdResult = orderIdStatement.executeQuery();
            int orderId = 0;
            if (orderIdResult.next()) {
            	orderId = 1+orderIdResult.getInt("ORDERID");
            }
            orderIdResult.close();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            PreparedStatement billIdStatement = connection.prepareStatement(billIdQuery);
            ResultSet billIdResult = billIdStatement.executeQuery();
            int maxBillId = 0;
            if (billIdResult.next()) {
                maxBillId = billIdResult.getInt("MAXBILLID");
            }
            billIdResult.close();
            billIdStatement.close();
            preparedStatement.setInt(1, maxBillId+1);
            preparedStatement.setInt(2, orderId);
            preparedStatement.setDate(3, new java.sql.Date(billDate.getTime()));
            preparedStatement.setDouble(4, amount); // Use the retrieved price as total amount
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
            	System.out.println("------------------------------------------------");
            	System.out.println("Restaurant Name : "+restaurantName);
            	System.out.println("Bill ID : "+maxBillId);
            	System.out.println("Order ID : "+orderId);
            	System.out.println("Bill Date : "+new java.sql.Date(billDate.getTime()));
            	System.out.println("Total Amount : "+amount);
                System.out.println("Bill generated successfully."); 
                System.out.println("------------------------------------------------");
            } else {
                System.out.println("Failed to generate the bill.");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
