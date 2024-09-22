package com.springbatch.simplepartitionerarquivoslocaljob.infra.config;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MultiResourcesPartitionerConfig {

    @Bean
    @StepScope
    public MultiResourcePartitioner pessoaPartitioner(@Value("file:files/pessoa-tmp*") Resource[] resources) {
        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
        partitioner.setKeyName("file");
        partitioner.setResources(resources);
        return partitioner;
    }

    @Bean
    @StepScope
    public MultiResourcePartitioner dadosBancariosPartitioner(
            @Value("file:files/dados_bancarios-tmp*") Resource[] resourcesFiles) {
        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
        partitioner.setKeyName("file");
        partitioner.setResources(resourcesFiles);
        return partitioner;
    }

}
