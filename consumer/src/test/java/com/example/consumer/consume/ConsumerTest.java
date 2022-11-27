package com.example.consumer.consume;

import com.example.consumer.service.ConsumerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"local"})
public class ConsumerTest {

    @Autowired
    ConsumerService consumerService;

    @Test
    public void consumeTest() {
        Long wordCount = consumerService.countWord("word");
        Assertions.assertThat(wordCount).isNotNull();
    }
}
