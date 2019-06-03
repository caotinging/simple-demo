package com.caotinging.redisdemo.redisTopic;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

import java.util.Random;

/**
 * 热门排行榜
 * @author caoting
 * @date 2018年12月25日
 *
 */
public class LolBoxPlayer {

	public static void main(String[] args) throws InterruptedException {
		// 连接redis
		JedisShardInfo info = new JedisShardInfo("localhost", 6379);
		info.setPassword("123456");
		Jedis jedis = new Jedis(info);
		
		String[] heros = {"韩信","李白","貂蝉","吕布","高渐离","公孙离","伽罗","马可波罗","不知火舞","迪迦奥特曼"};
		while (true) {
			String hero = heros[new Random().nextInt(heros.length)];
			Thread.sleep(2000);
			
			jedis.zincrby("hero:ccl:phb", 1, hero);
			System.out.println(hero + "出场了");
		}
		
	}
}
