package com.Reservation;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import com.DriverPackage.DBConnection;
import com.ExceptionHandling.ExceptionHandle;
public class Cancellation {

    public static void doCancel() {
        Scanner sc = new Scanner(System.in);
        String email = ExceptionHandle.getValidEmail(sc);
        try {
            final String QUERY = "SELECT * FROM DATABASE.RESERVATION WHERE RESERVATIONSTATUS = 'Reserved' AND CUSTOMERID = (SELECT CUSTOMERID FROM DATABASE.CUSTOMER WHERE EMAIL =  ?)";

            Connection con = DBConnection.doDBConnection();
            PreparedStatement stat = con.prepareStatement(QUERY);
            stat.setString(1, email);
            ResultSet result = stat.executeQuery();

            while (result.next()) {
                int reservationId = result.getInt("RESERVATIONID");
                String fullReservationDate = result.getString("RESERVATIONDATE");
                int customerId = result.getInt("CUSTOMERID");
                int tableId = result.getInt("TABLESID");
                String fullReservationTime = result.getString("RESERVATIONTIME");
                String specialRequest = result.getString("SPECIALREQUESTS");
                String reservationStatus = result.getString("RESERVATIONSTATUS");

                // show without time
                String reservationDate = fullReservationDate.substring(0, 10);
                String reservationTime = fullReservationTime.substring(0, 5);
                System.out.println("--------------------------------------");
                System.out.println("        Reservation Details           ");
                System.out.println("Reservation ID : " + reservationId);
                System.out.println("Customer ID : " + customerId);
                System.out.println("Table ID : " + tableId);
                System.out.println("Special Request : " + specialRequest);
                System.out.println("Reservation Date : " + reservationDate);
                System.out.println("Reservation Status: " + reservationStatus);
                System.out.println("--------------------------------------");
                
            }

            System.out.print("Enter Reservation ID to Cancel : ");
            int reservationId = ExceptionHandle.getInput(sc);
            System.out.print("Enter Table Number to Cancel : ");
            int tableId = ExceptionHandle.getInput(sc);
            try {
                final String updateQuery = "UPDATE DATABASE.RESERVATION SET RESERVATIONSTATUS = 'Cancelled' WHERE RESERVATIONID = ?";
                final String updateTableStatus = "UPDATE DATABASE.TABLES SET AVAILABILITYSTATUS	 = 'Available' WHERE TABLESID = ?";
                PreparedStatement updateStatement = con.prepareStatement(updateTableStatus);
                updateStatement.setInt(1, tableId);
                int tableUpdate = updateStatement.executeUpdate();
                if (tableUpdate > 0) {
                    System.out.println("Tables Updated Successfully");
                } else {
                    System.out.println("Tables not updated");
                }
                
                PreparedStatement cancelStat = con.prepareStatement(updateQuery);
                cancelStat.setInt(1, reservationId);
                int cancel_result = cancelStat.executeUpdate();
                if (cancel_result > 0) {
                    System.out.println("Reservation Cancelled Successfully");
                    System.out.println("Your amount will be refunded in 2 days!");
                } else {
                    System.out.println("Reservation not Cancelled");
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

