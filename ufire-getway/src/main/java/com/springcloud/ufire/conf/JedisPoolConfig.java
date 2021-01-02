package com.springcloud.ufire.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @description:
 * @author: Andy
 * @time: 2021/1/2 20:00
 */
@Configuration
public class JedisPoolConfig {
    @Bean
    Jedis getJedis() {
        return new Jedis("123.57.139.87", 6379);
    }
}
