package com.FeedbackManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.DriverPackage.DBConnection;
public class FeedbackView {   
    public static List<Object> viewFeedbackData() {
        List<Object> resultList = new ArrayList<>();
        String viewQuery = "SELECT * FROM DATABASE.FEEDBACK";
        try (Connection connection = DBConnection.doDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(viewQuery)) {
            ResultSet viewResult = preparedStatement.executeQuery();
            while (viewResult.next()) {
                resultList.add(viewResult.getInt("FEEDBACKID"));
                resultList.add(viewResult.getString("REVIEW"));
                resultList.add(viewResult.getInt("RATING"));
                resultList.add(viewResult.getInt("CUSTOMERID"));
            }
            viewResult.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        // Print Feedback details in a neat format
        System.out.println("Feedback details:");
        for (int i = 0; i < resultList.size(); i += 4) {
            System.out.println("Feedback ID: " + resultList.get(i));
            System.out.println("Review: " + resultList.get(i + 1));
            System.out.println("Rating: " + resultList.get(i + 2));
            System.out.println("Customer ID: " + resultList.get(i + 3));
            System.out.println("--------------------------------------");
        }
        return resultList;
    }
}
