package com.EmployeeManagement;
/**
 * The EmployeeDeletion class contains the methods that 
 * deletes the employee records from database
 * @author Dharun K (Expleo)
 * @since 19 Feb 2024
*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;


import com.DriverPackage.DBConnection;
import com.ExceptionHandling.ExceptionHandle;

public class EmployeeDeletion {

	 public static void delete() {
	    	Scanner sc = new Scanner(System.in);
	    	EmployeeView.viewEmployeeData();
	        System.out.println("Enter the Id of the Employee you want to delete : ");
            int empId = ExceptionHandle.getInput(sc);
            
            deleteEmployeeFromDatabase(empId);
	    }
	    
	    public static void deleteEmployeeFromDatabase(int empId) {
	        String deleteQuery = "DELETE FROM DATABASE.EMPLOYEE WHERE EMPLOYEEID =?";
	        try (Connection connection = DBConnection.doDBConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
	        	 preparedStatement.setInt(1, empId);
	            // Execute the update
	            int rowsAffected = preparedStatement.executeUpdate();

	            if (rowsAffected > 0) {
	                System.out.println("Employee record deleted Successfully.");
	            } else {
	                System.out.println("Failed to delete the employee record.");
	            }
	        } catch (SQLException e) {
	            System.err.println("Database Error: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }

}
