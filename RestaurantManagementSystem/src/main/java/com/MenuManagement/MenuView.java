package com.MenuManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.DriverPackage.DBConnection;

public class MenuView {
    public static List<Object> viewMenuData() {
        List<Object> resultList = new ArrayList<>();
        String viewQuery = "SELECT * FROM DATABASE.MENU ORDER BY ITEMID ";
        try (Connection connection = DBConnection.doDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(viewQuery)) {
            ResultSet viewResult = preparedStatement.executeQuery();
            while (viewResult.next()) {
                resultList.add(viewResult.getInt("ITEMID"));
                resultList.add(viewResult.getString("ITEMNAME"));
                resultList.add(viewResult.getString("CATEGORY"));
                resultList.add(viewResult.getString("DESCRIPTION"));
                resultList.add(viewResult.getDouble("PRICE"));
                resultList.add(viewResult.getString("AVAILABILITYSTATUS"));
            }

            viewResult.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }

        // Print Menu details in a neat format
        System.out.println("Menu details:");
        for (int i = 0; i < resultList.size(); i += 6) {
            System.out.println("Item ID: " + resultList.get(i));
            System.out.println("Item Name: " + resultList.get(i + 1));
            System.out.println("Category: " + resultList.get(i + 2));
            System.out.println("Description: " + resultList.get(i + 3));
            System.out.println("Price: " + resultList.get(i + 4));
            System.out.println("Availability Status: " + resultList.get(i + 5));
            System.out.println("--------------------------------------");
        }

        return resultList;
    }
}

