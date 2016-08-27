package com.jlearning.shopping.action;

import com.jlearning.shopping.model.Category;
import com.jlearning.shopping.model.CategoryList;
import com.jlearning.shopping.model.ProductByCategoryMap;
import com.jlearning.shopping.model.ProductByUpc;


public interface CategoryAndProductManager {

	public CategoryList retrieveProductCategories();
	
	public ProductByCategoryMap retrieveProductsByCategory(Category category);
	
	public ProductByUpc retrieveProductByUpc(String upc);
		
}
