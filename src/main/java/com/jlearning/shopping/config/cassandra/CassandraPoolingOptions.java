package com.jlearning.shopping.config.cassandra;

import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.PoolingOptions;

public class CassandraPoolingOptions {
	private PoolingOptions poolingOptions;
	
	public CassandraPoolingOptions(int coreConnectionsPerLocalHost, int maxConnectionsPerLocalHost, int coreConnectionsPerRemoteHost, 
									int maxConnectionsPerRemoteHost, int heartbeatIntervalSeconds) {
		poolingOptions = new PoolingOptions();
		poolingOptions.setConnectionsPerHost(HostDistance.LOCAL, coreConnectionsPerLocalHost, maxConnectionsPerLocalHost);
		poolingOptions.setConnectionsPerHost(HostDistance.REMOTE, coreConnectionsPerRemoteHost, maxConnectionsPerRemoteHost);
		poolingOptions.setHeartbeatIntervalSeconds(heartbeatIntervalSeconds);
	}
	
	public PoolingOptions getPoolingOptions(){
		return poolingOptions;
	}
}
