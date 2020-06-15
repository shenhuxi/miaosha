package com.zpself.module.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.zpself.module.service.OrderService;
import com.zpself.module.service.StockService;
import com.zpself.module.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private StockService stockService;

    // Guava令牌桶：每秒放行10个请求
    RateLimiter rateLimiter = RateLimiter.create(10);
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    /**
     * 下单接口：乐观锁更新库存 + 令牌桶限流
     * @param sid
     * @return
     */
    @RequestMapping("/createOptimisticOrder/{sid}")
    @ResponseBody
    public String createOptimisticOrder(@PathVariable int sid) {
        // 1. 阻塞式获取令牌
        //LOGGER.info("等待时间" + rateLimiter.acquire());
        // 2. 非阻塞式获取令牌
        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
            LOGGER.warn("你被限流了，真不幸，直接返回失败");
            return "你被限流了，真不幸，直接返回失败";
        }
        try {
            //int number = orderService.createOptimisticOrder(sid); 没事务
            int number = orderService.createPessimisticOrder(sid);
            LOGGER.info("购买成功，剩余库存为: [{}]", number);
            return String.format("购买成功，剩余库存为：%d", number);
        } catch (Exception e) {
            LOGGER.error("购买失败：[{}]", e.getMessage());
            return "购买失败，库存不足";
        }
    }
}