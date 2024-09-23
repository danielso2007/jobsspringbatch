package com.springbatch.dbpartitionerlocaljob.batch.reader.impl;

import javax.sql.DataSource;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.springbatch.dbpartitionerlocaljob.batch.reader.ReaderConfig;
import com.springbatch.dbpartitionerlocaljob.code.dominio.DadosBancarios;

@Configuration
public class BancoDadosBancariosReaderConfig extends ReaderConfig<DadosBancarios> {

    protected BancoDadosBancariosReaderConfig(@Qualifier("appDataSource") final DataSource dataSource,
            @Qualifier("queryProviderDadosBancarios") final PagingQueryProvider pagingQueryProvider) {
        super(dataSource, 2000, "dadosBancarios", pagingQueryProvider, "*", "FROM dados_bancarios_origem", "id",
                "WHERE id >= %d AND id <= %d");
    }

    @StepScope
    @Bean
    public JdbcPagingItemReader<DadosBancarios> dadosBancariosReader() {
        return reader();
    }

    @StepScope
    @Bean
    public SqlPagingQueryProviderFactoryBean queryProviderDadosBancarios(
            @Value("#{stepExecutionContext['minValue']}") final Long minValue,
            @Value("#{stepExecutionContext['maxValue']}") final Long maxValue) {
        return queryProvider(minValue, maxValue);
    }

}
