package com.frank.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author 小石潭记
 * @date 2020/6/26 12:42
 * @Description: ${todo}
 */
@SpringBootApplication
@EnableCaching
@MapperScan(basePackages = {"com.frank.cache"})
public class RedisSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisSpringbootApplication.class, args);
    }
}
