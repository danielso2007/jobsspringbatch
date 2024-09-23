package com.springbatch.dbpartitionerlocaljob.batch.writer;

import javax.sql.DataSource;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;

public interface WriterConfig<T> {

    default JdbcBatchItemWriter<T> writer(final DataSource dataSource, final String query) {
        return new JdbcBatchItemWriterBuilder<T>()
                .dataSource(dataSource)
                .sql(query)
                .beanMapped()
                .build();
    }

}
