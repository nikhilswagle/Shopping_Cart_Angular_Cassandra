package com.jlearning.shopping.model;

import java.util.ArrayList;
import java.util.List;

public class ProductByCategoryList {

	private List<ProductByCategory> products;

	public List<ProductByCategory> getProducts() {
		return products;
	}

	public void setProducts(List<ProductByCategory> products) {
		this.products = products;
	}
	
	public void addToProductList(ProductByCategory product){
		if(null == products){
			products = new ArrayList<ProductByCategory>();
		}
		products.add(product);
	}
}
