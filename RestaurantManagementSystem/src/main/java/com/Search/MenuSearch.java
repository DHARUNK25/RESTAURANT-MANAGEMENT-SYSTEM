package com.Search;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.ExceptionHandling.ExceptionHandle;
public class MenuSearch implements Searchable {
    @Override
    public void searchByCategory(Connection con) {
    	Scanner sc = new Scanner(System.in);
    	System.out.println("Categories");
    	System.out.println("Main Course, Breakfast, Appetizer, Beverage");
    	System.out.print("Enter the category to search dish items : ");
    	String category = ExceptionHandle.getValidString(sc);
        System.out.println("Search Results for Category: " + category);
        String searchQuery = "SELECT * FROM DATABASE.MENU WHERE CATEGORY = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(searchQuery)) {
            preparedStatement.setString(1, category);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    System.out.println("Item ID: " + resultSet.getInt("ITEMID"));
                    System.out.println("Item Name: " + resultSet.getString("ITEMNAME"));
                    System.out.println("Category: " + resultSet.getString("CATEGORY"));
                    System.out.println("Description: " + resultSet.getString("DESCRIPTION"));
                    System.out.println("Price: " + resultSet.getDouble("PRICE"));
                    System.out.println("Availability Status: " + resultSet.getString("AVAILABILITYSTATUS"));
//                  System.out.println("Admin ID: " + resultSet.getInt("ADMINID"));
                    System.out.println("--------------------------------------");
                }
            }

        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void searchByPrice(Connection con) {
        String minQuery = "SELECT MIN(PRICE) AS MINPRICE FROM DATABASE.MENU";
        String maxQuery = "SELECT MAX(PRICE) AS MAXPRICE FROM DATABASE.MENU";
        String searchQuery = "SELECT * FROM DATABASE.MENU WHERE PRICE BETWEEN ? AND ?";
        try (PreparedStatement minStatement = con.prepareStatement(minQuery);
        	 PreparedStatement maxStatement = con.prepareStatement(maxQuery);
        	 PreparedStatement preparedStatement = con.prepareStatement(searchQuery)) {
        	 ResultSet minSet = minStatement.executeQuery();
        	 double minPrice = 0;
        	 double maxPrice = 0;
        	 if (minSet.next()) {
        		 minPrice = minSet.getDouble("MINPRICE");
        	 }
        	 minSet.close();
        	 minStatement.close();
        	 ResultSet maxSet = maxStatement.executeQuery();
        	 if (maxSet.next()) {
        		 maxPrice = maxSet.getDouble("MAXPRICE");
        	 }
        	 maxSet.close();
        	 maxStatement.close();
             preparedStatement.setDouble(1, minPrice);
             preparedStatement.setDouble(2, maxPrice);
            System.out.println("Search Results for Price Range: " + minPrice + " to " + maxPrice);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    System.out.println("Item ID: " + resultSet.getInt("ITEMID"));
                    System.out.println("Item Name: " + resultSet.getString("ITEMNAME"));
                    System.out.println("Category: " + resultSet.getString("CATEGORY"));
                    System.out.println("Description: " + resultSet.getString("DESCRIPTION"));
                    System.out.println("Price: " + resultSet.getDouble("PRICE"));
                    System.out.println("Availability Status: " + resultSet.getString("AVAILABILITYSTATUS"));
                    System.out.println("--------------------------------------");
                }
            }

        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}