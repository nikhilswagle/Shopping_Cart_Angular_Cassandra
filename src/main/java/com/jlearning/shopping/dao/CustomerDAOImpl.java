package com.jlearning.shopping.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.jlearning.shopping.config.cassandra.CassandraCluster;
import com.jlearning.shopping.model.CustomerByUsername;

@Service("customerDao")
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private CassandraCluster cassandraCluster;
	
	@Override
	public CustomerByUsername getCustomerInfo(CustomerByUsername customer) {
		Session session = cassandraCluster.getSession();
		Statement getCustomer = QueryBuilder.select()
										.all()
										.from("shoppingcartapp", "customers_by_username")
										.where(QueryBuilder.eq("username", customer.getUsername()));

		ResultSet results = session.execute(getCustomer);
		
		CustomerByUsername customerInfo = null;
		for (Row row : results) {
			customerInfo = new CustomerByUsername();
			customerInfo.setCustomerId(row.getUUID("customer_id"));
			customerInfo.setEmail(row.getString("email"));
			customerInfo.setUsername(row.getString("username"));
			customerInfo.setFirstname(row.getString("firstname"));
			customerInfo.setLastname(row.getString("lastname"));
			customerInfo.setPassword(row.getString("password"));
		}
		return customerInfo;
	}
}
