package com.niuma.rainapiinterface;

import com.niuma.rainapiclientsdk.client.RainApiClient;
import com.niuma.rainapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class rainapiInterfaceApplicationTests {
    @Resource
    private RainApiClient rainApiClient;

    @Test
    void contextLoads() {

        User user = new User();
        user.setUsername("niuma");
        String userByPost = rainApiClient.getUserByPost(user);
        System.out.println(userByPost);

    }

}
