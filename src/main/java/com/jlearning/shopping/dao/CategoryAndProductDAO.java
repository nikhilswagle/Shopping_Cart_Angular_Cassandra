package com.jlearning.shopping.dao;

import com.jlearning.shopping.model.Category;
import com.jlearning.shopping.model.CategoryList;
import com.jlearning.shopping.model.ProductByCategoryMap;
import com.jlearning.shopping.model.ProductByUpc;

public interface CategoryAndProductDAO {
	
	public CategoryList getProductCategories();
	
	public ProductByCategoryMap getProductsByCategory(Category category);
	
	public ProductByUpc getProductByUpc(String upc);
}
