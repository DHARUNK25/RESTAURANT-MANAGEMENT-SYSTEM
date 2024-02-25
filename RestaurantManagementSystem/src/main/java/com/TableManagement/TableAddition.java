package com.TableManagement;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.DriverPackage.DBConnection;
import com.ExceptionHandling.ExceptionHandle;

public class TableAddition {

    public static void addTable() {
        Scanner sc = new Scanner(System.in);
        // Get details with validation
        System.out.println("Enter Table type : ");
        String tableType = ExceptionHandle.getValidString(sc);
        System.out.println("Enter Table price : ");
        double price = ExceptionHandle.getAmount(sc);
        sc.nextLine();
        System.out.println("Enter Availability Status : ");
        String status = ExceptionHandle.getValidString(sc));

        Table table = new Table(tableType, price, status);
        // Call the method to add the table to the database
        addTableToDatabase(table);
    }

    public static void addTableToDatabase(Table table) {
        String tableIdQuery = "SELECT MAX(TABLESID) AS MAXTABLESID FROM DATABASE.TABLES";
        String restaurantIdQuery = "SELECT RESTAURANTID FROM DATABASE.RESTAURANT";
        String insertQuery = "INSERT INTO DATABASE.TABLES(TABLESID, RESTAURANTID, AVAILABILITYSTATUS, EMPLOYEEID,TABLETYPE,	PRICE) VALUES (?,?,?,?,?,?)";
        final String employeeIdQuery = "SELECT EMPLOYEEID  FROM DATABASE.EMPLOYEE";
        try (Connection connection = DBConnection.doDBConnection();) {
            // get max table id
            PreparedStatement tableIdStatement = connection.prepareStatement(tableIdQuery);
            ResultSet tableIdResult = tableIdStatement.executeQuery();
            int maxTableId = 0;
            if (tableIdResult.next())
                maxTableId = tableIdResult.getInt("MAXTABLESID");
            tableIdResult.close();
            tableIdStatement.close();
            
            // get max table id
            PreparedStatement resIdStatement = connection.prepareStatement(restaurantIdQuery);
            ResultSet resIdResult = resIdStatement.executeQuery();
            int restaurantId = 0;
            if (resIdResult.next())
            	restaurantId = resIdResult.getInt("RESTAURANTID");
            resIdResult.close();
            resIdStatement.close();
            
         // Get Max Employee Id
            PreparedStatement employeeIdStatement = connection.prepareStatement(employeeIdQuery);
            ResultSet employeeIdResult = employeeIdStatement.executeQuery();
            int employeeId = 0;
            if (employeeIdResult.next())
                employeeId = employeeIdResult.getInt("EMPLOYEEID");
            employeeIdResult.close();
            employeeIdStatement.close();

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            // Set parameters in the prepared statement
            preparedStatement.setInt(1, maxTableId + 1);
            preparedStatement.setInt(2, restaurantId);
            preparedStatement.setString(3, table.getAvailabilityStatus());
            preparedStatement.setInt(4, employeeId);
            preparedStatement.setString(5, table.getTableType());
            preparedStatement.setDouble(6, table.getPrice());           
            // Execute the update
            int tableAffected = preparedStatement.executeUpdate();

            if (tableAffected > 0) {
                System.out.println("New Table added Successfully.");
            } else {
                System.out.println("Failed to add new table.");
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

