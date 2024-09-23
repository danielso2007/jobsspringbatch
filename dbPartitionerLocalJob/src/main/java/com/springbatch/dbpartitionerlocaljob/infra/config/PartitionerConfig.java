package com.springbatch.dbpartitionerlocaljob.infra.config;

import javax.sql.DataSource;
import com.springbatch.dbpartitionerlocaljob.batch.partitioner.ColumnRangePartitioner;

public interface PartitionerConfig {

    default ColumnRangePartitioner partitioner(final DataSource dataSource, final String table, final String column) {
        ColumnRangePartitioner partitioner = new ColumnRangePartitioner();
        partitioner.setTable(table);
        partitioner.setColumn(column);
        partitioner.setDataSource(dataSource);
        return partitioner;
    }

}
