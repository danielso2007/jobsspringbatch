package com.springbatch.dbpartitionerlocaljob.batch.partitioner;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Particionador de mente simples para um intervalo de valores de uma coluna em
 * uma tabela de banco de dados. Funciona melhor se os valores forem
 * distribuídos uniformemente (por exemplo, valores de chave primária gerados
 * automaticamente).
 *
 * @author Dave Syer
 *
 */
public class ColumnRangePartitioner implements Partitioner {

    private JdbcOperations jdbcTemplate;
    private String table;
    private String column;

    /**
     * O nome da tabela SQL onde os dados estão.
     *
     * @param table the name of the table
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * O nome da coluna a ser particionada.
     *
     * @param column the column name.
     */
    public void setColumn(String column) {
        this.column = column;
    }

    /**
     * A fonte de dados para conexão ao banco de dados.
     *
     * @param dataSource a {@link DataSource}
     */
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Particione uma tabela de banco de dados assumindo que os dados na coluna
     * especificada são distribuídos uniformemente. Os valores do contexto de
     * execução terão chaves <code>minValue</code> e <code>maxValue</code>
     * especificando o intervalo de valores a serem considerados em cada partição.
     *
     * @see Partitioner#partition(int)
     */
    @SuppressWarnings("null")
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        int min = jdbcTemplate.queryForObject(String.format("SELECT MIN(%s) from %s", column, table), Integer.class);
        int max = jdbcTemplate.queryForObject(String.format("SELECT MAX(%s) from %s", column, table), Integer.class);
        int targetSize = (max - min) / gridSize + 1;

        Map<String, ExecutionContext> result = new HashMap<>();
        int number = 0;
        int start = min;
        int end = start + targetSize - 1;

        while (start <= max) {
            ExecutionContext value = new ExecutionContext();
            result.put("partition" + number, value);

            if (end >= max) {
                end = max;
            }
            value.putInt("minValue", start);
            value.putInt("maxValue", end);
            start += targetSize;
            end += targetSize;
            number++;
        }

        return result;
    }

}