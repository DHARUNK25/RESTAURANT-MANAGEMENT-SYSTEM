package com.DriverPackage;
/**
 * The Driver class implements a application that 
 * manages a restaurant called "Delecious Bites"
 * @author Dharun K (Expleo)
 * @since 24 Feb 2024
*/
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.ExceptionHandling.ExceptionHandle;

public class Driver {

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Connection con = DBConnection.doDBConnection();

        while (true) {
            UtilityMethods.introLayout();
            UtilityMethods.userLayout();
            System.out.print("Enter your choice : ");
            int userChoice = ExceptionHandle.getInput(sc);
            sc.nextLine();
            switch (userChoice) {
                case 1:
                    UtilityMethods.adminMenu(sc, con);
                    break;
                case 2:
                    UtilityMethods.employeeMenu(sc, con);
                    break;
                case 3:
                    UtilityMethods.customerMenu(sc, con);
                    break;
                case 4:
                    System.out.println("Exiting program. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
