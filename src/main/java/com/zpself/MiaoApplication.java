package com.zpself;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zengpeng on 2019/6/12
 */
@SpringBootApplication
@MapperScan("com.zpself.module.repository")
public class MiaoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MiaoApplication.class, args);
    }
}
