package com.springbatch.parallelsteps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParallelStepsJobApplication {
    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(ParallelStepsJobApplication.class, args)));
    }
}
