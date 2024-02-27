package com.TableManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import com.DriverPackage.DBConnection;
import com.ExceptionHandling.ExceptionHandle;
public class TableUpdation {
    static Scanner sc = new Scanner(System.in);
    public static void updateTable() {
        System.out.println("Table ID Updation ");
        int tableId = ExceptionHandle.getValidTableId(sc);
        sc.nextLine();
        System.out.println("Enter new table type to update: ");
        String newTableType = ExceptionHandle.getValidString(sc);
        System.out.println("Enter new table Price to update: ");
        double newPrice = ExceptionHandle.getAmount(sc);
        sc.nextLine(); // consume the newline character
        System.out.println("Enter new table status to update: ");
        String newAvailabilityStatus = ExceptionHandle.getValidString(sc);
        updateTableToDatabase(tableId, newTableType, newPrice, newAvailabilityStatus);
    }
    public static void updateTableToDatabase(int tableId, String newTableType, double newPrice, String newAvailabilityStatus) {
        String checkQuery = "SELECT * FROM DATABASE.TABLES WHERE TABLESID = ?";
        String updateQuery = "UPDATE DATABASE.TABLES SET TABLETYPE = ?, PRICE = ?, AVAILABILITYSTATUS = ? WHERE TABLESID = ?";
        try (Connection connection = DBConnection.doDBConnection();
                PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
            // Check if table exists
            checkStatement.setInt(1, tableId);
            if (checkStatement.executeQuery().next()) {
                System.out.println("Table exists.");
                // Update the table
                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setString(1, newTableType);
                    updateStatement.setDouble(2, newPrice);
                    updateStatement.setString(3, newAvailabilityStatus);
                    updateStatement.setInt(4, tableId);
                    // Execute the update
                    int rowsUpdated = updateStatement.executeUpdate();
                    String updateResult = rowsUpdated > 0 ? "Table Details updated Successfully" : "Table Details not updated";
                    System.out.println(updateResult);
                }
            } else {
                System.out.println("Table Not found.");
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
