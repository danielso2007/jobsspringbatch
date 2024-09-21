package com.springbatch.simplepartitionerlocal.infra.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArquivoPartitionerConfig implements Partitioner {

    @Value("${migracaodados.totalRegistros}")
    private Integer totalRegistros;

    @Value("${migracaodados.gridSize}")
    private Integer gridSize;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> map = new HashMap<>();
        for (int i = 0; i < gridSize; i++) {
            ExecutionContext context = new ExecutionContext();
            context.putInt("particao", i);
            map.put("partition" + i, context);
        }
        return map;
    }

    public int calcularPrimeiroItemLeitura(Integer particao) {
        Integer indexPrimeiroItem = (particao * (totalRegistros / gridSize));
        return indexPrimeiroItem;
    }

    public int getItensLimit() {
        return totalRegistros / gridSize;
    }

}
