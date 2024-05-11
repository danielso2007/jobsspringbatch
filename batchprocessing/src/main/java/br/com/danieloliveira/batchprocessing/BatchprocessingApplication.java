package br.com.danieloliveira.batchprocessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BatchprocessingApplication {
    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(BatchprocessingApplication.class, args)));
    }
}
