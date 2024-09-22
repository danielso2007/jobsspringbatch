package com.springbatch.dbpartitionerlocaljob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DbPartitionerLocalJobApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(DbPartitionerLocalJobApplication.class, args);
    context.close();
  }

}
