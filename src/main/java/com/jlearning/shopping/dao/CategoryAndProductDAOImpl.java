package com.jlearning.shopping.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.jlearning.shopping.config.cassandra.CassandraCluster;
import com.jlearning.shopping.model.Category;
import com.jlearning.shopping.model.CategoryList;
import com.jlearning.shopping.model.ProductByCategory;
import com.jlearning.shopping.model.ProductByCategoryMap;
import com.jlearning.shopping.model.ProductByUpc;

@Service("cassandraCatAndProdDao")
public class CategoryAndProductDAOImpl implements
		CategoryAndProductDAO {
	
	@Autowired
	private CassandraCluster cassandraCluster;

	@Override
	public CategoryList getProductCategories() {
		Session session = cassandraCluster.getSession();
		Statement select = QueryBuilder.select()
										.all()
										.from("shoppingcartapp", "category");

		ResultSet results = session.execute(select);		
		CategoryList catList = new CategoryList();		
		Category cat = null;
		for (Row row : results) {
			cat = new Category();
			cat.setCategoryId(row.getUUID("category_id"));
			cat.setCategoryName(row.getString("category_name"));
			catList.addToCategories(cat);
		}
		return catList;
	}

	@Override
	public ProductByCategoryMap getProductsByCategory(Category category) {
		Session session = cassandraCluster.getSession();
		Statement select = QueryBuilder.select()
										.all()
										.from("shoppingcartapp", "products_by_category")
										.where(QueryBuilder.eq("category_id", category.getCategoryId()));
						
		ResultSet results = session.execute(select);
		ProductByCategoryMap prodMap = new ProductByCategoryMap();		
		ProductByCategory prodByCat = null;
		for (Row row : results) {
			prodByCat = new ProductByCategory();
			prodByCat.setCategoryId(row.getUUID("category_id"));
			prodByCat.setCategoryName(row.getString("category_name"));
			prodByCat.setUpc(row.getString("upc"));
			prodByCat.setTitle(row.getString("title"));
			prodByCat.setDescription(row.getString("description"));
			prodByCat.setPrice(Double.valueOf(row.getDouble("price")));
			prodMap.addToProductMap(prodByCat.getUpc(), prodByCat);
		}
		return prodMap;
	}

	@Override
	public ProductByUpc getProductByUpc(String upc) {
		Session session = cassandraCluster.getSession();
		
		// Query 1 : Get Product Info
		Statement select = QueryBuilder.select()
										.all()
										.from("shoppingcartapp", "products_by_upc")
										.where(QueryBuilder.eq("upc", upc));
						
		ResultSet prodSearchResults = session.execute(select);		
		ProductByUpc prodByUpc = null;
		for (Row row : prodSearchResults) {
			prodByUpc = new ProductByUpc();
			prodByUpc.setUpc(row.getString("upc"));
			prodByUpc.setTitle(row.getString("title"));
			prodByUpc.setDescription(row.getString("description"));
			prodByUpc.setPrice(row.getDouble("price"));
			break; // The result would contain only one row
		}
		
		// Query 2 : Get Product In Stock Qty
		Statement selectInStockQty = QueryBuilder.select()
										.all()
										.from("shoppingcartapp", "instockcounter_by_upc")
										.where(QueryBuilder.eq("upc", upc));
		
		ResultSet prodQtySearchResults = session.execute(selectInStockQty);
		for (Row row : prodQtySearchResults) {
			// Counter maps to long (https://docs.datastax.com/en/developer/java-driver/1.0/java-driver/reference/javaClass2Cql3Datatypes_r.html)
			prodByUpc.setInStockQty(Long.valueOf(row.getLong("in_stock_qty")));
			break; // The result would contain only one row
		}
		
		return prodByUpc;
	}	
}
