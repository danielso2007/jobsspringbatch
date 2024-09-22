package com.springbatch.simplepartitionerarquivoslocaljob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimplePartitionerArquivosLocalJobApplication {

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(SimplePartitionerArquivosLocalJobApplication.class, args)));
    }
}
