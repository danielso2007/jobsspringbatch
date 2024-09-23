package com.springbatch.bdremotepartitioner.batch.job;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.integration.partition.RemotePartitioningWorkerStepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.transaction.PlatformTransactionManager;
import com.springbatch.bdremotepartitioner.core.dominio.DadosBancarios;
import com.springbatch.bdremotepartitioner.core.dominio.Pessoa;

@Profile("worker")
@Configuration
@EnableBatchIntegration
public class WorkerConfig {

    private final PlatformTransactionManager transactionManagerApp;
    private final JobRepository jobRepository;
    private final JobExplorer jobExplorer;
    private final BeanFactory beanFactory;
    private static final int CHUNK_SIZE = 10000;

    public WorkerConfig(final JobRepository jobRepository, final JobExplorer jobExplorer,
            @Qualifier("transactionManagerApp") final PlatformTransactionManager transactionManagerApp,
            final BeanFactory beanFactory) {
        this.jobRepository = jobRepository;
        this.jobExplorer = jobExplorer;
        this.transactionManagerApp = transactionManagerApp;
        this.beanFactory = beanFactory;
    }

    // @Bean
    // public Job remotePartitioningWorkerJob(
    //         @Qualifier("migrarPessoaStep") final Step migrarPessoaStep,
    //         @Qualifier("migrarDadosBancariosStep") final Step migrarDadosBancariosStep) {
    //     return new JobBuilder("workerJob", jobRepository)
    //             .start(migrarPessoaStep).next(migrarDadosBancariosStep)
    //             .incrementer(new RunIdIncrementer()).build();
    // }

    @Bean
    public Step migrarPessoaStep(
            @Qualifier("pessoaReader") JdbcPagingItemReader<Pessoa> pessoaReader,
            @Qualifier("pessoaWriter") JdbcBatchItemWriter<Pessoa> pessoaWriter) {
        return new RemotePartitioningWorkerStepBuilder("migrarPessoaStep", jobRepository)
                .jobExplorer(jobExplorer).beanFactory(beanFactory).inputChannel(request())
                .<Pessoa, Pessoa>chunk(CHUNK_SIZE, transactionManagerApp).reader(pessoaReader)
                .writer(pessoaWriter).transactionManager(transactionManagerApp).build();
    }

    @Bean
    public Step migrarDadosBancariosStep(
            JdbcPagingItemReader<DadosBancarios> dadosBancariosReader,
            JdbcBatchItemWriter<DadosBancarios> dadosBancariosWriter) {
        return new RemotePartitioningWorkerStepBuilder("migrarDadosBancariosStep",
                jobRepository).jobExplorer(jobExplorer).beanFactory(beanFactory).inputChannel(request())
                        .<DadosBancarios, DadosBancarios>chunk(CHUNK_SIZE, transactionManagerApp)
                        .reader(dadosBancariosReader).writer(dadosBancariosWriter)
                        .transactionManager(transactionManagerApp).build();
    }

    @Bean
    public IntegrationFlow inboundFlow(ActiveMQConnectionFactory connectionFactory) {
        return IntegrationFlow
                .from(Jms.messageDrivenChannelAdapter(connectionFactory).destination("requests"))
                .channel(request()).get();
    }

    @Bean
    public DirectChannel request() {
        return new DirectChannel();
    }

}
