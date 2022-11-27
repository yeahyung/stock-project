package com.example.producer.service;

import com.example.producer.dto.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProducingService {

    @Value("${kafka.topic}")
    private String kafkaTopic;

    private final KafkaTemplate<String, Stock> kafkaTemplate;

    @Autowired
    public ProducingService(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public Boolean produceNAVERFinanceCrawlingResult(List<Stock> stockList) {
        return produce(stockList);
    }

    public Boolean produce(List<Stock> stockList) {
        try {
            for (Stock stock : stockList) {
                kafkaTemplate.send(kafkaTopic, stock);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
