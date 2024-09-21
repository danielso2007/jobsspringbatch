package com.springbatch.simplepartitionerlocal.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import com.springbatch.simplepartitionerlocal.code.dominio.Pessoa;

@Component
public class PessoaProcessor implements ItemProcessor<Pessoa, Pessoa> {
    private static final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Pessoa process(@SuppressWarnings("null") Pessoa pessoa) throws Exception {
        if (pessoa.getId() < 12) {
            try {
                String uri = String.format("https://my-json-server.typicode.com/danielso2007/demo/pessoas/%d",
                        pessoa.getId());
                // System.out.println(uri);
                ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
                System.out.println(response.getStatusCode());
            } catch (RestClientResponseException e) {
                System.out.println(e.getMessage());
            }
        }
        return pessoa;
    }
}