package com.zpself.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName
public class Stock {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer count;

    private Integer sale;

    private Integer version;
}