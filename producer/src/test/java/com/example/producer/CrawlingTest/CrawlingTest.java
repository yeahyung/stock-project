package com.example.producer.CrawlingTest;

import com.example.producer.dto.Stock;
import com.example.producer.service.CrawlingService;
import org.assertj.core.api.Assertions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

public class CrawlingTest {

    @Test
    public void convertHtmlToStockObject() {
        try{
            Document doc = Jsoup.parse(new File("src/test/java/com/example/producer/CrawlingTest/naver_finance.html"));
            Elements elements = doc.getElementsByClass("type_5").select("tbody tr");

            CrawlingService crawlingService = new CrawlingService();
            Method method = crawlingService.getClass().getDeclaredMethod("convertElementsToObject", Elements.class);
            method.setAccessible(true);

            List<Stock> response = (List<Stock>) method.invoke(crawlingService, elements);

            Assertions.assertThat(response.size()).isEqualTo(30);
            Assertions.assertThat(response.stream()
                    .anyMatch(stock -> stock.getName().equals("삼성전자"))).isTrue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
