package com.springbatch.parallelsteps.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class ParallelStepJobConfig {
	@Bean
	public Job migracaoDadosJob(final JobRepository jobRepository,
			@Qualifier("migrarPessoaStep") final Step migrarPessoaStep,
			@Qualifier("migrarDadosBancariosStep") final Step migrarDadosBancariosStep) {
		return new JobBuilder("parallelStepJob", jobRepository)
				.start(stepsParalelos(migrarPessoaStep, migrarDadosBancariosStep))
				.end()
				.incrementer(new RunIdIncrementer()).build();
		// Executar código abaixo para mostrar a velocidade inferior
		// return new JobBuilder("migracaoDadosJob", jobRepository)
		// .start(migrarPessoaStep)
		// .next(migrarDadosBancariosStep)
		// .incrementer(new RunIdIncrementer()).build();
	}

	private Flow stepsParalelos(final Step migrarPessoaStep, final Step migrarDadosBancariosStep) {
		return new FlowBuilder<Flow>("stepsParalelos").start(migrarPessoaFlow(migrarPessoaStep))
				.split(new SimpleAsyncTaskExecutor())
				.add(migrarDadosBancariosFlow(migrarDadosBancariosStep)).build();
	}

	private Flow migrarPessoaFlow(Step migrarPessoaStep) {
		return new FlowBuilder<Flow>("migrarPessoaFlow").start(migrarPessoaStep).build();
	}

	private Flow migrarDadosBancariosFlow(Step migrarDadosBancariosStep) {
		return new FlowBuilder<Flow>("migrarDadosBancariosFlow").start(migrarDadosBancariosStep).build();
	}
}