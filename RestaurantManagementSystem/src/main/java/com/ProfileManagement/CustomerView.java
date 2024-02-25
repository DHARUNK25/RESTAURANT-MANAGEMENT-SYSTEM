package com.ProfileManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.DriverPackage.DBConnection;

public class CustomerView {
    public static List<Object> viewCustomerData() {
        List<Object> resultList = new ArrayList<>();
        String viewQuery = "SELECT * FROM DATABASE.CUSTOMER";
        try (Connection connection = DBConnection.doDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(viewQuery)) {
            ResultSet viewResult = preparedStatement.executeQuery();

            while (viewResult.next()) {
                resultList.add(viewResult.getInt(1));
                resultList.add(viewResult.getInt(2));
                resultList.add(viewResult.getString(3));
                resultList.add(viewResult.getString(4));
                resultList.add(viewResult.getDate(5));
                resultList.add(viewResult.getString(6));
                resultList.add(viewResult.getString(7));
                resultList.add(viewResult.getString(8));
                resultList.add(viewResult.getString(9));
               
            }

            viewResult.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }

        // Print Employee details in a neat format
        System.out.println("Customer details:");
        for (int i = 0; i < resultList.size(); i += 9) {
            System.out.println("Customer ID: " + resultList.get(i));
            System.out.println("Account ID: " + resultList.get(i + 1));
            System.out.println("First Name: " + resultList.get(i + 2));
            System.out.println("Last Name: " + resultList.get(i + 3));
            System.out.println("Date of Birth: " + resultList.get(i + 4));
            System.out.println("Gender: " + resultList.get(i + 5));
            System.out.println("Phone Number: " + resultList.get(i + 6));
            System.out.println("Email: " + resultList.get(i + 7));
            System.out.println("City: " + resultList.get(i + 8));
            System.out.println("--------------------------------------");
        }

        return resultList;
    }
}
