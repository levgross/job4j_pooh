package ru.job4j.pooh;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class TopicServiceTest {
    @Test
    public void whenTopic() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Resp resultPost = topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher)
        );
        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        Resp resultPostNew = topicService.process(
                new Req("POST", "topic", "weather1", paramForPublisher)
        );
        Resp result3 = topicService.process(
                new Req("GET", "topic", "weather1", paramForSubscriber1)
        );
        assertThat(result1.text()).isEqualTo("temperature=18");
        assertThat(result1.status()).isEqualTo("200");
        assertThat(result2.text()).isEqualTo("");
        assertThat(result2.status()).isEqualTo("204");
        assertThat(resultPost.text()).isEqualTo("");
        assertThat(resultPost.status()).isEqualTo("201");
        assertThat(resultPostNew.text()).isEqualTo("");
        assertThat(resultPostNew.status()).isEqualTo("201");
        assertThat(result3.text()).isEqualTo("");
        assertThat(result3.status()).isEqualTo("204");
    }
}
