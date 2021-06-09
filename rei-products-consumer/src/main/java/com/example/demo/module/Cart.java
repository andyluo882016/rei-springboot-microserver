package com.example.demo.module;
import java.util.*;

import org.springframework.stereotype.Component;
@Component
public class Cart {

	private String id;
	private Item item;
	private CheckOutProducts product;
	private Integer total;
	private double charge;
	LinkedList<CheckOutProducts> plist;// =new LinkedList<CheckOutProducts>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public CheckOutProducts getProduct() {
		return product;
	}
	public void setProduct(CheckOutProducts product) {
		this.product = product;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public double getCharge() {
		return charge;
	}
	public void setCharge(double charge) {
		this.charge = charge;
	}
	public LinkedList<CheckOutProducts> getPlist() {
		return plist;
	}
	public void setPlist(LinkedList<CheckOutProducts> plist) {
		this.plist = plist;
	}
	public Cart(String id, Item item, CheckOutProducts product, Integer total, double charge) {
		super();
		this.id = id;
		this.item = item;
		this.product = product;
		this.total = total;
		this.charge = charge;
		this.plist=new LinkedList<CheckOutProducts>();
	}
	public Cart() {
		super();
		this.plist=new LinkedList<CheckOutProducts>();
	}
	@Override
	public String toString() {
		return "Cart [id=" + id + ", item=" + item + ", product=" + product + ", total=" + total + ", charge=" + charge
				+ ", plist=" + plist + "]";
	}
	
}
