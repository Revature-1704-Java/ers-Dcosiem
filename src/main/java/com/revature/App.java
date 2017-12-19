package com.revature;

import java.util.Scanner;

import com.revature.dao.EmployeeDAO;

public class App {

	public static void main(String[] args) {
		EmployeeDAO dao = new EmployeeDAO();
		
		//Perform Login
		System.out.println("Welcome to the Employee Reimbursement System.");
		System.out.println("Please enter your Employee ID");
		Scanner scan = new Scanner(System.in);
		String login = scan.nextLine();
		System.out.println("Please enter your Password");
		String pass = scan.nextLine();
		
		//Login, will keep asking until you input the correct credentials
		int login_id = dao.checkLogin(login, pass, scan);
		
		System.out.println("Please enter an amount to submit for reimbursement.");
		String temp = scan.nextLine();
		int amount_in = dao.formatAmount(temp, scan);		
		dao.submitForm(login_id, amount_in, scan);
		
		
		System.out.println("Thank you for using ERS.");
		System.out.println("We hope to see you again!");
		System.out.println("Goodbye!");
		System.exit(0);

	}

}
