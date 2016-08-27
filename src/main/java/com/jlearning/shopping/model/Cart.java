package com.jlearning.shopping.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {

	private Map<String, CartItem> items;

	public Cart(){
		items = new HashMap<String, CartItem>();
	}
	
	public Map<String, CartItem> getItems() {
		return items;
	}

	public void setItems(Map<String, CartItem> items) {
		this.items = items;
	}
	
	public void addToCart(CartItem item){
		
		String key = item.getProduct().getUpc();
		if(items.containsKey(key)){
			long qtyOrdered = items.get(key).getQtyOrdered() + item.getQtyOrdered();
			items.get(key).setQtyOrdered(qtyOrdered);
		}
		else{
			items.put(key, item);
		}
	}
	
	public void updateCart(CartItem item){
		String key = item.getProduct().getUpc();
		if(0 == item.getQtyOrdered()){
			items.remove(key);
		}
		else{
			items.get(key).setQtyOrdered(item.getQtyOrdered());
		}
	}
}
