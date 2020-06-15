
package com.zpself.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@Accessors(chain = true)
@TableName
public class StockOrder {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer sid;

    private String name;

    private Integer userId;

    private Date createTime;
}