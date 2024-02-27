package com.OrderManagement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.DriverPackage.DBConnection;
import com.DriverPackage.UtilityMethods;
import com.ExceptionHandling.ExceptionHandle;
import com.PaymentDetails.Bill;
import com.PaymentDetails.Payment;
import com.Search.MenuSearch;
public class PlaceOrder {
	   // static List<MenuView> menuList = new ArrayList<>();
        public static void searchMenu(Scanner sc, Connection con, int customerId, int tableId) throws SQLException {
        MenuSearch menuSearch = new MenuSearch();
        while (true) {
        	UtilityMethods.searchLayout();
            int menuSearchChoice = ExceptionHandle.getValidChoice(sc);
            sc.nextLine();
            switch (menuSearchChoice) {
                case 1:
                    menuSearch.searchByCategory(con);
                    placeOrder(sc, con, customerId, tableId );
                    break;
                case 2:
                    menuSearch.searchByPrice(con);
                    placeOrder(sc, con, customerId, tableId);
                    break;
                case 3:
                	UtilityMethods.customerLoggedInMenu(sc,con);
                    System.out.println("Going back to Customer Menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void placeOrder(Scanner sc, Connection con, int customerId,int tableId) {
    	//MenuView.viewMenuData();
        List<Object> orderDetails = new ArrayList<>();
        while (true) {
            int orderItemID = ExceptionHandle.getValidItemID(sc);
            if (orderItemID == 0) {
                break;
            }
            // Verify if the selected item is in the menu
            boolean isValidItem = checkValidItemId(con,orderItemID);
            if (isValidItem) {
                int quantity = ExceptionHandle.getValidQuantity(sc,orderItemID);
                sc.nextLine();
                System.out.println("Enter special request for item " + orderItemID +
                        " (or enter 'null' for no special request):");
                String specialRequest = sc.nextLine();
                OrderItem orderItem = new OrderItem(orderItemID,customerId, quantity, specialRequest);
                addOrderItemToDatabase(orderItem,con);
                orderDetails.add(orderItem);
                calculateItemAmount(orderItemID,quantity,sc);
            } else {
                System.out.println("Invalid item ID. Please choose a valid item from the menu.");
            }
        }
        if (orderDetails.isEmpty()) {        	        	
            System.out.println("No item selected!");
        } else {
            System.out.println("Order placed.");
        }
    }
    public static boolean checkValidItemId(Connection con,int orderItemId) {
    	String searchQuery = "SELECT * FROM DATABASE.MENU WHERE ITEMID = ?";
    	try (PreparedStatement preparedStatement = con.prepareStatement(searchQuery)) {
    		preparedStatement.setInt(1, orderItemId);
    		ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            	return true;
    	} catch (SQLException e) {
    		System.err.println("Database Error: " + e.getMessage());
    		e.printStackTrace();
    	}
    	return false;
    }
    public static void addOrderItemToDatabase(OrderItem orderItem,Connection con) {
        final String itemIdQuery = "SELECT MAX(ORDERID) AS MAXORDERID FROM DATABASE.ORDERTABLES";
    	final String insertOrderItem = "INSERT INTO DATABASE.ORDERITEM (ORDERID,ITEMID, QUANTITY, SPECIALREQUEST, CUSTOMERID) VALUES (?,?, ?, ?, ?)";
    	        try (PreparedStatement preparedStatement = con.prepareStatement(insertOrderItem)) {
    	        	PreparedStatement itemIdStatement = con.prepareStatement(itemIdQuery);
    	            ResultSet itemIdResult = itemIdStatement.executeQuery();
    	            int maxOrderId = 0;
    	            if (itemIdResult.next())
    	            	maxOrderId = itemIdResult.getInt("MAXORDERID");
    	            itemIdResult.close();
    	            itemIdStatement.close();
    	            preparedStatement.setInt(1,maxOrderId );
    	            preparedStatement.setInt(2, orderItem.getOrderItemID());
    	            preparedStatement.setInt(3, orderItem.getQuantity());
    	            preparedStatement.setString(4, orderItem.getSpecialRequest());
    	            preparedStatement.setInt(5, orderItem.getCustomerId());
    	            int affectedRows = preparedStatement.executeUpdate();
    	            if (affectedRows > 0) {
    	            	
    	                System.out.println("order items inserted Successfully.");
    	            } else {
    	                System.out.println("Failed to insert order items in order item table.");
    	            }
    	        }
    	        catch (SQLException e) {
    	        	e.printStackTrace();
    	        	System.err.println("Error " + e.getMessage());
    	        }
    }
    public static void calculateItemAmount(int orderItemId, int quantity,Scanner sc) {
        String itemPriceQuery = "SELECT PRICE FROM DATABASE.MENU WHERE ITEMID = ?";
        double totalItemPrice = 0.0;
        try (Connection connection = DBConnection.doDBConnection()) {
            PreparedStatement itempriceStatement = connection.prepareStatement(itemPriceQuery);
            itempriceStatement.setInt(1, orderItemId);
            ResultSet itempriceResult = itempriceStatement.executeQuery();     
            if (itempriceResult.next()) {
                totalItemPrice = quantity * itempriceResult.getDouble("PRICE");
                Bill.generateBill(totalItemPrice);
             // Payment
                UtilityMethods.paymentLayout();
                int payType = ExceptionHandle.getValidChoice(sc);  
                Payment.makePayment(payType, totalItemPrice);
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
}
