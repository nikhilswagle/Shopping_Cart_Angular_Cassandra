package com.jlearning.shopping.config.cassandra;

import java.net.InetSocketAddress;
import java.util.List;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class CassandraCluster extends Cluster{
	
	private Session session;
	
	protected CassandraCluster(String name,
			List<InetSocketAddress> contactPoints, CassandraConfig config) {
		super(name, contactPoints, config.getConfig());
		setSession(super.connect("shoppingcartapp"));
	}
	
	public Session getSession() {
		return session;
	}

	private void setSession(Session session) {
		this.session = session;
	}
}
