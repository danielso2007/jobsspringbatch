package com.springbatch.bdremotepartitioner.batch.job;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.integration.partition.RemotePartitioningManagerStepBuilder;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.jms.dsl.Jms;

@Profile("manager")
@Configuration
@EnableBatchIntegration
public class ManagerConfig {

    private final JobRepository jobRepository;
    private final JobExplorer jobExplorer;
    private final BeanFactory beanFactory;
    private static final int GRID_SIZE = 2;


    public ManagerConfig(JobRepository jobRepository, JobExplorer jobExplorer,
            BeanFactory beanFactory) {
        this.jobRepository = jobRepository;
        this.jobExplorer = jobExplorer;
        this.beanFactory = beanFactory;
    }

    @Bean
    public Job remotePartitioningManagerJob(
            @Qualifier("migrarPessoaStep") final Step migrarPessoaStep,
            @Qualifier("migrarDadosBancariosStep") final Step migrarDadosBancariosStep) {
        return new JobBuilder("managerJob", jobRepository).start(migrarPessoaStep)
                .next(migrarDadosBancariosStep).incrementer(new RunIdIncrementer()).build();
    }

    @Bean
    public Step migrarPessoaStep(
            @Qualifier("pessoaPartitioner") final Partitioner pessoaPartitioner) {
        return new RemotePartitioningManagerStepBuilder("migrarPessoaStep", jobRepository)
                .partitioner("migrarPessoaStep", pessoaPartitioner).gridSize(GRID_SIZE)
                .jobExplorer(jobExplorer).beanFactory(beanFactory).outputChannel(request()).build();
    }

    @Bean
    public Step migrarDadosBancariosStep(
            @Qualifier("dadosBancariosPartitioner") final Partitioner dadosBancariosPartitioner) {
        return new RemotePartitioningManagerStepBuilder("migrarDadosBancariosStep", jobRepository)
                .partitioner("migrarDadosBancariosStep", dadosBancariosPartitioner)
                .gridSize(GRID_SIZE).jobExplorer(jobExplorer).beanFactory(beanFactory)
                .outputChannel(request()).build();
    }

    @Bean
    public IntegrationFlow outboundFlow(ActiveMQConnectionFactory connectionFactory) {
        return IntegrationFlow.from(request())
                .handle(Jms.outboundAdapter(connectionFactory).destination("requests")).get();
    }

    @Bean
    public DirectChannel request() {
        return new DirectChannel();
    }

}
