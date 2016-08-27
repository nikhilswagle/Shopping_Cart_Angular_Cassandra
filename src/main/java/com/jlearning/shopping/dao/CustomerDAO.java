package com.jlearning.shopping.dao;

import com.jlearning.shopping.model.CustomerByUsername;

public interface CustomerDAO {
	
	public CustomerByUsername getCustomerInfo(CustomerByUsername customer);
}
