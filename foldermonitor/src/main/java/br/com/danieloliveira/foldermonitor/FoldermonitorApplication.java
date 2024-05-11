package br.com.danieloliveira.foldermonitor;

import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Calendar;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class FoldermonitorApplication {
    private final JobLauncher jobLauncher;
    private final Job job;

    @Value("${foldermonitor.local}")
    private String local;

    public static void main(String[] args) {
        new SpringApplicationBuilder(FoldermonitorApplication.class)
            .web(WebApplicationType.SERVLET)
            .run(args)
            .registerShutdownHook();
    }

    public void run(final String inputFile) throws Exception {
        val jobParameters = new JobParametersBuilder()
            .addDate("timestamp", Calendar.getInstance().getTime())
            .addString("inputFile", inputFile).toJobParameters();
        val jobExecution = jobLauncher.run(job, jobParameters);
        while (jobExecution.isRunning()) {
            log.info("..................");
        }
    }

    @Scheduled(fixedRate = 2000)
    @SuppressFBWarnings(value = "PATH_TRAVERSAL_IN", justification = "É preciso passar o parâmetro do Paths")
    public void runJob() {
        val path = Paths.get(local);
        WatchKey key;
        WatchService watchService = null;
        try {
            watchService = FileSystems.getDefault().newWatchService();
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    log.info("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
                    if (event.kind().name().equals("ENTRY_CREATE")) {
                        run(path + "/" + event.context().toString());
                    }
                }
                key.reset();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
