package com.jlearning.shopping.model;

import java.util.UUID;

public class ProductByCategory {

	private UUID categoryId;
	private String upc;
	private String categoryName;
	private String title;
	private String description;
	private Double price;
	
	public UUID getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(UUID categoryId) {
		this.categoryId = categoryId;
	}
	public String getUpc() {
		return upc;
	}
	public void setUpc(String upc) {
		this.upc = upc;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
}
