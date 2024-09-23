package com.springbatch.dbpartitionerlocaljob.infra.config.impl;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.springbatch.dbpartitionerlocaljob.batch.partitioner.ColumnRangePartitioner;
import com.springbatch.dbpartitionerlocaljob.infra.config.PartitionerConfig;

@Configuration
public class PessoaPartitionerImpl implements PartitionerConfig {

    @Bean
    public ColumnRangePartitioner pessoaPartitioner(@Qualifier("appDataSource") DataSource dataSource) {
        return partitioner(dataSource, "pessoa_origem", "id");
    }

}
