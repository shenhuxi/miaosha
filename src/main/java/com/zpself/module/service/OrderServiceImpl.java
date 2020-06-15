package com.zpself.module.service;

import com.zpself.module.entity.Stock;
import com.zpself.module.entity.StockOrder;
import com.zpself.module.entity.User;
import com.zpself.module.repository.StockOrderMapper;
import com.zpself.module.repository.SystemUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);


    @Autowired
    private StockService stockService;

    @Autowired
    private StockOrderMapper stockOrderMapper;

    @Autowired
    private SystemUserMapper userMapper;

    @Override
    public int createWrongOrder(int sid) {
        //校验库存
        Stock stock = checkStock(sid);
        //扣库存
        saleStock(stock);
        //创建订单
        int id = createOrder(stock);
        return id;
    }

    @Override
    public int createOptimisticOrder(int sid) {
        //校验库存
        Stock stock = checkStock(sid);
        //乐观锁更新库存
        saleStockOptimistic(stock);
        //创建订单
        createOrder(stock);
        return stock.getCount() - (stock.getSale()+1);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public int createPessimisticOrder(int sid){
        //校验库存(悲观锁for update)
        Stock stock = checkStockForUpdate(sid);
        //更新库存
        saleStock(stock);
        //创建订单
        createOrder(stock);
        return stock.getCount() - (stock.getSale());
    }

    @Override
    public int createVerifiedOrder(Integer sid, Integer userId, String verifyHash) throws Exception {

        // 验证是否在抢购时间内
        LOGGER.info("请自行验证是否在抢购时间内,假设此处验证成功");

        // 验证hash值合法性
//        String hashKey = CacheKey.HASH_KEY.getKey() + "_" + sid + "_" + userId;
//        System.out.println(hashKey);
//        String verifyHashInRedis = stringRedisTemplate.opsForValue().get(hashKey);
//        if (!verifyHash.equals(verifyHashInRedis)) {
//            throw new Exception("hash值与Redis中不符合");
//        }
//        LOGGER.info("验证hash值合法性成功");

        // 检查用户合法性
        User user = userMapper.selectById(userId.longValue());
        if (user == null) {
            throw new Exception("用户不存在");
        }
        LOGGER.info("用户信息验证成功：[{}]", user.toString());

        // 检查商品合法性
        Stock stock = stockService.getStockById(sid);
        if (stock == null) {
            throw new Exception("商品不存在");
        }
        LOGGER.info("商品信息验证成功：[{}]", stock.toString());

        //乐观锁更新库存
        saleStockOptimistic(stock);
        LOGGER.info("乐观锁更新库存成功");

        //创建订单
        createOrderWithUserInfo(stock, userId);
        LOGGER.info("创建订单成功");

        return stock.getCount() - (stock.getSale()+1);
    }

    /**
     * 检查库存
     * @param sid
     * @return
     */
    private Stock checkStock(int sid) {
        User user = userMapper.selectById(sid);
        Stock stock = stockService.getStockById(sid);
        if (stock.getSale().equals(stock.getCount())) {
            throw new RuntimeException("库存不足");
        }
        return stock;
    }

    /**
     * 检查库存 ForUpdate
     * @param sid
     * @return
     */
    private Stock checkStockForUpdate(int sid) {
        Stock stock = stockService.getStockByIdForUpdate(sid);
        if (stock.getSale().equals(stock.getCount())) {
            throw new RuntimeException("库存不足");
        }
        return stock;
    }

    /**
     * 更新库存
     * @param stock
     */
    private void saleStock(Stock stock) {
        stock.setSale(stock.getSale() + 1);
        stockService.updateStockById(stock);
    }

    /**
     * 更新库存 乐观锁
     * @param stock
     */
    private void saleStockOptimistic(Stock stock) {
        LOGGER.info("查询数据库，尝试更新库存");
        int count = stockService.updateStockByOptimistic(stock);
        if (count == 0){
            throw new RuntimeException("乐观锁并发更新库存失败") ;
        }
    }

    /**
     * 创建订单
     * @param stock
     * @return
     */
    private int createOrder(Stock stock) {
        StockOrder order = new StockOrder();
        order.setSid(stock.getId());
        order.setName(stock.getName());
        int id = stockOrderMapper.insert(order);
        return id;
    }

    /**
     * 创建订单：保存用户信息
     * @param stock
     * @return
     */
    private int createOrderWithUserInfo(Stock stock, Integer userId) {
        StockOrder order = new StockOrder();
        order.setSid(stock.getId());
        order.setName(stock.getName());
        order.setUserId(userId);
        return 0;//orderMapper.insertSelective(order);
    }
}
