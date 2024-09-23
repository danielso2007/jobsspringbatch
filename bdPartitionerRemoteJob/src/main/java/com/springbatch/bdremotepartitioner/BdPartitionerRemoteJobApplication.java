package com.springbatch.bdremotepartitioner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BdPartitionerRemoteJobApplication {

    public static void main(String[] args) {
        System.exit(SpringApplication
                .exit(SpringApplication.run(BdPartitionerRemoteJobApplication.class, args)));
    }

}
