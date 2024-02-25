package com.MenuManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import com.DriverPackage.DBConnection;
import com.ExceptionHandling.ExceptionHandle;

public class MenuUpdation {
    static Scanner sc = new Scanner(System.in);

    public static void updateMenu() {
        System.out.println("Enter the ItemId of the menu item: ");
        int itemId = ExceptionHandle.getInput(sc);
        sc.nextLine();
        System.out.println("Enter new Item Name to update: ");
        String newItemName = sc.nextLine();
        System.out.println("Enter new Category to update: ");
        String newCategory = ExceptionHandle.getValidString(sc);
        System.out.println("Enter new Description to update: ");
        String newDescription = sc.nextLine();
        System.out.println("Enter new Price to update: ");
        double newPrice = ExceptionHandle.getAmount(sc);
        sc.nextLine(); // Consume the newline character
        System.out.println("Enter new Availability Status to update: ");
        String newAvailabilityStatus = ExceptionHandle.getValidString(sc);
        updateMenuToDatabase(itemId, newItemName, newCategory, newDescription, newPrice, newAvailabilityStatus);
    }
    public static void updateMenuToDatabase(int itemId, String newItemName, String newCategory,String newDescription, double newPrice, String newAvailabilityStatus) {
        String checkQuery = "SELECT * FROM DATABASE.MENU WHERE ITEMID = ?";
        String updateQuery = "UPDATE DATABASE.MENU SET ITEMNAME = ?, CATEGORY = ?, DESCRIPTION = ?,PRICE = ?, AVAILABILITYSTATUS = ? WHERE ITEMID = ?";
        try (Connection connection = DBConnection.doDBConnection();
             PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
            // Check if menu item exists
            checkStatement.setInt(1, itemId);
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Menu item exists.");
                // Update the menu item
                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setString(1, newItemName);
                    updateStatement.setString(2, newCategory);
                    updateStatement.setString(3, newDescription);
                    updateStatement.setDouble(4, newPrice);
                    updateStatement.setString(5, newAvailabilityStatus);
                    updateStatement.setInt(6, itemId);
                    // Execute the update
                    int rowsUpdated = updateStatement.executeUpdate();
                    String updateResult = rowsUpdated > 0 ? "Menu updated Successfully " : "Menu not updated";
                    System.out.println(updateResult);
                }
            } else {
                System.out.println("Menu item not found.");
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

