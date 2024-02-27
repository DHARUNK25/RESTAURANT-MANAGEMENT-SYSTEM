package com.EmployeeManagement;
/**
 * The EmployeeUpdation class contains the methods that 
 * updates the employee details to the  database
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

public class EmployeeUpdation {
    static Scanner sc = new Scanner(System.in);

    public static void updateEmployee() {
        System.out.println("Enter the Id of employee : ");
        int employeeId = ExceptionHandle.getValidEmpItemID(sc);
        sc.nextLine();
        System.out.println("Contact number updation  ");
        String newPhoneNumber = ExceptionHandle.getValidPhoneNumber(sc);
        System.out.println("Email updation ");
        String newEmail = ExceptionHandle.getValidEmail(sc);
        System.out.println("Salary updation ");
        double newSalary = ExceptionHandle.getValidSalary(sc);
        // invoke updateEmployeeToDatabase method
        updateEmployeeToDatabase(employeeId, newPhoneNumber, newEmail, newSalary);
    }

    public static void updateEmployeeToDatabase(int employeeId, String newPhoneNumber, String newEmail,double newSalary) {
        String checkQuery = "SELECT * FROM DATABASE.EMPLOYEE WHERE EMPLOYEEID = ?";
        String updateQuery = "UPDATE DATABASE.EMPLOYEE SET PHONENUMBER = ?, EMAIL = ?, SALARY = ? WHERE EMPLOYEEID = ?";
        try (Connection connection = DBConnection.doDBConnection();
                PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {

            // Check if employee exists
            checkStatement.setInt(1, employeeId);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Employee exists.");

                // Update the employee
                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setString(1, newPhoneNumber);
                    updateStatement.setString(2, newEmail);
                    updateStatement.setDouble(3, newSalary);
                    updateStatement.setInt(4, employeeId);

                    // Execute the update
                    int rowsUpdated = updateStatement.executeUpdate();
                    String updateResult = rowsUpdated > 0 ? "Rows updated" : "No rows updated";
                    System.out.println(updateResult);
                }

            } else {
                System.out.println("Employee Not found.");
            }

        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
