package com.revature.beans;

public class Reimbursement {
	private int Employeeid;
	private int amount;
	public int getEmployeeid() {
		return Employeeid;
	}
	public void setEmployeeid(int employeeid) {
		Employeeid = employeeid;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "Reimbursement [Employeeid=" + Employeeid + ", amount=" + amount + "]";
	}
	public Reimbursement() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Reimbursement(int employeeid, int amount) {
		super();
		Employeeid = employeeid;
		this.amount = amount;
	}
	
}
