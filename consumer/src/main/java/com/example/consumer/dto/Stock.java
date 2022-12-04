package com.example.consumer.dto;

import lombok.Data;

@Data
public class Stock {
    Long currentTime;
    String ratio;
    String name;
    String searchRatio;
    String currentValue;
    String valueCompareToYesterday;
    String percentCompareToYesterday;
    String transactionVolume;
    String marketPrice;
    String highestPrice;
    String lowestPrice;
}
