package com.PersonDetails;

import java.util.Scanner;

public class Admin {
	public static void showAdminFunctions() {  		   		
		while(true) {
			Scanner sc=new Scanner(System.in);
			System.out.println("-----Welcome Admin------");
    		System.out.println("1. Employee Management");
    		System.out.println("2. Menu Management");
    		System.out.println("3. Resrvation Management");
    		System.out.println("4. previous");
    		System.out.println("5. exit");
    		System.out.print("Enter your choice: ");
			int adminChoice = sc.nextInt();			
			switch(adminChoice) {
			case 1:
				System.out.println("1. Add Employee");
	    		System.out.println("2. Delete Employee");
	    		System.out.println("3. View Employee");
	    		System.out.println("4. Update Employee");
				break;
			case 2:
			
				
			case 3:
				
			case 4:
				
			case 5:
				System.out.println("Thank You ! Come Again :)");
				System.exit(0);
				break;
    			}
    		}
}
