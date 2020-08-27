package com.zxw.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 用于配置需要动态生成的mapper接口的路径
 */
@Configuration
@MapperScan("com.zxw.mbg.mapper")
public class MyBatisConfig {

}
