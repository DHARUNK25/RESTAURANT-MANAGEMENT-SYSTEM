package com.CustomerManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.DriverPackage.DBConnection;
import com.ExceptionHandling.ExceptionHandle;
import com.PersonDetails.Account;
public class CustomerRegistration {

    public static void register() {
      Scanner sc = new Scanner(System.in);
      try {
        // Get details with validation
        String firstName = ExceptionHandle.getValidFirstName(sc);
        String lastName = ExceptionHandle.getValidLastName(sc);
        System.out.println("Enter your User Name: ");
        String userName = sc.nextLine();
        String password = ExceptionHandle.getValidPassword(sc);
        System.out.println("Enter your date Of Birth (YYYY-MM-DD): ");
        String dateOfBirthString = ExceptionHandle.getValidDate(sc);
        java.sql.Date dateOfBirth = java.sql.Date.valueOf(dateOfBirthString);
        String gender = ExceptionHandle.getValidGender(sc);
        String phoneNumber = ExceptionHandle.getValidPhoneNumber(sc);
        String email = ExceptionHandle.getValidEmail(sc);
        String city = ExceptionHandle.getValidCity(sc);
        String role = "Customer";
        Customer customer = new Customer(firstName, lastName, userName, password, dateOfBirth, gender, phoneNumber, email,city, role);
        // Call the method to add the employee to the database
        addCustomerToDatabase(customer);
      } catch (InputMismatchException e) {
          System.out.println("Invalid input. Please enter the correct input.");
      }
    }
    
    public static void addCustomerToDatabase(Customer customer) {
        final String accountIdQuery = "SELECT MAX(ACCOUNTID) AS MAXACCID FROM DATABASE.ACCOUNT";
        final String customerIdQuery = "SELECT MAX(CUSTOMERID) AS MAXCUSTID FROM DATABASE.CUSTOMER";
        final String adminIdQuery = "SELECT ADMINID FROM DATABASE.ADMIN";

        final String insertQuery = "INSERT INTO DATABASE.CUSTOMER (CUSTOMERID,ACCOUNTID,FIRSTNAME,LASTNAME,GENDER,DATEOFBIRTH,PHONENUMBER,EMAIL,CITY) VALUES (?,?,?,?,?,?,?,?,?)";

        try (Connection connection = DBConnection.doDBConnection();) {
        	Account account = new Account(customer.getUserName(), customer.getPassword(), customer.getRole());
            account.addAccountToDatabase();
            // get max account id
            PreparedStatement accountIdStatement = connection.prepareStatement(accountIdQuery);
            ResultSet accountIdResult = accountIdStatement.executeQuery();
            int maxAccountId = 0;
            if (accountIdResult.next())
                maxAccountId = accountIdResult.getInt("MAXACCID");
            accountIdResult.close();
            accountIdStatement.close();
            // Get Max Employee Id
            PreparedStatement customerIdStatement = connection.prepareStatement(customerIdQuery);
            ResultSet customerIdResult = customerIdStatement.executeQuery();
            int maxCustomerId = 0;
            if (customerIdResult.next())
            	maxCustomerId = customerIdResult.getInt("MAXCUSTID");
            customerIdResult.close();
            customerIdStatement.close();
            // Get Admin Id
            PreparedStatement adminIdResultStatement = connection.prepareStatement(adminIdQuery);
            ResultSet adminIdResult = adminIdResultStatement.executeQuery();
            int adminId = 0;
            if (adminIdResult.next())
                adminId = adminIdResult.getInt("ADMINID");
            adminIdResult.close();
            adminIdResultStatement.close();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            // Set parameters in the prepared statement
            preparedStatement.setInt(1, maxCustomerId + 1);
            preparedStatement.setInt(2, maxAccountId );
            preparedStatement.setString(3, customer.getFirstName());
            preparedStatement.setString(4, customer.getLastName());
            preparedStatement.setString(5, customer.getGender());
            preparedStatement.setDate(6, customer.getDateOfBirth());
            preparedStatement.setString(7, customer.getPhoneNumber());
            preparedStatement.setString(8, customer.getEmail());
            preparedStatement.setString(9, customer.getCity());
            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Registered Successfully.");
            } else {
                System.out.println("Registeration Failed.");
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
