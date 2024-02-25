package com.TableManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.DriverPackage.DBConnection;
public class TableView {
    public static List<Object> viewTableData() {
        List<Object> resultList = new ArrayList<>();
        String viewQuery = "SELECT * FROM DATABASE.TABLES";
        try (Connection connection = DBConnection.doDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(viewQuery)) {
            ResultSet viewResult = preparedStatement.executeQuery();
            while (viewResult.next()) {
                resultList.add(viewResult.getInt("TABLESID"));
                resultList.add(viewResult.getInt("RESTAURANTID"));
                resultList.add(viewResult.getString("AVAILABILITYSTATUS"));
                resultList.add(viewResult.getInt("EMPLOYEEID"));
                resultList.add(viewResult.getString("TABLETYPE"));
                resultList.add(viewResult.getDouble("PRICE"));
            }
            viewResult.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        // Print Table details in a neat format
        System.out.println("Table details:");
        for (int i = 0; i < resultList.size(); i += 6) {
            System.out.println("Table ID: " + resultList.get(i));
            System.out.println("Restaurant ID: " + resultList.get(i + 1));
            System.out.println("Availability Status: " + resultList.get(i + 2));
            System.out.println("Employee ID: " + resultList.get(i + 3));
            System.out.println("Table Type: " + resultList.get(i + 4));
            System.out.println("Price: " + resultList.get(i + 5));
            System.out.println("--------------------------------------");
        }
        return resultList;
    }
}

