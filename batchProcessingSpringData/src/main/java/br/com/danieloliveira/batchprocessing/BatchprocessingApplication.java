package br.com.danieloliveira.batchprocessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "br.com.danieloliveira.batchprocessing.core.repository")
@SpringBootApplication
public class BatchprocessingApplication {
    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(BatchprocessingApplication.class, args)));
    }
}
