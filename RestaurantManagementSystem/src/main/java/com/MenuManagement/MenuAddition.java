package com.MenuManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.DriverPackage.DBConnection;
import com.ExceptionHandling.ExceptionHandle;

public class MenuAddition {

    public static void addMenu() {
        Scanner sc = new Scanner(System.in);
        // Get details with validation
        System.out.println("Enter Item Name : ");
        String itemName = sc.nextLine();
        System.out.println("Enter category of the item : ");
        String category = ExceptionHandle.getValidString(sc);
        System.out.println("Enter description for the item : ");
        String description = sc.nextLine();
        System.out.println("Enter price for the item : ");
        double price = ExceptionHandle.getAmount(sc);
        sc.nextLine();
        System.out.println("Enter the availability status : ");
        String status = ExceptionHandle.getValidString(sc);
        Menu menu = new Menu(itemName, category, description, price, status);
        // Call the method to add the menu to the database
        addMenuToDatabase(menu);
    }

    public static void addMenuToDatabase(Menu menu) {
        final String itemIdQuery = "SELECT MAX(ITEMID) AS MAXITEMID FROM DATABASE.MENU";
        final String adminIdQuery = "SELECT ADMINID FROM DATABASE.ADMIN";
        String insertQuery = "INSERT INTO DATABASE.MENU( ITEMID,ITEMNAME,CATEGORY,DESCRIPTION,PRICE,AVAILABILITYSTATUS,ADMINID) VALUES (?,?,?,?,?,?,?)";

        try (Connection connection = DBConnection.doDBConnection();) {
            // get max item id
            PreparedStatement itemIdStatement = connection.prepareStatement(itemIdQuery);
            ResultSet itemIdResult = itemIdStatement.executeQuery();
            int maxItemId = 0;
            if (itemIdResult.next())
            	maxItemId = itemIdResult.getInt("MAXITEMID");
            itemIdResult.close();
            itemIdStatement.close();
            // Get Admin Id
            PreparedStatement adminIdtStatement = connection.prepareStatement(adminIdQuery);
            ResultSet adminIdResult = adminIdtStatement.executeQuery();
            int adminId = 0;
            if (adminIdResult.next())
                adminId = adminIdResult.getInt("ADMINID");
            adminIdResult.close();
            adminIdtStatement.close();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            // Set parameters in the prepared statement
            preparedStatement.setInt(1, maxItemId + 1);
            preparedStatement.setString(2, menu.getItemName());
            preparedStatement.setString(3, menu.getCategory());
            preparedStatement.setString(4, menu.getDescription());
            preparedStatement.setDouble(5, menu.getPrice());
            preparedStatement.setString(6, menu.getStatus());
            preparedStatement.setInt(7, adminId);
            // Execute the update
            int menuAffected = preparedStatement.executeUpdate();

            if (menuAffected > 0) {
                System.out.println("New Dish added Successfully.");
            } else {
                System.out.println("Failed to add new dish in menu.");
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
