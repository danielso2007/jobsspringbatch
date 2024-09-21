package com.springbatch.simplepartitionerlocal;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(classes = SimplePartitionerLocalJobApplicationTests.class, webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
        "spring.datasource.url=jdbc:tc:postgres:16.2-alpine3.19:///migracao_dados2" })
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {

    static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16.2-alpine3.19")
            .withDatabaseName("migracao_dados2").withUsername("postgres").withPassword("postgres");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        postgresContainer.start();
        registry.add("DB_HOST", postgresContainer::getHost);
        registry.add("DB_PORT", postgresContainer::getFirstMappedPort);
        registry.add("DB_USER", postgresContainer::getUsername);
        registry.add("DB_PASSWORD", postgresContainer::getPassword);
        registry.add("DB_HOST_APP", postgresContainer::getHost);
        registry.add("DB_PORT_APP", postgresContainer::getFirstMappedPort);
        registry.add("DB_USER_APP", postgresContainer::getUsername);
        registry.add("DB_PASSWORD_APP", postgresContainer::getPassword);
        registry.add("DATA_BASE", postgresContainer::getDatabaseName);
        registry.add("DATA_BASE_APP", postgresContainer::getDatabaseName);
    }
}
