package com.foodiehub.model;

public class Cart {
	
	private int cartID;
	private int userID;
	private int menuID;
	private int quantity;
	private String itemName;
	private double price;
	private String imagePath;
	
	
	
	public int getCartID() {
		return cartID;
	}
	public void setCartID(int cartID) {
		this.cartID = cartID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getMenuID() {
		return menuID;
	}
	public void setMenuID(int menuID) {
		this.menuID = menuID;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Cart() {
		
	}
	public Cart(int cartID, int userID, int menuID, int quantity) {
		this.cartID = cartID;
		this.userID = userID;
		this.menuID = menuID;
		this.quantity = quantity;
	}
	public String getItemName() {
	    return itemName;
	}

	public void setItemName(String itemName) {
	    this.itemName = itemName;
	}

	public double getPrice() {
	    return price;
	}

	public void setPrice(double price) {
	    this.price = price;
	}

	public String getImagePath() {
	    return imagePath;
	}

	public void setImagePath(String imagePath) {
	    this.imagePath = imagePath;
	}
	
	@Override
	public String toString() {
		return "Cart [cartID=" + cartID + ", userID=" + userID + ", menuID=" + menuID + ", quantity=" + quantity + "]";
	}

}
