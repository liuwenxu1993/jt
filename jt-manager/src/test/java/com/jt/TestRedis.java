package com.jt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jt.mapper.ItemDescMapper;
import com.jt.pojo.ItemDesc;
import com.jt.util.JsonUtils;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.Transaction;

//@SpringBootTest
//@RunWith(SpringRunner.class)
public class TestRedis {
	private String host="192.168.111.129";
	private int port=6379;
	//private Jedis jedis;
	
//	@Before
//	public void init() {
//		jedis = new Jedis(host, port);
//	}
	
	/** spring整合redis入门案例 */
	@Test
	public void testRedis1() {
		jedis.set("1903", "高薪就业");
		String result = jedis.get("1903");
		System.out.println(result);
		jedis.close();
	}
	
	@Test
	public void testRedis2() {
		jedis.append("1903", ",立馬起飛");
		System.out.println(jedis.get("1903"));
	}
	
	@Test
	public void testRedis3() {
		//jedis.expire("1903", 20);
		System.out.println(jedis.ttl("1903"));
	}
	
	//2.设置超时
	@Test
	public void testRedis4() {
		jedis.setex("abc", 100, "英文字母");
		System.out.println(jedis.get("abc"));
	}
	
	/**
	 * 3.锁机制操作
	 *实际用法:保证set数据时如果这个key已经存在,不允许修改
	 *业务场景:
	 * 小明:set("jimian","8点")
	 * 小张:set("jimian","5点")
	 * 小丽:谁约我 
	 *  */
	@Test
	public void testRedis5() {
		jedis.setnx("见面","6点");
		jedis.setnx("见面","5点");
		System.out.println(jedis.get("见面"));
	}
	
	/**
	 * 4.防死锁机制操作
	 * 业务场景:
	 *  小明:setnx("jimian","8点") 若出现了异常,或造成死锁的现象
	 *  */
	@Test
	public void testRedis6() {
		jedis.set("yue", "8dian", "nx", "ex", 10);
		jedis.del("yue");
		jedis.set("yue", "5dian", "nx", "ex", 10);
		System.out.println(jedis.get("yue"));
	}
	
	/**
	 * 5.Hash类型
	 * 在工作中出场率低
	 *  */
	@Test
	public void testHash() {
		jedis.hset("user", "id", "001");
		jedis.hset("user", "name", "小明");
		jedis.hset("user", "age", "28");
		jedis.hset("user", "gender", "男");
		System.out.println(jedis.hgetAll("user"));
	}
	
	/**
	 * 6.List类型  不是缓存,pop后就没了
	 * 队列
	 * 栈
	 *  */
	@Test
	public void testList() {
		jedis.lpush("list", "1","2","3","4","5");
		String res = jedis.rpop("list");
		System.out.println(res);
	}
	
	/**
	 * 7.事务控制
	 * */ 
	@Test
	public void testMulti() {
		Transaction trans = jedis.multi();
		try {
		trans.set("aa", "aa");
		//int i = 1/0;
		trans.set("bb", "bb");
		trans.exec();
		}catch (Exception e) {
			System.out.println(e.getMessage());
			trans.discard();
		}
		System.out.println(jedis.get("bb"));
	}
	
	/**
	 * 8.SpringBoot整合redis实际操作代码
	 * 业务需求:
	 * 	查询itemDesc数据,之后缓存处理
	 * 步骤:
	 * 	1.先查询缓存,是否有
	 *  null 查询数据库,将数据保存到缓存中
	 *  !null 获取数据直接返回
	 *  注意:一般使用redis时都采用String类型操作,但从数据库获取的数据都是对象Object
	 *  */
	@Autowired
	private Jedis jedis;
	
	@Autowired
	private ItemDescMapper mapper;
	
	@Test
	public void testRedis() throws Exception {
		String key = "907012";
		String result = jedis.get(key);
		if(StringUtils.isEmpty(result)) {
			ItemDesc desc = mapper.selectById(key);
			String json = JsonUtils.toJson(desc);
			jedis.set(key, json);
			System.out.println("赋值完成");
		}else {
			ItemDesc res = JsonUtils.toObject(result, ItemDesc.class);
			System.out.println(res);
		}
	}
	
	//测试redis分片
	@Test
	public void testShards() {
		ArrayList<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		String host = "192.168.19.129";
		shards.add(new JedisShardInfo(host,6379));
		shards.add(new JedisShardInfo(host,6380));
		shards.add(new JedisShardInfo(host,6381));
		ShardedJedis redis = new ShardedJedis(shards);
		redis.set("ceshi", "test");
		System.out.println(redis.get("ceshi"));
		redis.close();
	}
	
	//测试redis哨兵
	@Test
	public void testSentinel() {
		Set<String> sentinels = new HashSet<>();
		sentinels.add("192.168.19.129:26379");
		JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels);
		Jedis jedis = pool.getResource();
		jedis.set("aa", "董天赐好骚气");
		System.out.println(jedis.get("aa"));
		jedis.close();
		pool.close();
	}
	
	//测试redis集群
	@Test
	public void testCluster() {
		Set<HostAndPort> nodes =new HashSet<>();
		nodes.add(new HostAndPort("192.168.19.129", 7000));
		nodes.add(new HostAndPort("192.168.19.129", 7001));
		nodes.add(new HostAndPort("192.168.19.129", 7002));
		nodes.add(new HostAndPort("192.168.19.129", 7003));
		nodes.add(new HostAndPort("192.168.19.129", 7004));
		nodes.add(new HostAndPort("192.168.19.129", 7005));
		JedisCluster cluster = new JedisCluster(nodes);
		cluster.set("aa", "ClusterTest");
		System.out.println(cluster.get("aa"));
	}
}