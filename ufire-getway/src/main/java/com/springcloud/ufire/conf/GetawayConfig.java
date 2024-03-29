package com.springcloud.ufire.conf;

import com.springcloud.ufire.filter.CustomLoadBalancerClientFilter;
import com.springcloud.ufire.rule.IChooseRule;
import com.springcloud.ufire.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @program: ufire-springcloud-platform
 * @description: X
 * @author: fengandong
 * @create: 2021-01-04 16:58
 **/
@Configuration
public class GetawayConfig {
    private static final Logger log = LoggerFactory.getLogger(HashRingConfig.class);

    @Autowired
    private JedisPool jedisPool;


    @Autowired

    private DiscoveryClient discoveryClient;

    @Bean
    public CustomLoadBalancerClientFilter loadBalancerClientFilter(LoadBalancerClient client,
                                                                   LoadBalancerProperties properties,
                                                                   HashRingConfig hashRingConfig,
                                                                   List<IChooseRule> chooseRules) {
        return new CustomLoadBalancerClientFilter(client, properties, hashRingConfig, chooseRules);
    }

    @Bean
    public HashRingConfig initHashRingConfig() {
        log.info("-------------------初始化HashRingConfig对象-----------------");
        HashRingConfig hashRingConfig = new HashRingConfig();
        Jedis jedis = jedisPool.getResource();
        Map<String, String> serverMap = jedis.hgetAll("ufire-websocket");
        Map<String, String> userMap = jedis.hgetAll("user");
        hashRingConfig.updateHashRing(serverMap, userMap);
        List<ServiceInstance> instances = discoveryClient.getInstances("ufire-websocket");
        hashRingConfig.setInstances(instances);
        //增加虚拟节点
        hashRingConfig.addVirtualNode(hashRingConfig.getHashRing());
        return hashRingConfig;
    }

}
