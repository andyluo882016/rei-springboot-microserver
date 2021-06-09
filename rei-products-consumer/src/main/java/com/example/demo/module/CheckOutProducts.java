package com.example.demo.module;

public class CheckOutProducts {

	private Product product;
	private Integer amount;
	private double topay;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public double getTopay() {
		return topay;
	}
	public void setTopay(double topay) {
		this.topay = topay;
	}
	public CheckOutProducts(Product product, Integer amount, double topay) {
		super();
		this.product = product;
		this.amount = amount;
		this.topay = topay;
	}
	public CheckOutProducts() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		long temp;
		temp = Double.doubleToLongBits(topay);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CheckOutProducts other = (CheckOutProducts) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (Double.doubleToLongBits(topay) != Double.doubleToLongBits(other.topay))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CheckOutProducts [product=" + product + ", amount=" + amount + ", topay=" + topay + "]";
	}
	
	
}
