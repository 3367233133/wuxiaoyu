package com.blue.cat.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ChatBaseAuthenticationInterceptor implements Interceptor {
    private final String token;
    public ChatBaseAuthenticationInterceptor(String token) {
        this.token = token;
    }

    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder().header("Authorization", "Bearer " + this.token).build();
        return chain.proceed(request);
    }
}
