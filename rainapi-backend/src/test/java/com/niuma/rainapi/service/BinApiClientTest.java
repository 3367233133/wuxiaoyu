package com.niuma.rainapi.service;

import com.niuma.rainapiclientsdk.client.RainApiClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author niuma
 * @create 2023-03-28 16:25
 */
@SpringBootTest
public class BinApiClientTest {

    @Test
    public void testRenjian(){
        RainApiClient binApiClient = new RainApiClient("niuma","asdfg");
        String renjian = binApiClient.renjian();
        System.out.println(renjian);

    }

}
