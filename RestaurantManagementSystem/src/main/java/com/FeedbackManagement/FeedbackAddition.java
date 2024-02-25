package com.FeedbackManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.DriverPackage.DBConnection;
import com.ExceptionHandling.ExceptionHandle;

public class FeedbackAddition {

    public static void addFeedback() {
        Scanner sc = new Scanner(System.in);
        // Get details with validation
        System.out.println("Enter Review : ");
        String review = sc.nextLine();
        System.out.println("Enter Rating (1-5) : ");
        int rating = ExceptionHandle.getInput(sc);
        sc.nextLine();
        System.out.println("Enter Customer ID : ");
        int customerId = ExceptionHandle.getInput(sc);
        sc.nextLine();
        Feedback feedback = new Feedback(review, rating, customerId);
        // Call the method to add the feedback to the database
        addFeedbackToDatabase(feedback);
    }

    public static void addFeedbackToDatabase(Feedback feedback) {
        final String feedbackIdQuery = "SELECT MAX(FEEDBACKID) AS MAXFEEDBACKID FROM DATABASE.FEEDBACK";

        String insertQuery = "INSERT INTO DATABASE.FEEDBACK(FEEDBACKID, REVIEW, RATING, CUSTOMERID) VALUES (?,?,?,?)";

        try (Connection connection = DBConnection.doDBConnection();) {
            // get max feedback id
            PreparedStatement feedbackIdStatement = connection.prepareStatement(feedbackIdQuery);
            ResultSet feedbackIdResult = feedbackIdStatement.executeQuery();
            int maxFeedbackId = 0;
            if (feedbackIdResult.next())
                maxFeedbackId = feedbackIdResult.getInt("MAXFEEDBACKID");
            feedbackIdResult.close();
            feedbackIdStatement.close();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            // Set parameters in the prepared statement
            preparedStatement.setInt(1, maxFeedbackId + 1);
            preparedStatement.setString(2, feedback.getReview());
            preparedStatement.setInt(3, feedback.getRating());
            preparedStatement.setInt(4, feedback.getCustomerId());
            // Execute the update
            int feedbackAffected = preparedStatement.executeUpdate();

            if (feedbackAffected > 0) {
                System.out.println("Feedback added Successfully.");
            } else {
                System.out.println("Failed to add feedback.");
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

