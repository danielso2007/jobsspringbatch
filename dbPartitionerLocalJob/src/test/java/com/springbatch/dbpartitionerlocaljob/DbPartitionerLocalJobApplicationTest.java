package com.springbatch.dbpartitionerlocaljob;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DbPartitionerLocalJobApplicationTest extends AbstractIntegrationTest {
    @Test
    void contextLoads() {
        assertThat(true).isTrue();
    }
}
