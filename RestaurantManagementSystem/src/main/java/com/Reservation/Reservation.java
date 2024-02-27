package com.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;
import com.DriverPackage.DBConnection;
import com.DriverPackage.UtilityMethods;
import com.ExceptionHandling.ExceptionHandle;
import com.OrderManagement.OrderTables;
import com.PaymentDetails.Bill;
import com.PaymentDetails.Payment;
public class Reservation {
    static Scanner sc = new Scanner(System.in);
    public static boolean doReservation() throws SQLException {
        Connection con = DBConnection.doDBConnection();
        try {
        	showAvailableTable(con);
        	int tableID = ExceptionHandle.getValidTableId(sc);
        	sc.nextLine();
        	System.out.println("Enter the date to make a reservation (YYYY-MM-DD): ");
        	String reservDate = ExceptionHandle.getValidDate(sc);
        	java.sql.Date reservationDate = java.sql.Date.valueOf(reservDate);
        	System.out.println("Enter time to make a reservation (HH24:MI): ");
        	String reservationTime = ExceptionHandle.getValidTime(sc);
        	System.out.println("Enter any special request you want: ");
        	String specialRequest = sc.nextLine();
        	System.out.println("Enter your Password : ");
        	String password = sc.nextLine();
        	if (isAvailable(con, tableID, reservationDate, reservationTime)) {
        	  String status = "Reserved";
        	  String insert = "INSERT INTO DATABASE.RESERVATION (RESERVATIONID,CUSTOMERID, TABLESID, SPECIALREQUESTS, RESERVATIONDATE, RESERVATIONTIME, RESERVATIONSTATUS, ADMINID) VALUES (?, ?, ?, ?, ?, TO_TIMESTAMP(?, 'HH24:MI'), ?, ?)";
        	  String priceQuery = "SELECT PRICE FROM DATABASE.TABLES WHERE TABLESID =? ";
        	  String adminIdQuery = "SELECT ADMINID FROM DATABASE.ADMIN";
              String resIdQuery = "SELECT MAX(RESERVATIONID) AS MAXRESVID FROM DATABASE.RESERVATION";
              String customerIdQuery = "SELECT CUSTOMERID FROM DATABASE.CUSTOMER WHERE ACCOUNTID = ( SELECT ACCOUNTID FROM DATABASE.ACCOUNT WHERE PASSWORD = ?)";
              String updateTableStatusQuery = "UPDATE DATABASE.TABLES SET AVAILABILITYSTATUS = 'Booked' WHERE TABLESID = ?";
              String insertOrderTabes = "INSERT INTO DATABASE.ORDERTABLES (ORDERID,CUSTOMERID,TABLESID) VALUES (?,?, ?)";
              String OrderItemIdQuery = "SELECT MAX(ORDERID) AS MAXORDERID FROM DATABASE.ORDERTABLES";
              try (PreparedStatement preparedStatement = con.prepareStatement(insert);
                 PreparedStatement customerIDStatement = con.prepareStatement(customerIdQuery);
                 PreparedStatement adminIdResultStatement = con.prepareStatement(adminIdQuery);
                 PreparedStatement resvIdStatement = con.prepareStatement(resIdQuery);
                 PreparedStatement updateTableStatusStatement = con.prepareStatement(updateTableStatusQuery);
            	 PreparedStatement insertOrderTableStatement = con.prepareStatement(insertOrderTabes);
                 PreparedStatement OrderIdStatement = con.prepareStatement(OrderItemIdQuery);
                 PreparedStatement priceStatement = con.prepareStatement(priceQuery)) {
                customerIDStatement.setString(1, password);
                ResultSet custIdResult = customerIDStatement.executeQuery();
                int customerId = 0;
                if (custIdResult.next()) {
                    customerId = custIdResult.getInt("CUSTOMERID");
                }
                custIdResult.close();
                ResultSet adminIdResult = adminIdResultStatement.executeQuery();
                int adminId = 0;
                if (adminIdResult.next())
                    adminId = adminIdResult.getInt("ADMINID");
                adminIdResult.close();
                ResultSet reservationIdResult = resvIdStatement.executeQuery();
                int maxReservationId = 0;
                if (reservationIdResult.next())
                    maxReservationId = 1 + reservationIdResult.getInt("MAXRESVID");
                reservationIdResult.close();
                priceStatement.setInt(1, tableID);
                ResultSet priceResult = priceStatement.executeQuery();
                double tableprice = 0.0;
                if (priceResult.next()) {
                    tableprice = priceResult.getDouble("PRICE");
                }
                ResultSet OrderIdResult = OrderIdStatement.executeQuery();
	            int maxOrderId = 0;
	            if (OrderIdResult.next())
	            	maxOrderId = OrderIdResult.getInt("MAXORDERID");
	            OrderIdResult.close();
                OrderTables orderTables = new OrderTables(customerId,tableID);
                insertOrderTableStatement.setInt(1, maxOrderId + 1);
                insertOrderTableStatement.setInt(2, orderTables.getCustomerID());
                insertOrderTableStatement.setInt(3, orderTables.getTableID());
                insertOrderTableStatement.executeUpdate();
	            // Bill Generation
	            Bill.generateBill(tableprice);
	            // Payment
	            UtilityMethods.paymentLayout();
                int payType = ExceptionHandle.getValidChoice(sc);          
                boolean doPay = Payment.makePayment(payType, tableprice);      
                if (doPay) {
                    preparedStatement.setInt(1, maxReservationId);
                    preparedStatement.setInt(2, customerId);
                    preparedStatement.setInt(3, tableID);
                    preparedStatement.setString(4, specialRequest);
                    preparedStatement.setDate(5, reservationDate);
                    preparedStatement.setString(6, reservationTime);
                    preparedStatement.setString(7, status);
                    preparedStatement.setInt(8, adminId);
                    // Execute the update
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        // Update the TABLES table to mark the table as 'Booked' after a successful reservation
                        updateTableStatusStatement.setInt(1, tableID);
                        updateTableStatusStatement.executeUpdate();
                        System.out.println("Reservation Successful.");
                    } else {
                        System.out.println("Failed to make the reservation.");
                    }
                } else {
                    System.out.println("Payment failed. Reservation not made");
                }
            } catch (SQLException e) {
                System.err.println("Database Error: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            // If not available, display a message
            System.out.println("The selected table is unavailable for the specified date and time. Please choose another table.");
        }
        
       } catch (Exception e) {
            System.out.println("Invalid input. Please enter the correct data type.");
           }
      return true;
    }
    private static void showAvailableTable(Connection connection) {
        // SQL query to select available tables
        String selectQuery = "SELECT TABLESID,TABLETYPE,PRICE FROM DATABASE.TABLES WHERE AVAILABILITYSTATUS = 'Available'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
            System.out.println("Available Tables:");
            System.out.println("---------------------------------------------");
            System.out.printf("%-15s %-15s %-15s%n", "Table Number", "Price", "Table Type");
            System.out.println("---------------------------------------------");
            while (resultSet.next()) {
                int tableID = resultSet.getInt("TABLESID");
                String type = resultSet.getString("TABLETYPE");
                int tablePrice = resultSet.getInt("PRICE");
                System.out.printf("%-15s Rs.%-12s %-15s%n", tableID, tablePrice, type);
            }
            System.out.println("---------------------------------------------");

        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static boolean isAvailable(Connection connection, int tableID, Date reservationDate, String reservationTime) {
        // SQL query to check if the selected table is available for the specified date and time
        String checkAvailabilityQuery = "SELECT 1 FROM DATABASE.TABLES t WHERE t.TABLESID = ? AND t.AVAILABILITYSTATUS = 'Available' AND NOT EXISTS (SELECT 1 FROM DATABASE.RESERVATION r WHERE r.TABLESID = t.TABLESID   AND r.RESERVATIONDATE = TO_DATE(?, 'YYYY-MM-DD') AND r.RESERVATIONTIME = TO_TIMESTAMP(?, 'HH24:MI'))";
        try (PreparedStatement preparedStatement = connection.prepareStatement(checkAvailabilityQuery)) {
            preparedStatement.setInt(1, tableID);
            preparedStatement.setDate(2, (java.sql.Date) reservationDate);
            preparedStatement.setString(3, reservationTime);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if the table is available, false otherwise
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


}
