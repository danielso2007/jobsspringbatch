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
        ActiveMQConnectionFactory activeMqConnectionFactory = new ActiveMQConnectionFactory();
        activeMqConnectionFactory.setBrokerURL(this.brokerUrl);
        activeMqConnectionFactory.setUserName(this.username);
        activeMqConnectionFactory.setPassword(this.password);
        activeMqConnectionFactory.setTrustAllPackages(true);
        return activeMqConnectionFactory;
    }

}
