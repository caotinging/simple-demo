package com.caotinging.redisdemo.redisTopic;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Tuple;

import java.util.Set;

/**
 * 英雄出场率排行榜查看模块
 * 
 * @author
 * 
 */
public class LolBoxViewer {

	public static void main(String[] args) throws Exception {
		// 连接redis
		JedisShardInfo info = new JedisShardInfo("localhost", 6379);
		info.setPassword("123456");
		Jedis jedis = new Jedis(info);
		
		int i = 1;
		while (true) {
			Thread.sleep(3000);
			System.out.println("第 " + i + " 次查询英雄热门排行榜");
			Set<Tuple> tuples = jedis.zrevrangeWithScores("hero:ccl:phb", 0L, 10L);
			
			for (Tuple tuple : tuples) {
				System.out.println(tuple.getElement()+"     "+tuple.getScore());
			}
			i++;
			
			System.out.println("");
			System.out.println("");
			System.out.println("");
		}
		
	}
}
