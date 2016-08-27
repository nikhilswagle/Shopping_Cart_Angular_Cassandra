package com.jlearning.shopping.model;

public class CartItem {
	
	private ProductByUpc product;
	private Long qtyOrdered;
	
	public ProductByUpc getProduct() {
		return product;
	}
	public void setProduct(ProductByUpc product) {
		this.product = product;
	}
	public Long getQtyOrdered() {
		return qtyOrdered;
	}
	public void setQtyOrdered(Long qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}	
}
