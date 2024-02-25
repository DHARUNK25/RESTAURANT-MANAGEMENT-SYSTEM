package com.PersonDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.DriverPackage.DBConnection;

public class Account extends DBConnection {

	private String userName;
    private String password;
    private String role;

    public Account(String userName, String password,String role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void addAccountToDatabase() {
        final String accountIdQuery = "SELECT MAX(ACCOUNTID) AS MAXACCID FROM DATABASE.ACCOUNT";
        String insertQuery = "INSERT INTO DATABASE.ACCOUNT ( ACCOUNTID,USERNAME, PASSWORD, ROLE) VALUES (?,?,?,?)";
        try (Connection connection = doDBConnection();) {

        	// get max account id
            PreparedStatement accountIdStatement = connection.prepareStatement(accountIdQuery);
            ResultSet accountIdResult = accountIdStatement.executeQuery();
            int maxAccountId = 0;
            if (accountIdResult.next())
                maxAccountId = accountIdResult.getInt("MAXACCID");
            accountIdResult.close();
            accountIdStatement.close();

            // Set parameters in the prepared statement
        	PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
        	preparedStatement.setInt(1, maxAccountId+1);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, role);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("New Account added Successfully.");
            } else {
                System.out.println("Failed to add new account.");
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
