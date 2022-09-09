package com.example.producer.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Builder
public class Stock {
    Date currentTime;
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
