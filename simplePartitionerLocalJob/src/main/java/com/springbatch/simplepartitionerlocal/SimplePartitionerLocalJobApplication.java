package com.springbatch.simplepartitionerlocal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimplePartitionerLocalJobApplication {

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(SimplePartitionerLocalJobApplication.class, args)));
    }
}
