package com.example.barcode;

import java.util.ArrayList;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MonthSales {

	private int id;
	private double totalProft;
	private double totalPurchase;
	private String date;



	public MonthSales(int id,double totalPurchase, String date, double totalProft) {
		super();
		this.totalProft = totalProft;
		this.totalPurchase = totalPurchase;
		this.date = date;
		this.id = id;


	}


	public MonthSales(double totalPurchase, String date, double totalProft) {
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
