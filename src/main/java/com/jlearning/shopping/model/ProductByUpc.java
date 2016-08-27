package com.jlearning.shopping.model;


public class ProductByUpc {

	private String upc;
	private String title;
	private String description;
	private Double price;
	private Long inStockQty;
	
	public String getUpc() {
		return upc;
	}
	public void setUpc(String upc) {
		this.upc = upc;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Long getInStockQty() {
		return inStockQty;
	}
	public void setInStockQty(Long inStockQty) {
		this.inStockQty = inStockQty;
	}	
}
