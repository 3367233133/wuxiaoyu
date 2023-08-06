package com.blue.cat.handler;

import com.blue.cat.service.ChatBaseOpenAiProxyService;
import io.github.asleepyfish.config.ChatGPTProperties;

import java.time.Duration;

/**
 * @author huyd
 * @date 2023/5/23 8:09 PM
 */
public class OpenAiProxyServiceFactory {

    private static final String[] tokenArray = {"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"};

    private static final ChatBaseOpenAiProxyService[] openAiProxyServiceArr = new ChatBaseOpenAiProxyService[tokenArray.length];

    static {
        for (int i = 0; i < tokenArray.length; i++) {
            ChatGPTProperties properties = new ChatGPTProperties();
            properties.setChatModel("gpt-3.5-turbo");
            properties.setSessionExpirationTime(2);
            properties.setToken(tokenArray[i]);
            ChatBaseOpenAiProxyService openAiProxyService = new ChatBaseOpenAiProxyService(properties, Duration.ZERO);
            openAiProxyServiceArr[i] = openAiProxyService;
        }
    }

    public static ChatBaseOpenAiProxyService createProxyService(Long userId){
        int i = (int) (userId % tokenArray.length);
        return openAiProxyServiceArr[i];
    }

    public static void init(){

    }

    public static String[] getTokenArray() {
        return tokenArray;
    }

    public static ChatBaseOpenAiProxyService[] getOpenAiProxyServiceArr() {
        return openAiProxyServiceArr;
    }
}
