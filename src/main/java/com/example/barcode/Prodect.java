package com.example.barcode;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Prodect implements Comparable<Prodect> {

	private String ID;
	private String name;
	private double Purchase;
	private int numCopy;
	private double price;
	private double proft;

	private double total;
	private int quantity;
	private double totalproft;

	private int idd;



	public Prodect(String name, int quantity, double price, double total) {
		super();

		this.name = name;

		this.quantity = quantity;
		this.price = price;
		this.total = total;
	}



	public Prodect(String iD, String name, double purchase, int numCopy, double price) {
		super();
		ID = iD;
		this.name = name;
		Purchase = purchase;
		this.numCopy = numCopy;
		this.price = price;

	}


	public Prodect(String iD, String name, double purchase, int numCopy, double price, int quantity) {
		super();
		ID = iD;
		this.name = name;
		Purchase = purchase;
		this.numCopy = numCopy;
		this.price = price;
		this.quantity = quantity;

	}


	public Prodect(String ID, String name, double price, int quantity, double total, double proft, double Purchase,
			int numCopy, double totalproft) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.total = total;
		this.proft = proft;
		this.Purchase = Purchase;
		this.ID = ID;
		this.numCopy = numCopy;
		this.totalproft = totalproft;
	}



	public Prodect(String ID, String name, double price, double total, int quantity, double proft, double Purchase,
			double totalproft ) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.total = total;
		this.proft = proft;
		this.Purchase = Purchase;
		this.ID = ID;
		this.totalproft = totalproft;
	}

	public Prodect(String ID, String name, double price, double total, int quantity, double proft, double Purchase,
				   double totalproft , int idd) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.total = total;
		this.proft = proft;
		this.Purchase = Purchase;
		this.ID = ID;
		this.totalproft = totalproft;
		this.idd = idd;
	}


	public Prodect( String name, double price, double total, int quantity, double proft, double Purchase,
				   double totalproft , int idd) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.total = total;
		this.proft = proft;
		this.Purchase = Purchase;
		this.ID = ID;
		this.totalproft = totalproft;
		this.idd = idd;
	}





	public Prodect() {
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPurchase() {
		return Purchase;
	}

	public void setPurchase(double purchase) {
		Purchase = purchase;
	}

	public int getNumCopy() {
		return numCopy;
	}

	public void setNumCopy(int numCopy) {
		this.numCopy = numCopy;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getProft() {
		return proft = price - Purchase;
	}

	public double getTotalProft() {
		return totalproft = proft * quantity;
	}

	public void setProft(double proft) {
		this.proft = proft;
	}

	public double getTotal() {
		return total = price * quantity;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getQuantity() {
		return quantity = quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotalproft() {
		return totalproft = proft * quantity;
	}

	public void setTotalproft(double totalproft) {
		this.totalproft = totalproft;
	}

	public int getIdd() {
		return idd;
	}

	public void setIdd(int idd) {
		this.idd = idd;
	}


	@Override
	public String toString() {
		return " " + name + "       " + "           " + quantity + "        " + price + "";
	}

	public int compareTo(Prodect o) {
		return this.name.compareTo(o.name);
	}

}
