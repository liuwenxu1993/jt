package com.jt.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConf {
	
	//@Value("${redis.mastername}")
	//private String masterName;
	//@Value("${redis.sentinels}")
	//private String sentinel;
	
	//@Value("${redis.nodes}")
	//private String nodes;
	
	@Value("${redis.clusterNodes}")
	private String clusterNodes;
	
	//Redis集群
	@Bean("jedisCluster")
	public JedisCluster jedisCluster() {
		Set<HostAndPort> clusterNode = getClusterNodes();
		System.out.println("redis集群配置");
		return new JedisCluster(clusterNode);
	}

	private Set<HostAndPort> getClusterNodes() {
		String[] hostAndPort = clusterNodes.split(",");
		Set<HostAndPort> clusterNode=new HashSet<>();
		for(String hap:hostAndPort) {
			String[] hp = hap.split(":");
			HostAndPort hps = new HostAndPort(hp[0], Integer.parseInt(hp[1]));
			clusterNode.add(hps);
		}
		return clusterNode;
	}


	//哨兵池 JedisSentinelPool
	//@Bean
//	public JedisSentinelPool jedisSentinelPool() {
//		Set<String> sentinels = new HashSet<>();
//		sentinels.add(sentinel);
//		return new JedisSentinelPool(masterName, sentinels);
//	}
	
	//实现redis哨兵
	//@Bean
	//@Scope("prototype")
//	public Jedis jedis(@Qualifier("jedisSentinelPool")JedisSentinelPool pool) {
//		Jedis jedis = pool.getResource();
//		return jedis;
//	}
	
	//实现redis分片
	//@Bean("shardedJedis")
//	public ShardedJedis shardedJedis() {
//		List<JedisShardInfo> shards = getJedisShardInfo();
//		System.out.println(shards);
//		ShardedJedis jedis = new ShardedJedis(shards);
//		return jedis;
//	}
//	
//	private List<JedisShardInfo> getJedisShardInfo(){
//		List<JedisShardInfo> shards = new ArrayList<>();
//		String[] node = nodes.split(",");
//		for(String n:node) {
//			String[] hosts = n.split(":");
//			String host = hosts[0];
//			Integer port = Integer.parseInt(hosts[1]);
//			System.out.println(port);
//			shards.add(new JedisShardInfo(host,port));
//		}
//		return shards;
//	}
}
