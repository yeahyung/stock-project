package com.example.producer.service;

import com.example.producer.dto.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CrawlingService {
    public List<Stock> getNAVERFinanceCrawlingResult() throws Exception {
        Document doc = Jsoup.connect("https://finance.naver.com/sise/lastsearch2.nhn").get();
        Elements elements = doc.getElementsByClass("type_5");
        return convertElementsToObject(elements.select("tbody tr"));
    }

    private List<Stock> convertElementsToObject(Elements elements) {
        List<Stock> stockList = new ArrayList<>();
        for(String stockInfo : elements.eachText()) {
            String[] stockInfos = stockInfo.split("% ", 2);
            // 주식 정보가 아닌 경우
            if (stockInfos.length < 2) {
                continue;
            }

            /*
             index 0: 순위
             index 마지막: % 검색 비율
             Stock Name : 0번 index 마지막 글자 ~ 마지막 index 시작 전
             */
            String[] stockHeaderInfo = stockInfos[0].split(" ");
            int lastIndexPosition = stockInfos[0].length() - stockHeaderInfo[stockHeaderInfo.length - 1].length();
            String stockName = stockInfos[0].substring(stockHeaderInfo[0].length() + 1, lastIndexPosition);

            String[] stockFooterInfo = stockInfos[1].split(" ");
            Stock stock = Stock.builder()
                    .ratio(stockHeaderInfo[0])
                    .name(stockName.trim())
                    .searchRatio(stockHeaderInfo[stockHeaderInfo.length -1])
                    .currentValue(stockFooterInfo[0].replace(",", ""))
                    .valueCompareToYesterday(stockFooterInfo[1].replace(",", ""))
                    .percentCompareToYesterday(stockFooterInfo[2])
                    .transactionVolume(stockFooterInfo[3].replace(",", ""))
                    .marketPrice(stockFooterInfo[4].replace(",", ""))
                    .highestPrice(stockFooterInfo[5].replace(",", ""))
                    .lowestPrice(stockFooterInfo[6].replace(",", ""))
                    .currentTime(new Date())
                    .build();
            stockList.add(stock);
        }
        return stockList;
    }
}
