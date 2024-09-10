package com.springbatch.processamentoassincrono.batch.processor;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.springbatch.processamentoassincrono.code.dominio.Pessoa;

@Configuration
public class PessoaProcessorConfig {
    private static final RestTemplate restTemplate = new RestTemplate();

    @Bean
    public AsyncItemProcessor<Pessoa, Pessoa> asyncPessoaProcessor() {
        AsyncItemProcessor<Pessoa, Pessoa> processor = new AsyncItemProcessor<>();
        processor.setDelegate(pessoaProcessor());
        processor.setTaskExecutor(taskExecutor());
        return processor;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(6);
        executor.setMaxPoolSize(6);
        executor.setQueueCapacity(8);
        executor.setQueueCapacity(8);
        executor.setThreadNamePrefix("MultiThreaded-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    public ItemProcessor<Pessoa, Pessoa> pessoaProcessor() {
        return new ItemProcessor<Pessoa, Pessoa>() {
            @Override
            @Nullable
            public Pessoa process(@NonNull Pessoa pessoa) throws Exception {
                if (pessoa.getId() < 12) {
                    try {
                        String uri = String.format("https://my-json-server.typicode.com/danielso2007/demo/pessoas/%d",
                                pessoa.getId());
                        // System.out.println(uri);
                        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
                    } catch (RestClientResponseException e) {
                        System.out.println(e.getMessage());
                    }
                }
                return pessoa;
            }
        };
    }
}
