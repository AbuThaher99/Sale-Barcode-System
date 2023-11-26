package com.example.barcode;

public class YearSales {

	private int id;
	private double totalProft;
	private double totalPurchase;
	private String date;

	public YearSales(int id,double totalProft, String date, double totalPurchase) {
		super();
		this.totalProft = totalProft;
		this.totalPurchase = totalPurchase;
		this.date = date;
		this.id = id;
	}

	public YearSales(double totalProft, String date, double totalPurchase) {
		super();
		this.totalProft = totalProft;
		this.totalPurchase = totalPurchase;
		this.date = date;
	}

	public double getTotalProft() {
		return totalProft;
	}

	public void setTotalProft(double totalProft) {
		this.totalProft = totalProft;
	}

	public double getTotalPurchase() {
		return totalPurchase;
	}

	public void setTotalPurchase(double totalPurchase) {
		this.totalPurchase = totalPurchase;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	// getters and setters


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
