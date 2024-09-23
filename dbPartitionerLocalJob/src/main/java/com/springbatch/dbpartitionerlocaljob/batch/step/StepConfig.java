package com.springbatch.dbpartitionerlocaljob.batch.step;

import java.util.Objects;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

public abstract class StepConfig<T> {

    private final PlatformTransactionManager transactionManagerApp;
    private final JobRepository jobRepository;
    private final Integer gridSize;
    private final Integer chunkSize;
    private final String name;

    protected StepConfig(final String name, final JobRepository jobRepository,
            final PlatformTransactionManager transactionManagerApp,
            final Integer gridSize, final Integer chunkSize) {
        this.name = name;
        this.jobRepository = jobRepository;
        this.transactionManagerApp = transactionManagerApp;
        this.gridSize = Objects.isNull(gridSize) ? 10 : gridSize;
        this.chunkSize = Objects.isNull(chunkSize) ? 2000 : chunkSize;
    }

    public Step manager(
            final Partitioner pessoaPartitioner,
            final ItemReader<T> arquivoPessoaReader,
            final ItemWriter<T> pessoaWriter,
            final TaskExecutor taskExecutor) {
        return new StepBuilder(String.format("%sStepManager", name), jobRepository)
                .partitioner(String.format("%sStep.manager", name), pessoaPartitioner)
                .step(step(arquivoPessoaReader, pessoaWriter))
                .gridSize(this.gridSize)
                .taskExecutor(taskExecutor)
                .build();
    }

    private Step step(final ItemReader<T> reader, final ItemWriter<T> whiter) {
        return new StepBuilder(String.format("%sStep", this.name), this.jobRepository)
                .<T, T>chunk(this.chunkSize, this.transactionManagerApp)
                .reader(reader)
                .writer(whiter)
                .transactionManager(this.transactionManagerApp)
                .build();
    }

    public PlatformTransactionManager getTransactionManagerApp() {
        return this.transactionManagerApp;
    }

    public Integer getGridSize() {
        return this.gridSize;
    }

    public Integer getChunkSize() {
        return this.chunkSize;
    }

    public JobRepository getJobRepository() {
        return this.jobRepository;
    }

    public String getName() {
        return this.name;
    }
}
