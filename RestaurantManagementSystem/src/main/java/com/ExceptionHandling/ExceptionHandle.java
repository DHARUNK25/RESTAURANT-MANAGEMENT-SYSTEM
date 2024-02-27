package com.ExceptionHandling;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExceptionHandle {
	public static String getValidFirstName(Scanner sc) {
        while (true) {
        	System.out.println("Enter First Name: ");
            String firstName = sc.nextLine();
            if (isValidFistName(firstName)) {
                return firstName;
            } else {
                System.out.println("Invalid first name. Please enter a valid first name.");
            }
        }
    }
    
	public static String getValidLastName(Scanner sc) {
        while (true) {
        	System.out.println("Enter Last Name: ");
            String lastName = sc.nextLine();
            if (isValidLastName(lastName)) {
                return lastName;
            } else {
                System.out.println("Invalid last name. Please enter a valid last name.");
            }
        }
    }
	public static String getValidString(Scanner sc) {
        while (true) {
            String string = sc.nextLine();
            if (isValidString(string)) {
                return string;
            } else {
                System.out.println("Input should be in alphabhetical Characters");
            }
        }
    }
	public static String getValidGender(Scanner sc) {
        while (true) {
        	System.out.println("Enter gender : ");
            String gender = sc.nextLine();
            if (isValidGender(gender)) {
                return gender;
            } else {
                System.out.println("Invalid gender. Please enter a valid gender.");
            }
        }
    }
    
	public static String getValidEmail(Scanner sc) {
        while (true) {
            System.out.println("Enter Email:");
            String email = sc.nextLine();
            if (isValidEmail(email)) {
                return email;
            } else {
                System.out.println("Invalid Email. Please enter a valid email address.");
            }
        }
    }

	public static String getValidPassword(Scanner sc) {
        System.out.println( "Password should be at least 8 characters and include lowercase, uppercase, digit, and special character.");
        while (true) {
            System.out.println("Enter Password:");
            String password = sc.nextLine();
            if (isValidPassword(password)) {
                return password;
            } else {
                System.out.println( "Invalid Password. Password should be at least 8 characters and include lowercase, uppercase, digit, and special character.");
            }
        }
    }

	public static String getValidPhoneNumber(Scanner sc) {
        while (true) {
            System.out.println("Enter Contact Number:");
            String phoneNumber = sc.nextLine();
            if (isValidPhoneNumber(phoneNumber)) {
                return phoneNumber;
            } else {
                System.out.println("Invalid Contact Number. Please enter a 10-digit number starting with a digit greater than 5");
            }
        }
    }
	public static String getValidCardNumber(Scanner sc) {
        while (true) {
            System.out.println("Enter Card Number:");
            String card = sc.nextLine().replaceAll("\\s", "");
            if (isValidCard(card)) {
                return card;
            } else {
                System.out.println("Invalid Card Number. Please enter a 16-digit card number");
            }
        }
    }
	public static int getValidCvv(Scanner sc) {
        while (true) {
        	try {
        	    System.out.print("Enter CVV: ");
        		int cvv = sc.nextInt();
        		return cvv;
        	}catch(Exception e) {
        		System.out.println("Invalid CVV.Please enter Valid CVV!");
        	}
        	
        }
    }
    
	public static String getValidCity(Scanner sc) {
        while (true) {
            System.out.println("Enter city name:");
            String city = sc.nextLine();
            if (isValidCity(city)) {
                return city;
            } else {
                System.out.println("Invalid city name. Please enter valid city name!");
            }
        }
    }
	public static int getValidChoice(Scanner sc) {
        while (true) {
        	try {
        		System.out.println("Enter your Choice : ");
        		int input = sc.nextInt();
        		return input;
        	}catch(Exception e) {
        		System.out.println("Choice Should be a number!");
        	    sc.nextLine();
        	}	
        }
    }
	public static int getValidQuantity(Scanner sc,int orderItemID) {
        while (true) {
        	try {
        		System.out.println("Enter the quantity for item " + orderItemID + " :");
        		int quantity = sc.nextInt();
        		return quantity;
        	}catch(Exception e) {
        		System.out.println("Quantity Should be in numbers!");
        	    sc.nextLine();
        	}	
        }
    }
	public static int getValidItemID(Scanner sc) {
        while (true) {
        	try {
        		System.out.println("Enter the item ID to order (or enter 0 to finish order):");
        		int orderItemId = sc.nextInt();
        		return orderItemId;
        	}catch(Exception e) {
        		System.out.println("Item ID Should be in numbers!");
        	    sc.nextLine();
        	}	
        }
    }
	public static int getValidEmpItemID(Scanner sc) {
        while (true) {
        	try {
        		System.out.println("Enter the Id of the Employee you want to delete : ");
        		int empId = sc.nextInt();
        		return empId;
        	}catch(Exception e) {
        		System.out.println("Employee ID Should be in numbers!");
        	    sc.nextLine();
        	}	
        }
    }
	public static int getValidCustId(Scanner sc) {
        while (true) {
        	try {
        		System.out.println("Enter Customer ID : ");
        		int custID = sc.nextInt();
        		return custID;
        	}catch(Exception e) {
        		System.out.println("Customer ID Should be in numbers!");
        	    sc.nextLine();
        	}	
        }
    }
	public static double getValidSalary(Scanner sc) {
        while (true) {
        	try {
        		System.out.println("Enter Salary :");
        		double salary = sc.nextDouble();
        		return salary;
        	}catch(Exception e) {
        		System.out.println("Invalid salary.Please enter Valid salary!");
        		sc.nextLine();
        	}
        	
        }
	}
	public static double getAmount(Scanner sc) {
        while (true) {
        	try {
        		double amount = sc.nextDouble();
        		return amount;
        	}catch(Exception e) {
        		System.out.println("Invalid amount.Please enter Valid amount!");
        		sc.nextLine();
        	}
        	
        }
	}
	
	public static String getValidDate(Scanner sc) {
        while (true) {
            String date = sc.nextLine();
            if (isValidDate(date)) {
                return date;
            } else {
                System.out.println("Invalid date. Please enter valid date!");
            }
        }
    }
	public static String getValidExpiryDate(Scanner sc) {
        while (true) {
        	System.out.print("Enter expiration date (MM-YYYY): ");
            String date = sc.nextLine();
            if (isValidExpiryDate(date)) {
                return date;
            } else {
                System.out.println("Invalid Expiry date. Please enter valid date!");
            }
        }
    }
	
	public static String getValidTime(Scanner sc) {
        while (true) {
            String time = sc.nextLine();
            if (isValidTime(time)) {
                return time;
            } else {
                System.out.println("Invalid time. Please enter valid time");
            }
        }
    }
	public static int getValidRating(Scanner sc) {
        while (true) {
        	try {
        	    System.out.println("Enter Rating (1-5) : ");
        		int rating = sc.nextInt();
        		return rating;
        	}catch(Exception e) {
        		System.out.println("Rating Should be in numbers!");
        	    sc.nextLine();
        	}	
        }
    }
	public static int getValidRervationID(Scanner sc) {
        while (true) {
        	try {
        		System.out.print("Enter Reservation ID to Cancel : ");
        		int resId = sc.nextInt();
        		return resId;
        	}catch(Exception e) {
        		System.out.println("Reservation ID Should be in numbers!");
        	    sc.nextLine();
        	}	
        }
    }
	public static int getValidTableId(Scanner sc) {
        while (true) {
        	try {
        	    System.out.print("Enter Table ID : ");
        		int tableId = sc.nextInt();
        		return tableId;
        	}catch(Exception e) {
        		System.out.println("Table ID Should be in numbers!");
        	    sc.nextLine();
        	}	
        }
    }
	public static boolean isValidString(String string) {
        return string.matches("[a-zA-Z\\s]*");
    }
	public static boolean isValidTime(String time) {
	    String regex = "\\d{2}:\\d{2}";
	    return time.matches(regex);
	}
	public static boolean isValidDate(String date) {
	    String regex = "\\d{4}-\\d{2}-\\d{2}";
	    return date.matches(regex);
	}
	public static boolean isValidExpiryDate(String date) {
	    String regex = "\\d{2}-\\d{4}";
	    return date.matches(regex);
	}

	public static boolean isValidFistName(String firstName) {
        return firstName.matches("[a-z|A-Z]*");
    }
    
	public static boolean isValidLastName(String lastName) {
        return lastName.matches("[a-z|A-Z]*");
    }
    
	public static boolean isValidGender(String gender) {
        return gender.matches("[a-z|A-Z]*");
    }
    
	public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

	public static boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()-+]).{8,}$");
    }

	public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("[6-9]\\d{9}");
    }
	public static boolean isValidCard(String card) {
	    return card.matches("\\d{16}");
	}

	public static boolean isValidCity(String city) {
        return city.matches("[a-z|A-Z]*");
    }

}
