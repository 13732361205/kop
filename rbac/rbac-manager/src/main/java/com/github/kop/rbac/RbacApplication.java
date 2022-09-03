package com.github.kop.rbac;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.github.kop.rbac.*"})
@EnableTransactionManagement
@MapperScan(basePackages = {"com.github.kop.rbac.repo.mapper"})
public class RbacApplication {
  private static final Logger logger = LoggerFactory.getLogger(RbacApplication.class);

  public static void main(String[] args) {
    ConfigurableApplicationContext run = SpringApplication.run(RbacApplication.class, args);
    logger.info("rbac项目启动成功");
  }
}
