package com.springbatch.dbpartitionerlocaljob.infra.config.impl;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.springbatch.dbpartitionerlocaljob.batch.partitioner.ColumnRangePartitioner;
import com.springbatch.dbpartitionerlocaljob.infra.config.PartitionerConfig;

@Configuration
public class DadosBancariosPartitionerImpl implements PartitionerConfig {

    @Bean
    public ColumnRangePartitioner dadosBancariosPartitioner(@Qualifier("appDataSource") DataSource dataSource) {
        return partitioner(dataSource, "dados_bancarios_origem", "id");
    }

}
