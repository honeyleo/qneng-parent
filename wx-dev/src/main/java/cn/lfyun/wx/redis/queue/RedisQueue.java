package cn.lfyun.wx.redis.queue;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.lfyun.wx.util.JedisPoolUtils;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisQueue {

	public static interface Handler {
		void handle(String key, Object message);
	}
	
	public static Builder init(String key, int num, JedisPool pool) {
		Builder builder = new Builder();
		builder.key = key;
		builder.num = num;
		builder.pool = pool;
		return builder;
	}
	
	private RedisQueue(Builder builder) {
		
	}
	public static class Builder {
		
		private String key;
		
		private int num;
		
		private JedisPool pool;
		
		public Builder producer(String key, int num, JedisPool pool) {
			this.key = key;
			this.num = num;
			this.pool = pool;
			return this;
		}
		
		public void send(String message) {
			int idx = Math.abs(message.hashCode() % num);
			Jedis jedis = pool.getResource();
			String realKey = key + "_" + idx;
			jedis.rpush(realKey, message);
			pool.returnResourceObject(jedis);
		}
		public void send(Object message) {
			int idx = Math.abs(message.hashCode() % num);
			Jedis jedis = pool.getResource();
			String realKey = key + "_" + idx;
			jedis.rpush(realKey, JSON.toJSONString(message));
			pool.returnResourceObject(jedis);
		}
		
		public void handler(final Handler handler) {
			for(int i = 0; i < num; i++) {
				final int mod = i;
				final String realKey = key + "_" + mod;
				System.out.println("start " + realKey);
				ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
				service.scheduleWithFixedDelay(new Runnable() {
					
					@Override
					public void run() {
						Jedis jedis = pool.getResource();
						String message = jedis.lindex(realKey, 0);
						if(message != null) {
							try {
								handler.handle(realKey, message);
								jedis.lpop(realKey);
							} catch(Throwable t) {
								System.err.println(t);
							}
						}
						pool.returnResourceObject(jedis);
					}
				}, 5, 1, TimeUnit.SECONDS);  
			}
		}
		
	}
	
	public static void main(String[] args) {
		RedisQueue.init("queue", 2, JedisPoolUtils.getPool()).send("" + System.currentTimeMillis());
		RedisQueue.init("queue", 2, JedisPoolUtils.getPool()).handler(new Handler() {
			
			@Override
			public void handle(String key, Object message) {
				System.out.println("队列：" + key + "，消费消息：" + message);
			}
		});
	}
}
