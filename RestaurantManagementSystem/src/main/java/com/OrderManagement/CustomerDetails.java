package com.OrderManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class CustomerDetails {
	public static int getCustomerId(Connection con, String email) {
    	int customerID = -1;
        String selectQuery = "SELECT CUSTOMERID FROM DATABASE.CUSTOMER WHERE EMAIL = ?";
        try (
             PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    customerID = resultSet.getInt("CUSTOMERID");
                }
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }

        return customerID;
    }
	public static int getTableId(Connection connection, int customerId) {
	    int tableId = -1;    
	    String selectQuery = "SELECT TABLESID FROM DATABASE.RESERVATION WHERE CUSTOMERID = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
	        preparedStatement.setInt(1, customerId);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                tableId = resultSet.getInt("TABLESID");
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Database Error: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return tableId;
	}
}
