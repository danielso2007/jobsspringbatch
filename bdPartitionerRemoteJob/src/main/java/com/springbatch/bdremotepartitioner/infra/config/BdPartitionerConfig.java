package com.springbatch.bdremotepartitioner.infra.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BdPartitionerConfig {

    private final DataSource dataSource;

    public BdPartitionerConfig(@Qualifier("appDataSource") final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public ColumnRangePartitioner pessoaPartitioner() {
        ColumnRangePartitioner partitioner = new ColumnRangePartitioner();
        partitioner.setTable("pessoa_origem");
        partitioner.setColumn("id");
        partitioner.setDataSource(dataSource);
        return partitioner;
    }

    @Bean
    public ColumnRangePartitioner dadosBancariosPartitioner() {
        ColumnRangePartitioner partitioner = new ColumnRangePartitioner();
        partitioner.setTable("dados_bancarios_origem");
        partitioner.setColumn("id");
        partitioner.setDataSource(dataSource);
        return partitioner;
    }
}
