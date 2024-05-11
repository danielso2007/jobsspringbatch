package br.com.danieloliveira.batchprocessing.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import br.com.danieloliveira.batchprocessing.batch.listener.JobCompletionNotificationListener;

@Configuration
public class BatchConfiguration {
    @Bean
    public Job importUserJob(JobRepository jobRepository, Step personStep, JobCompletionNotificationListener listener) {
        return new JobBuilder("importUserJob", jobRepository).listener(listener).start(personStep).build();
    }
}
