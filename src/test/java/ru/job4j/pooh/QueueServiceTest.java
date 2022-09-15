package ru.job4j.pooh;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class QueueServiceTest {
    @Test
    public void whenPostThenGetQueue() {
        Service queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        Resp resultPost = queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod)
        );
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        Resp result1 = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text()).isEqualTo("temperature=18");
        assertThat(result.status()).isEqualTo("200");
        assertThat(result1.text()).isEqualTo("");
        assertThat(result1.status()).isEqualTo("204");
        assertThat(resultPost.text()).isEqualTo("");
        assertThat(resultPost.status()).isEqualTo("201");
    }
}
