package com.example.consumer.service;

import com.example.consumer.dto.Stock;
import com.example.consumer.utils.JsonUtil;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ConsumerService {

    @Value("${kafka.topic}")
    private String kafkaTopic;

    private static final Serde<String> STRING_SERDE = Serdes.String();

    @Autowired
    public void buildPipeline(StreamsBuilder streamsBuilder) {
        KStream<String, String> messageStream = streamsBuilder
               .stream(kafkaTopic, Consumed.with(STRING_SERDE, STRING_SERDE));

        messageStream
                .foreach((key, value) -> System.out.println(removeNonWords(value)));
//                .flatMapValues(value -> Arrays.asList(value.split(" ")))
//                .foreach((key, value) -> System.out.println(key + " : " + value));
                //.to("test2");

//       KTable<String, Long> wordCounts = messageStream
//            .mapValues((ValueMapper<String, String>) String::toLowerCase)
//            .flatMapValues(value -> Arrays.asList(value.split("\\W+")))
//            .groupBy((key, word) -> word, Grouped.with(STRING_SERDE, STRING_SERDE))
//            .count(Materialized.as("counts"));
//
//        wordCounts.toStream()
//            .foreach((word, count) -> System.out.println("word : " + word + " => count : " + count));
            //.to("output-topic");
    }

    @Autowired
    private StreamsBuilderFactoryBean factoryBean;

    // 애초에 produce를 % 제거하고 하는게 좋으나 테스트 용도..
    private String removeNonWords(String value) {
        Stock stock = JsonUtil.convertStringToObject(value.replaceAll("%", ""), Stock.class);
        return JsonUtil.convertObjectToString(stock);
    }

    public Long countWord(String word) {
        KafkaStreams kafkaStreams =  factoryBean.getKafkaStreams();

        // Cannot get state store counts because the stream thread is STARTING, not RUNNING
        ReadOnlyKeyValueStore<String, Long> counts = kafkaStreams
                .store(StoreQueryParameters.fromNameAndType("counts", QueryableStoreTypes.keyValueStore()));
        Long wordCount = counts.get(word);
        return wordCount;
    }
}
