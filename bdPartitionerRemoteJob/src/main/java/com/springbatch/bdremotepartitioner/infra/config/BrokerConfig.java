package com.springbatch.bdremotepartitioner.infra.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfig {

    @Value("${broker.url}")
    private String brokerUrl;
    @Value("${broker.username}")
    private String username;
    @Value("${broker.password}")
    private String password;

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(this.brokerUrl);
        activeMQConnectionFactory.setUserName(this.username);
        activeMQConnectionFactory.setPassword(this.password);
        activeMQConnectionFactory.setTrustAllPackages(true);
        return activeMQConnectionFactory;
    }

}
