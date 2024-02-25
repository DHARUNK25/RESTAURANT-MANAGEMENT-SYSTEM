package com.PersonDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import com.DriverPackage.DBConnection;

public class Login extends DBConnection {
	
    public static boolean login() {
    	
    	Scanner sc = new Scanner(System.in);
        System.out.println("Enter User Name:");
        String loginuserName = sc.nextLine();

        System.out.println("Enter Password:");   
        String loginpassword = sc.nextLine();

        // SQL query to check user credentials
        String selectQuery = "SELECT * FROM DATABASE.ACCOUNT WHERE USERNAME = ? AND PASSWORD = ?";

        try (Connection connection = doDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setString(1, loginuserName);
            preparedStatement.setString(2, loginpassword);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Login Successful!");
                return true;
            } else {
                System.out.println("Invalid credentials. Login failed.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}

