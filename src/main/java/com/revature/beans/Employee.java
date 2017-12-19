package com.revature.beans;

public class Employee {
	
	private int id;
	private String firstname;
	private String lastname;
	private int amount;
	private String password;
	
	public Employee(int id, String firstname, String lastname, int amount, String password) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.amount = amount;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getPassword() {
		return password;
	}

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", amount=" + amount
				+ ", password=" + password + "]";
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

}
