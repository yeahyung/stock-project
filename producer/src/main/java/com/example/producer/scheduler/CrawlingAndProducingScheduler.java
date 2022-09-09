package com.example.producer.scheduler;

import com.example.producer.service.CrawlingService;
import com.example.producer.service.ProducingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CrawlingAndProducingScheduler {

    private final CrawlingService crawlingService;
    private final ProducingService producingService;

    // 초 분 시 일 월 요
    // 월~금 9시~18시 1분마다
    @Scheduled(cron = "0 * 9-18 * * MON-FRI")
    public void crawlingScheduler() {
        try{
            producingService.produceNAVERFinanceCrawlingResult(
                    crawlingService.getNAVERFinanceCrawlingResult());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("fail " + e);
        }
    }
}
