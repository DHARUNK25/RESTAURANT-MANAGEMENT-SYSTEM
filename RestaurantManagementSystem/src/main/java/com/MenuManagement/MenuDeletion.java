package com.MenuManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import com.DriverPackage.DBConnection;
import com.ExceptionHandling.ExceptionHandle;

public class MenuDeletion {

    public static void delete() {
        Scanner sc = new Scanner(System.in);
        MenuView.viewMenuData();
        System.out.println("Enter the ItemId of the Menu item you want to delete : ");
        int itemId = ExceptionHandle.getInput(sc);
        deleteMenuItemFromDatabase(itemId);
    }
    public static void deleteMenuItemFromDatabase(int itemId) {
        String deleteQuery = "DELETE FROM DATABASE.MENU WHERE ITEMID =?";
        try (Connection connection = DBConnection.doDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, itemId);
            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Menu item deleted successfully.");
            } else {
                System.out.println("Failed to delete the menu item.");
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

