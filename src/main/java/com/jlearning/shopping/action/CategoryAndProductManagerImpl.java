package com.jlearning.shopping.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jlearning.shopping.dao.CategoryAndProductDAO;
import com.jlearning.shopping.model.Category;
import com.jlearning.shopping.model.CategoryList;
import com.jlearning.shopping.model.ProductByCategoryMap;
import com.jlearning.shopping.model.ProductByUpc;

@Service("catAndProdManager")
public class CategoryAndProductManagerImpl implements
		CategoryAndProductManager {

	@Autowired
	@Qualifier("cassandraCatAndProdDao")
	private CategoryAndProductDAO cassandraDao;
	
	@Override
	public CategoryList retrieveProductCategories() {
		return cassandraDao.getProductCategories();
	}

	@Override
	public ProductByCategoryMap retrieveProductsByCategory(Category category) {
		return cassandraDao.getProductsByCategory(category);
	}

	@Override
	public ProductByUpc retrieveProductByUpc(String upc) {
		return cassandraDao.getProductByUpc(upc);
	}

}
