package com.jlearning.shopping.config.cassandra;

import com.datastax.driver.core.policies.LoadBalancingPolicy;
import com.datastax.driver.core.policies.Policies;

public class CassandraPolicies {
	private Policies policies;
	public CassandraPolicies(LoadBalancingPolicy loadBalancingPolicy) {
		setPolicies(Policies.builder().withLoadBalancingPolicy(loadBalancingPolicy).build());
	}
	
	public Policies getPolicies() {
		return policies;
	}
	
	private void setPolicies(Policies policies) {
		this.policies = policies;
	}
}
