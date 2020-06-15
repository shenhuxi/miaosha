package com.zpself.module.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zpself.module.entity.Stock;

public interface StockMapper extends BaseMapper<Stock> {

    int deleteByPrimaryKey(Integer id);

    int insert(Stock record);

    int insertSelective(Stock record);

    Stock selectByPrimaryKey(Integer id);

    void selectByPrimaryKey2(Integer id);

    Stock selectByPrimaryKeyForUpdate(Integer id);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);

    int updateByOptimistic(Stock record);
}