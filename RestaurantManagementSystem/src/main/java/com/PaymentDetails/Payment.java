package com.PaymentDetails;

import java.util.Scanner;

import com.ExceptionHandling.ExceptionHandle;
public class Payment {
	static Scanner scanner = new Scanner(System.in);
    public static boolean makePayment(int paymentType, double amount) {
        switch (paymentType) {
            case 1:
                return cashPayment(amount);
            case 2:
                return cardPayment(amount);
            default:
                System.out.println("Invalid payment type");
                return false;
        }
    }
    private static boolean cashPayment(double amount) {
        System.out.println("     Cash Payment   ");
        System.out.print("Enter the amount : ");
        double amountReceived = ExceptionHandle.getAmount(scanner);
        if (amountReceived >= amount) {
            double change = amountReceived - amount;
            System.out.println("Payment completed. Change: " + change);
            return true;
        } else {
            System.out.println("Insufficient amount.");
            return false;
        }  
    }
    private static boolean cardPayment(double amount) {
        System.out.println("    Card Payment    ");
        String cardNumber = ExceptionHandle.getValidCardNumber(scanner); 
        @SuppressWarnings("unused")
		String expirationDate = ExceptionHandle.getValidExpiryDate(scanner);
        int cvv = ExceptionHandle.getValidCvv(scanner);
        boolean isCardValid = validateCard(cardNumber, cvv);
        if (isCardValid) {
            System.out.println("Card processing successful. Payment completed.");
            return true;
        } else {
            System.out.println("Card processing failed. Payment failed.");
            return false;
        }
    }
    private static boolean validateCard(String cardNumber, int cvv) {
        return !cardNumber.isEmpty()&& cvv >= 0;
    }
}
