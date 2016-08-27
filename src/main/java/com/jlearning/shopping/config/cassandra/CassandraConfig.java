package com.jlearning.shopping.config.cassandra;

import com.datastax.driver.core.Configuration;

public class CassandraConfig {
	
	private Configuration config;
	
	public CassandraConfig(CassandraPolicies policies, CassandraPoolingOptions poolingOptions) {
		 setConfig(Configuration.builder()
				 				.withPolicies(policies.getPolicies())
				 				.withPoolingOptions(poolingOptions.getPoolingOptions())
				 				.build());
	}

	public Configuration getConfig() {
		return config;
	}

	private void setConfig(Configuration config) {
		this.config = config;
	}
}
