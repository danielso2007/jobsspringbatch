package com.springbatch.migracaodedados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MigracaoDeDadosJobApplication {

	public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(MigracaoDeDadosJobApplication.class, args)));
	}

}
