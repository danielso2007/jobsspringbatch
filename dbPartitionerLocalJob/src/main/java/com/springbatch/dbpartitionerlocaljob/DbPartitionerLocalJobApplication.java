package com.springbatch.dbpartitionerlocaljob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DbPartitionerLocalJobApplication {

  public static void main(String[] args) {
    System.exit(SpringApplication.exit(SpringApplication.run(DbPartitionerLocalJobApplication.class, args)));
  }

}
