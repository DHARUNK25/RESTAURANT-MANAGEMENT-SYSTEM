package com.EmployeeManagement;
/**
 * The EmployeeAddition class contains the methods that 
 * register the employee and 
 * add the records to the database
 * @author Dharun K (Expleo)
 * @since 19 Feb 2024
*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import com.DriverPackage.DBConnection;
import com.ExceptionHandling.ExceptionHandle;
import com.PersonDetails.Account;
import com.PersonDetails.Employee;
public class EmployeeAddition {

    public static void register() {
        Scanner sc = new Scanner(System.in);
        // Get details with validation
        String firstName = ExceptionHandle.getValidFirstName(sc);
        String lastName = ExceptionHandle.getValidLastName(sc);
        System.out.println("Enter User Name: ");
        String userName = sc.nextLine();
        // validates the password
        String password = ExceptionHandle.getValidPassword(sc);
        System.out.println("Enter date Of Birth (YYYY-MM-DD): ");
        String dateOfBirthString = ExceptionHandle.getValidDate(sc);
        java.sql.Date dateOfBirth = java.sql.Date.valueOf(dateOfBirthString);
        String gender = ExceptionHandle.getValidGender(sc);
        // validates the phone number
        String phoneNumber = ExceptionHandle.getValidPhoneNumber(sc);
        // validates the email
        String email = ExceptionHandle.getValidEmail(sc);
        double salary = ExceptionHandle.getValidSalary(sc);
        sc.nextLine();
        System.out.println("Enter hire date (YYYY-MM-DD): ");
        String hireDateString = ExceptionHandle.getValidDate(sc);
        java.sql.Date hireDate = java.sql.Date.valueOf(hireDateString);
        final String role = "Employee";

        Employee employee = new Employee(firstName, lastName, userName, password, dateOfBirth, gender, phoneNumber,
                email, salary, hireDate, role);

        // Call the method to add the employee to the database
        addEmployeeToDatabase(employee);
    }

    public static void addEmployeeToDatabase(Employee employee) {
        final String accountIdQuery = "SELECT MAX(ACCOUNTID) AS MAXACCID FROM DATABASE.ACCOUNT";
        final String employeeIdQuery = "SELECT MAX(EMPLOYEEID) AS MAXEMPID FROM DATABASE.EMPLOYEE";
        final String adminIdQuery = "SELECT ADMINID FROM DATABASE.ADMIN";

        String insertQuery = "INSERT INTO DATABASE.EMPLOYEE ( EMPLOYEEID,ACCOUNTID,FIRSTNAME,LASTNAME,GENDER,DATEOFBIRTH,PHONENUMBER,EMAIL,SALARY,HIREDATE,	ADMINID) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection connection = DBConnection.doDBConnection();) {
        	Account account = new Account(employee.getUserName(), employee.getPassword(), employee.getRole());
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
            PreparedStatement employeeIdStatement = connection.prepareStatement(employeeIdQuery);
            ResultSet employeeIdResult = employeeIdStatement.executeQuery();
            int maxEmployeeId = 0;
            if (employeeIdResult.next())
                maxEmployeeId = employeeIdResult.getInt("MAXEMPID");
            employeeIdResult.close();
            employeeIdStatement.close();
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
            preparedStatement.setInt(1, maxEmployeeId + 1);
            preparedStatement.setInt(2, maxAccountId );
            preparedStatement.setString(3, employee.getFirstName());
            preparedStatement.setString(4, employee.getLastName());
            preparedStatement.setString(5, employee.getGender());
            preparedStatement.setDate(6, employee.getDateOfBirth());
            preparedStatement.setString(7, employee.getPhoneNumber());
            preparedStatement.setString(8, employee.getEmail());
            preparedStatement.setDouble(9, employee.getSalary());
            preparedStatement.setDate(10, employee.getHireDate());
            preparedStatement.setInt(11, adminId);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("New Employee added Successfully.");
            } else {
                System.out.println("Failed to add new employee.");
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
