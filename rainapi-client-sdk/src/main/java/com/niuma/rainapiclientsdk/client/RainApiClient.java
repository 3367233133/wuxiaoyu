package com.niuma.rainapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.niuma.rainapiclientsdk.model.User;

import java.util.HashMap;
import java.util.Map;

import static com.niuma.rainapiclientsdk.util.SignUtil.genSign;


/**
 * @author niumazlb
 * @create 2022-11-09 15:06
 */
public class RainApiClient {

    private String accessKey;
    private String secretKey;
    private static final String GATEWAY_HOST ="http://localhost:8002";

    public RainApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    private Map<String, String> getHeaderMap(String body) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey", accessKey);
//        hashMap.put("secretKey",secretKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("body", body);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", genSign(body, secretKey));
        return hashMap;
    }

    public String getUserByPost(User user) {
        String json = JSONUtil.toJsonStr(user);
        String result2 = HttpRequest.post(GATEWAY_HOST +"/api/interface/user")
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute().body();
        return result2;
    }

    public String renjian() {
        String result2 = HttpRequest.get(GATEWAY_HOST +"/api/interface/renjian")
                .addHeaders(getHeaderMap(""))
                .execute().body();
        return result2;
    }

}
