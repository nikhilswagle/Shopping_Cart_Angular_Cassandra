package com.jlearning.shopping.model;

import java.util.HashMap;
import java.util.Map;

public class ProductByCategoryMap {
	
	private Map<String, ProductByCategory> productMap;

	public Map<String, ProductByCategory> getProductMap() {
		return productMap;
	}

	public void setProductMap(Map<String, ProductByCategory> productMap) {
		this.productMap = productMap;
	}
	
	public void addToProductMap(String key, ProductByCategory prod){
		if(null == productMap){
			productMap = new HashMap<String, ProductByCategory>();
		}
		productMap.put(key, prod);
	}
}
