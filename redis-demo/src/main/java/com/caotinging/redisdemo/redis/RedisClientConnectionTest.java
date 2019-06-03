package com.caotinging.redisdemo.redis;

import com.google.gson.Gson;
import org.junit.Test;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

public class RedisClientConnectionTest {
	private static ShardedJedisPool pool;
	static {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(100);
		config.setMaxIdle(50);
		config.setMaxWaitMillis(3000);
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);

		// 集群
		JedisShardInfo jedisShardInfo1 = new JedisShardInfo("192.168.192.129", 6379);
		jedisShardInfo1.setPassword("123456");
		List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
		list.add(jedisShardInfo1);
		pool = new ShardedJedisPool(config, list);
	}

	public static void main(String[] args) {
		ShardedJedis jedis = pool.getResource();
		String keys = "myname";
		String vaule = jedis.set(keys, "lxr");
		System.out.println(vaule);
	}

	/*
	 * public static void main(String[] args) { // 构造一个redis的客户端对象 Jedis jedis =
	 * new Jedis("pinshutang.zicp.net", 6379); String ping = jedis.ping();
	 * System.out.println(ping); }
	 */

	/**
	 * 将对象缓存到redis的string结构数据中
	 * 
	 * @throws Exception
	 *
	 */
	@Test
	public void testObjectCache() throws Exception {
		ShardedJedis jedis = pool.getResource();
		ProductInfo p = new ProductInfo();

		p.setName("sufei");
		p.setDescription("angelababy zhaunyong");
		p.setCatelog("unknow");
		p.setPrice(10.8);

		// 将对象序列化成字节数组
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(ba);

		// 用对象序列化流来将p对象序列化，然后把序列化之后的二进制数据写到ba流中
		oos.writeObject(p);

		// 将ba流转成byte数组
		byte[] pBytes = ba.toByteArray();

		// 将对象序列化之后的byte数组存到redis的string结构数据中
		String set = jedis.set("product:01".getBytes(), pBytes);
		System.out.println("set: " + set);

		// 根据key从redis中取出对象的byte数据
		byte[] pBytesResp = jedis.get("product:01".getBytes());

		// 将byte数据反序列出对象
		ByteArrayInputStream bi = new ByteArrayInputStream(pBytesResp);
		ObjectInputStream oi = new ObjectInputStream(bi);

		// 从对象读取流中读取出p对象
		ProductInfo pResp = (ProductInfo) oi.readObject();
		System.out.println(pResp);

	}

	/**
	 * 将对象转成json字符串缓存到redis的string结构数据中
	 */
	@Test
	public void testObjectToJsonCache() {
		ShardedJedis jedis = pool.getResource();
		ProductInfo p = new ProductInfo();

		p.setName("ABC");
		p.setDescription("liuyifei zhuanyong");
		p.setCatelog("unkown");
		p.setPrice(10.8);

		// 利用gson将对象转成json串
		Gson gson = new Gson();
		String pJson = gson.toJson(p);

		// 将json串存入redis
		String set = jedis.set("prodcut:02", pJson);
		System.out.println("set: " + set);

		// 从redis中取出对象的json串
		String pJsonResp = jedis.get("prodcut:02");

		// 将返回的json解析成对象
		ProductInfo pResponse = gson.fromJson(pJsonResp, ProductInfo.class);

		// 显示对象的属性
		System.out.println(pResponse);
	}

}
