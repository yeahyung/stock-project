package com.example.producer.controller;

import com.example.producer.service.CrawlingService;
import com.example.producer.service.ProducingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    private final CrawlingService crawlingService;

    private final ProducingService producingService;

    @Autowired
    public MainController(CrawlingService crawlingService, ProducingService producingService) {
        this.crawlingService = crawlingService;
        this.producingService = producingService;
    }

    @RequestMapping(value="/produceOnce", method = RequestMethod.GET)
    @ResponseBody
    public Boolean produceOnce() throws Exception{
        return producingService.produceNAVERFinanceCrawlingResult(
                crawlingService.getNAVERFinanceCrawlingResult());
    }

    @RequestMapping(value="/crawlOnce", method = RequestMethod.GET)
    @ResponseBody
    public String producer() throws Exception{
        return crawlingService.getNAVERFinanceCrawlingResult().toString();
    }
}
