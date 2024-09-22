package com.springbatch.dbpartitionerlocaljob.infra.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.dbpartitionerlocaljob.batch.partitioner.ColumnRangePartitioner;

@Configuration
public class DbPartitionerConfig {

    @Bean
    public ColumnRangePartitioner pessoaPartitioner(@Qualifier("appDataSource") DataSource dataSource) {
        ColumnRangePartitioner partitioner = new ColumnRangePartitioner();
        partitioner.setTable("pessoa_origem");
        partitioner.setColumn("id");
        partitioner.setDataSource(dataSource);
        return partitioner;
    }

    @Bean
    public ColumnRangePartitioner dadosBancariosPartitioner(@Qualifier("appDataSource") DataSource dataSource) {
        ColumnRangePartitioner partitioner = new ColumnRangePartitioner();
        partitioner.setTable("dados_bancarios_origem");
        partitioner.setColumn("id");
        partitioner.setDataSource(dataSource);
        return partitioner;
    }

}
