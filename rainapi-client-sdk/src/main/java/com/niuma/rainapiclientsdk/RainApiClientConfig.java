package com.niuma.rainapiclientsdk;

import com.niuma.rainapiclientsdk.client.RainApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author niumazlb
 * @create 2022-11-09 20:38
 */
@Configuration
@ConfigurationProperties("binapi-client")
@Data
@ComponentScan
public class RainApiClientConfig {


    private String accessKey;
    private String secretKey;

    @Bean
    public RainApiClient binApiClient(){
        RainApiClient binApiClient = new RainApiClient(accessKey,secretKey);
        return binApiClient;
    }

}
