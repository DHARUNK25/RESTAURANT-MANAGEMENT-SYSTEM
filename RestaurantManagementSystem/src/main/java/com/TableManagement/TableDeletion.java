package com.TableManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import com.DriverPackage.DBConnection;
import com.ExceptionHandling.ExceptionHandle;

public class TableDeletion {
    public static void delete() {
        Scanner sc = new Scanner(System.in);
        TableView.viewTableData();
        int tableId = ExceptionHandle.getValidTableId(sc);
        deleteTableFromDatabase(tableId);
    }
    public static void deleteTableFromDatabase(int tableId) {
        String deleteQuery = "DELETE FROM DATABASE.TABLES WHERE TABLESID =?";
        try (Connection connection = DBConnection.doDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, tableId);
            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Table deleted successfully.");
            } else {
                System.out.println("Failed to delete the table.");
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
