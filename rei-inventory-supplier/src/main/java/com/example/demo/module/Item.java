package com.example.demo.module;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="item")
public class Item {
    @Id
	private String id;
	private String productId;
	private int quantity;
	private Location location;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Item(String productId, int quantity, Location location) {
		super();
		this.productId = productId;
		this.quantity = quantity;
		this.location = location;
	}
	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Item [id=" + id + ", productId=" + productId + ", quantity=" + quantity + ", location=" + location
				+ "]";
	}
	
	
	
}
