package com.example.producer.controller;

import com.example.producer.service.CrawlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    private final CrawlingService crawlingService;

    @Autowired
    public MainController(CrawlingService crawlingService) {
        this.crawlingService = crawlingService;
    }

    @RequestMapping(value="/crawlOnce", method = RequestMethod.GET)
    @ResponseBody
    public String producer() throws Exception{
        return crawlingService.getNAVERFinanceCrawlingResult().toString();
    }
}
