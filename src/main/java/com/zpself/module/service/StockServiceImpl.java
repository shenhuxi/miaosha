package com.zpself.module.service;

import com.zpself.module.entity.Stock;
import com.zpself.module.repository.StockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockServiceImpl.class);

    @Autowired
    private StockMapper stockMapper;

//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;

    @Override
    public int getStockCountByDB(int id) {
        Stock stock = stockMapper.selectByPrimaryKey(id);
        return stock.getCount();
    }

    @Override
    public Integer getStockCountByCache(int id) {
//        String hashKey = CacheKey.STOCK_COUNT.getKey() + "_" + id;
//        String countStr = stringRedisTemplate.opsForValue().get(hashKey);
//        if (countStr != null) {
//            return Integer.parseInt(countStr);
//        } else {
//            return null;
//        }
        return null;
    }

    @Override
    public void setStockCountCache(int id, int count) {
//        String hashKey = CacheKey.STOCK_COUNT.getKey() + "_" + id;
//        String countStr = String.valueOf(count);
//        stringRedisTemplate.opsForValue().set(hashKey, countStr, 3600, TimeUnit.SECONDS);
    }

    @Override
    public void delStockCountCache(int id) {
//        String hashKey = CacheKey.STOCK_COUNT.getKey() + "_" + id;
//        stringRedisTemplate.delete(hashKey);
        LOGGER.info("删除商品id：[{}] 缓存", id);
    }

    @Override
    public Stock getStockById(int id) {
        stockMapper.selectById(id);
        //stockMapper.selectByPrimaryKey2(id);
        return stockMapper.selectByPrimaryKey(id);
    }

    @Override
    public Stock getStockByIdForUpdate(int id) {
        return stockMapper.selectByPrimaryKeyForUpdate(id);
    }

    @Override
    public int updateStockById(Stock stock) {
        return stockMapper.updateByPrimaryKeySelective(stock);
    }

    @Override
    public int updateStockByOptimistic(Stock stock) {
        return stockMapper.updateByOptimistic(stock);
    }
}
