package com.example.barcode;

public class Customer implements Comparable<Customer> {

	private String id;
	private String name;
	private double debt;
	private String phoneNumber;
	private String Date;
	private String CustomerType;

	public Customer(String id,String name, double debt, String phoneNumber, String CustomerType, String Date) {
		this.name = name;
		this.debt = debt;
		this.phoneNumber = phoneNumber;
		this.CustomerType = CustomerType;
		this.Date = Date;
		this.id = id;
	}


	public Customer(String name, double debt, String phoneNumber, String CustomerType, String Date) {
		this.name = name;
		this.debt = debt;
		this.phoneNumber = phoneNumber;
		this.CustomerType = CustomerType;
		this.Date = Date;

	}


	public Customer() {
	}

	public String getName() {
		return name;
	}

	public double getDebt() {
		return debt;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDebt(double debt) {
		this.debt = debt;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getCustomerType() {
		return CustomerType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCustomerType(String customerType) {
		CustomerType = customerType;
	}

	public String toString() {
		return "Name: " + name + "\nDebt: " + debt + "\nPhone Number: " + phoneNumber;
	}

	@Override
	public int compareTo(Customer o) {
		return this.name.compareTo(o.name);
	}
}
