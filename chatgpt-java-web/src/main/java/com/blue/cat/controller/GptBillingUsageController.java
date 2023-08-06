package com.blue.cat.controller;

import com.blue.cat.handler.OpenAiProxyServiceFactory;
import com.blue.cat.service.ChatBaseOpenAiProxyService;
import com.blue.cat.utils.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class GptBillingUsageController {


    /**
     * gpt的账号
     */
    @GetMapping("/gpt/billing")
    public ResultVO getAllTokenBillingUsage(){
        Map<String, String> hashMap = new LinkedHashMap<>();
        String[] tokenArray = OpenAiProxyServiceFactory.getTokenArray();
        ChatBaseOpenAiProxyService[] services = OpenAiProxyServiceFactory.getOpenAiProxyServiceArr();
        LocalDate startDate = LocalDate.of(2023, 5, 1);
        LocalDate endDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String left = startDate.format(formatter);
        String right = endDate.format(formatter);
        for (int i = 0; i < services.length; i++) {
            String billingUsage = services[i].billingUsage(left, right);
            hashMap.put(tokenArray[i],billingUsage);
        }
        return ResultVO.success(hashMap);
    }
}
