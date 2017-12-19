package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.beans.Employee;
import com.revature.util.ConnectionUtil;

public class EmployeeDAO {
	
	public List<Employee> getAllEmployee() {
		PreparedStatement ps = null;
		Employee e = null;
		List<Employee> employee = new ArrayList<>();
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM EMPLOYEE";
			ps = conn.prepareStatement(sql);
			//Add any variables to PS
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("EMPLOYEEID");
				String f_name = rs.getString("FIRSTNAME");
				String l_name = rs.getString("LASTNAME");
				int amt = rs.getInt("AMOUNT");
				String pw = rs.getString("PASSWORD");
				
				e = new Employee(id, f_name, l_name, amt, pw);
				employee.add(e);
			}
			rs.close();
			ps.close();
		} catch (Exception ex) {
			ex.getMessage();
		}
		
		return employee;
	}
	
    public boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }
    
    public int formatAmount (String str, Scanner scan) {
    	int answer = -1;
		if (isNumeric(str)) {
			if(Integer.parseInt(str) > 0) {
				answer = Integer.parseInt(str);
			}
		} else {
			System.out.println("Sorry, but thats an invalid amount, please try again.");
			System.out.println("Please enter an amount to submit for reimbursement.");
			String temp = scan.nextLine();
			formatAmount(temp, scan);
		}
		return answer;
    }
    
    public int checkLogin(String login, String password, Scanner scan) {
    	int answer = -1;
    	if(isNumeric(login)) {
			int login_id = Integer.parseInt(login);
			if(getEmployee(Integer.parseInt(login)) != null) {
				Employee temp = getEmployee(login_id);
				if(temp.getPassword().equals(password)) {
					answer = login_id;
				}
			}
		} else {
			//Login Failed Prompt User Again
			System.out.println("I'm sorry, but you either chose a wrong ID or put in the wrong password.");
			System.out.println("Please enter an ID");
			String next_login = scan.nextLine();
			System.out.println("Please enter your password");
			String next_password = scan.nextLine();
			checkLogin(next_login, next_password, scan);
		}
    	return answer;
    }
	public Employee getEmployee(int id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Employee e = null;
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM EMPLOYEE WHERE EMPLOYEEID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				int eid = rs.getInt("EMPLOYEEID");
				String f_name = rs.getString("FIRSTNAME");
				String l_name = rs.getString("LASTNAME");
				int amt = rs.getInt("AMOUNT");
				String pw = rs.getString("PASSWORD");
				
				e = new Employee(eid, f_name, l_name, amt, pw);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException sq) {
					// TODO Auto-generated catch block
					sq.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sq) {
					// TODO Auto-generated catch block
					sq.printStackTrace();
				}
			}
		}
		return e;
	}
	public boolean checkAmount(int amount, Employee e) {
		if(e.getAmount() - amount >= 0) {
			return true;
		} else {
			return false;
		}		
	}
	public void submitForm(int eid, int amt, Scanner scan) {
		if(checkAmount(amt, getEmployee(eid))) {
			CallableStatement cs = null;
			try (Connection conn = ConnectionUtil.getConnection()) {
				String sql = "{CALL SP_SUBMIT_FORM(?, ?)}";
				cs = conn.prepareCall(sql);
				cs.setInt(1, eid);			
				cs.setInt(2, amt);
				
				Boolean result = cs.execute();
				if (!result)
					System.out.println("Successfully Submitted Form!");
				else
					System.out.println("Sorry you don't have enough for the required amount try again.");
				
				cs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			System.out.println("Im sorry, but we couldn't reimburst you for the amount you gave.");
			System.out.println("The maxium reimburstment we can give you is" + getEmployee(eid).getAmount());
			System.out.println("Please enter an amount to submit for reimbursement.");
			String temp = scan.nextLine();
			submitForm(eid, formatAmount(temp, scan), scan);
		}
	}
	
	
	
	
}
