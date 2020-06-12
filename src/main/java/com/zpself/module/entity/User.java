package com.zpself.module.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName
public class User implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    private String name;

    private int age;

    @TableField(fill = FieldFill.INSERT_UPDATE,update = "CONCAT(%s,'*')")
    private String email;

    @TableLogic
    private Integer deleted=0;

}
