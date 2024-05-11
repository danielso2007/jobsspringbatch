package br.com.danieloliveira.batchprocessing.batch.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import br.com.danieloliveira.batchprocessing.core.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobCompletionNotificationListener implements JobExecutionListener {
    private final PersonRepository personRepository;

    @Transactional(readOnly = true)
    @Override
    public void afterJob(@SuppressWarnings("null") JobExecution jobExecution) {
        log.info("Chamou JobCompletionNotificationListener");
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! TRABALHO FINALIZADO! Hora de verificar os resultados");
            log.info("Resultado final: {}", personRepository.count());
        }
    }
}
