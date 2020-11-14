package com.luban.testcache.controller;

import com.luban.testcache.entity.NullValueResultDO;
import com.luban.testcache.entity.Order;
import com.luban.testcache.entity.R;
import com.luban.testcache.filter.RedisBloomFilter;
import com.luban.testcache.service.OrderService;
import com.luban.testcache.util.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCommands;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/test")
    public Integer insertOrder(Order order){
        return orderService.insertOrder(order);
    }


    @GetMapping("/selectid")
    public R selectOrderById(Integer id){
        return orderService.selectOrderById(id);
    }
}
