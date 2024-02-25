package com.ProfileManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.DriverPackage.DBConnection;
import com.ExceptionHandling.ExceptionHandle;

public class CustomerUpdation {
    static Scanner sc = new Scanner(System.in);

    public static void updateCustomer() {
        System.out.println("Enter the Id of customer : ");
        int customerId = ExceptionHandle.getInput(sc);
        sc.nextLine();
        System.out.println("First Name updation ");
        String newFirstName = ExceptionHandle.getValidFirstName(sc);
        System.out.println("Last Name updation ");
        String newLastName = ExceptionHandle.getValidLastName(sc);
        System.out.println("Contact number updation  ");
        String newPhoneNumber = ExceptionHandle.getValidPhoneNumber(sc);
        System.out.println("Email updation ");
        String newEmail = ExceptionHandle.getValidEmail(sc);
        System.out.println("City Name updation ");
        String newCity = ExceptionHandle.getValidCity(sc);

        updateCustomerToDatabase(customerId,newFirstName,newLastName, newPhoneNumber, newEmail, newCity);
    }

    public static void updateCustomerToDatabase(int customerId,String newFirstName,String newLastName, String newPhoneNumber, String newEmail,String newCity) {
        String checkQuery = "SELECT * FROM DATABASE.CUSTOMER WHERE CUSTOMERID = ?";
        String updateQuery = "UPDATE DATABASE.CUSTOMER SET FIRSTNAME=?,LASTNAME=?, PHONENUMBER = ?, EMAIL = ?, CITY = ? WHERE CUSTOMERID = ?";
        try (Connection connection = DBConnection.doDBConnection();
                PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {

            // Check if employee exists
            checkStatement.setInt(1, customerId);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Customer exists.");

                // Update the employee
                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                	updateStatement.setString(1, newFirstName);
                    updateStatement.setString(2, newLastName);
                    updateStatement.setString(3, newPhoneNumber);
                    updateStatement.setString(4, newEmail);
                    updateStatement.setString(5, newCity);
                    updateStatement.setInt(6, customerId);

                    // Execute the update
                    int rowsUpdated = updateStatement.executeUpdate();
                    String updateResult = rowsUpdated > 0 ? "Customer Details updated Successfully" : "Customer Details not updated";
                    System.out.println(updateResult);
                }

            } else {
                System.out.println("Customer Not found.");
            }

        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

