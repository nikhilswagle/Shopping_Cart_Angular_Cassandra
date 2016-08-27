package com.jlearning.shopping.model;

import java.util.UUID;

public class Category {
	private UUID categoryId;
	private String categoryName;
	private String categoryDesc;
	
	public UUID getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(UUID categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryDesc() {
		return categoryDesc;
	}
	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}
}
