package com.jlearning.shopping.config.cassandra;
import java.util.UUID;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.NettyOptions;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;
import com.datastax.driver.core.querybuilder.QueryBuilder;


public class TestCassandra {

	public static void main(String[] args) {
		PoolingOptions poolingOpts = new PoolingOptions();
		poolingOpts.setConnectionsPerHost(HostDistance.LOCAL, 4, 20);
		poolingOpts.setMaxRequestsPerConnection(HostDistance.LOCAL, 100);
		poolingOpts.setConnectionsPerHost(HostDistance.REMOTE, 4, 10);
		poolingOpts.setMaxRequestsPerConnection(HostDistance.REMOTE, 50);
		poolingOpts.setHeartbeatIntervalSeconds(60);
		
		Cluster cluster = Cluster.builder()
									.withRetryPolicy(DefaultRetryPolicy.INSTANCE)
									.withNettyOptions(NettyOptions.DEFAULT_INSTANCE)
									.withPoolingOptions(poolingOpts)
									.withLoadBalancingPolicy(
											new TokenAwarePolicy(
													DCAwareRoundRobinPolicy.builder()
														.withLocalDc("datacenter1").build()))
									.addContactPoint("127.0.0.1").build();
		Session session = cluster.connect("shoppingcartapp");
		
		ProtocolVersion protocolVersion = cluster.getConfiguration().getProtocolOptions().getProtocolVersion();
		
		System.out.println("Protocol Version : "+protocolVersion.name());
		
		Statement select = QueryBuilder.select()
										.all()
										.from("shoppingcartapp", "products_by_category")
										.where(QueryBuilder.eq("category_id", UUID.fromString("a7425950-625e-11e6-9699-a373e4e238df")));

		ResultSet results = session.execute(select);
		for (Row row : results) {
			System.out.format("%s %s %s %s \n", row.getString("upc"),
					row.getString("category_name"), row.getDouble("price"), row.getString("title"));
		}
		
		cluster.close();
	}
}
