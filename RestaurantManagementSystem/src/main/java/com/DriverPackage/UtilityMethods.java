package com.DriverPackage;
/**
 * The UtilityMethods class contains all the necessary methods that are
 * needed by the Driver class
 * @author Dharun K (Expleo)
 * @since 20 Feb 2024
*/
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import com.CustomerManagement.CustomerRegistration;
import com.EmployeeManagement.*;
import com.ExceptionHandling.ExceptionHandle;
import com.FeedbackManagement.FeedbackAddition;
import com.MenuManagement.*;
import com.OrderManagement.*;
import com.PersonDetails.Login;
import com.ProfileManagement.*;
import com.Reservation.*;
import com.TableManagement.*;
public class UtilityMethods {

    public static void introLayout() {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                                                          ║");
        System.out.println("║        🍽   WELCOME TO DELICIOUS BITES   🍽              ║");
        System.out.println("║                                                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
    public static void userLayout() {
        System.out.println("┌───────────────────────────┐");
        System.out.println("│       Select User         │");
        System.out.println("│       1. Admin            │");
        System.out.println("│       2. Employee         │");
        System.out.println("│       3. Customer         │");
        System.out.println("│       4. Exit             │");
        System.out.println("└───────────────────────────┘");
    }

    static void adminMenu(Scanner sc, Connection con) throws SQLException {
    	System.out.println("┌───────────────────────────┐");
    	System.out.println("│      Please Login         │");
    	System.out.println("└───────────────────────────┘");

        if (Login.login()) {
            while (true) {
            	System.out.println("┌─────────────────────────────────────┐");
            	System.out.println("│      Welcome Admin                  │");
            	System.out.println("│  1. Employee Management             │");
            	System.out.println("│  2. Menu Management                 │");
            	System.out.println("│  3. Table Management                │");
            	System.out.println("│  4. Reservation                     │");
            	System.out.println("│  5. Go to Admin management          │");
            	System.out.println("│  6. Log Out                         │");
            	System.out.println("└─────────────────────────────────────┘");

                System.out.print("Enter your choice : ");
                int adminChoice = ExceptionHandle.getInput(sc);
                sc.nextLine();

                switch (adminChoice) {
                    case 1:
                        employeeManagementMenu(sc, con);
                        break;
                    case 2:
                        menuManagementMenu(sc, con);
                        break;
                    case 3:
                        tableManagementMenu(sc, con);
                        break;
                    case 4:
                        Reservation.doReservation();
                        break;
                    case 5:
                        return; // Go to previous option
                    case 6:
                        System.out.println("Logging out.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    public static void employeeMenu(Scanner sc, Connection con) {
    	System.out.println("┌───────────────────────────┐");
    	System.out.println("│      Please Login         │");
    	System.out.println("└───────────────────────────┘");

        if (Login.login()) {
            while (true) {
            	System.out.println("┌──────────────────────────────┐");
            	System.out.println("│    Welcome Employee          │");
            	System.out.println("│  1. Table Management         │");
            	System.out.println("│  2. Go to Previous Option    │");
            	System.out.println("│  3. Logout                   │");
            	System.out.println("└──────────────────────────────┘");

                System.out.print("Enter your choice : ");
                int employeeChoice = ExceptionHandle.getInput(sc);
                sc.nextLine();
                switch (employeeChoice) {
                    case 1:
                        tableManagementMenu(sc, con);
                        break;
                    case 2:
                        return; // Go to previous option
                    case 3:
                        System.out.println("Logging out.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    public static void customerMenu(Scanner sc, Connection con) throws SQLException {
    	System.out.println("┌───────────────────────────┐");
    	System.out.println("│      User Options         │");
    	System.out.println("│      1. Register          │");
    	System.out.println("│      2. Login             │");
    	System.out.println("└───────────────────────────┘");

        System.out.print("Enter your choice : ");
        int customerChoice = ExceptionHandle.getInput(sc);
        sc.nextLine();
        switch (customerChoice) {
            case 1:
                CustomerRegistration.register();
                break;
            case 2:
            	System.out.println("┌───────────────────────────┐");
            	System.out.println("│      Please Login         │");
            	System.out.println("└───────────────────────────┘");
                if (Login.login()) {
                    customerLoggedInMenu(sc, con);
                }
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    public static void customerLoggedInMenu(Scanner sc, Connection con) throws SQLException {
        while (true) {
        	System.out.println("┌───────────────────────────────┐");
        	System.out.println("│       Customer Options        │");
        	System.out.println("│  1. Profile Management        │");
        	System.out.println("│  2. Reservation               │");
        	System.out.println("│  3. Cancel Reservation        │");
        	System.out.println("│  4. Menu View                 │");
        	System.out.println("│  5. Order                     │");
        	System.out.println("│  6. Logout                    │");
        	System.out.println("└───────────────────────────────┘");

            System.out.print("Enter your choice : ");
            int customerChoice = ExceptionHandle.getInput(sc);
            sc.nextLine();

            switch (customerChoice) {
                case 1:
                    System.out.println("Update Customer Details : ");
                    CustomerUpdation.updateCustomer();
                    break;
                case 2:
                    Reservation.doReservation();
                    System.out.println("Your feedback shapes our future.");
                    System.out.println("Want to Share your feedback ?[yes | no] : ");
                    String choice = sc.nextLine();
                    if(choice.equalsIgnoreCase("yes")) {
                    	FeedbackAddition.addFeedback();
                    } 
                    break;
                case 3:
                    Cancellation.doCancel();
                    break;
                case 4:
                    MenuView.viewMenuData();
                    break;
                case 5:
                    System.out.println("Enter your email:");
                    String email = sc.nextLine();
                    int customerId = CustomerDetails.getCustomerId(con, email);
                    if (customerId != -1) {
                        System.out.println("Customer ID for " + email + ": " + customerId);
                        int tableId = CustomerDetails.getTableId(con, customerId);
                        if (tableId != -1)
                            PlaceOrder.searchMenu(sc, con, customerId, tableId);
                        else
                            Reservation.doReservation();
                    } else {
                        System.out.println("Customer ID not found for email: " + email);
                    }
                    break;
                case 6:
                    System.out.println("Logging out.");
                    Driver.main(null);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void employeeManagementMenu(Scanner sc, Connection con) {
        while (true) {
        	System.out.println("┌───────────────────────────────────┐");
        	System.out.println("│    Employee Management            │");
        	System.out.println("│  1. Register Employee             │");
        	System.out.println("│  2. Delete Employee               │");
        	System.out.println("│  3. Update Employee               │");
        	System.out.println("│  4. View Employee Data            │");
        	System.out.println("│  5. Back to Admin Menu            │");
        	System.out.println("└───────────────────────────────────┘");

            System.out.print("Enter your choice : ");
            int employeeManagementChoice = ExceptionHandle.getInput(sc);
            sc.nextLine();
            switch (employeeManagementChoice) {
                case 1:
                    EmployeeAddition.register();
                    break;
                case 2:
                    EmployeeDeletion.delete();
                    break;
                case 3:
                    EmployeeUpdation.updateEmployee();
                    break;
                case 4:
                    EmployeeView.viewEmployeeData();
                    break;
                case 5:
                	System.out.println("Going back to Admin Menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void menuManagementMenu(Scanner sc, Connection con) {
        while (true) {
        	System.out.println("┌──────────────────────────────────┐");
        	System.out.println("│      Menu Management             │");
        	System.out.println("│  1. Add Menu                     │");
        	System.out.println("│  2. Delete Menu                  │");
        	System.out.println("│  3. Update Menu                  │");
        	System.out.println("│  4. View Menu Data               │");
        	System.out.println("│  5. Go to Previous Option        │");
        	System.out.println("│  6. Back to Admin Menu           │");
        	System.out.println("└──────────────────────────────────┘");

            System.out.print("Enter your choice : ");
            int menuManagementChoice = ExceptionHandle.getInput(sc);
            sc.nextLine();
            switch (menuManagementChoice) {
                case 1:
                    MenuAddition.addMenu();
                    break;
                case 2:
                    MenuDeletion.delete();
                    break;
                case 3:
                    MenuUpdation.updateMenu();
                    break;
                case 4:
                    MenuView.viewMenuData();
                    break;
                case 5:
                    return; // Go to previous option
                case 6:
                    System.out.println("Going back to Admin Menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void tableManagementMenu(Scanner sc, Connection con) {
        while (true) {
        	System.out.println("┌───────────────────────────────────┐");
        	System.out.println("│     Table Management              │");
        	System.out.println("│  1. Add Table                     │");
        	System.out.println("│  2. Delete Table                  │");
        	System.out.println("│  3. Update Table                  │");
        	System.out.println("│  4. View Table Data               │");
        	System.out.println("│  5. Go to Previous Menu           │");
        	System.out.println("└───────────────────────────────────┘");

            System.out.print("Enter your choice : ");
            int tableManagementChoice = ExceptionHandle.getInput(sc);
            sc.nextLine();

            switch (tableManagementChoice) {
                case 1:
                    TableAddition.addTable();
                    break;
                case 2:
                    TableDeletion.delete();
                    break;
                case 3:
                    TableUpdation.updateTable();
                    break;
                case 4:
                    TableView.viewTableData();
                    break;
                case 5:
                	System.out.println("Going back to Employee menu.");
                    return; // Go to previous option
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
