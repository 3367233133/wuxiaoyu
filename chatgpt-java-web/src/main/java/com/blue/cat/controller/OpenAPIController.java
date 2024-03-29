package com.blue.cat.controller;

import com.blue.cat.bean.annotation.VisitLimit;
import com.blue.cat.bean.common.BaseCodeEnum;
import com.blue.cat.bean.constants.CommonConstant;
import com.blue.cat.bean.constants.LimitEnum;
import com.blue.cat.bean.constants.ModelServiceTypeConstant;
import com.blue.cat.bean.gpt.ChatReq;
import com.blue.cat.bean.gpt.ImageReq;
import com.blue.cat.bean.gpt.drawImageRes;
import com.blue.cat.handler.OpenAiProxyServiceFactory;
import com.blue.cat.service.ChatBaseOpenAiProxyService;
import com.blue.cat.utils.HttpUtil;
import com.blue.cat.utils.OpenGptUtil;
import com.blue.cat.utils.SessionUser;
import io.github.asleepyfish.util.OpenAiUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author huyd
 * @date 2023/5/5 11:19 PM
 */
@Slf4j
@RestController
@RequestMapping("/chat")
public class OpenAPIController {


    /**
     * 走 yml配置的token
     * @param chatReq
     * @param response
     * @throws Exception
     */
    @VisitLimit(value = {LimitEnum.IP},scope = CommonConstant.NO_LOGIN_SCOPE)
    @PostMapping("/streamChatWithWeb/V2")
    public void streamChatWithWebV2(@RequestBody ChatReq chatReq, HttpServletResponse response) throws Exception{
        try {
            response.setContentType("text/event-stream");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            Long id = SessionUser.getUserId();
            String userId = id == null ? "" : String.valueOf(id);
            String uid = StringUtils.isBlank(chatReq.getConversationId()) ? userId : userId + "_" + chatReq.getConversationId();
            OpenAiUtils.createStreamChatCompletion(chatReq.getPrompt(), StringUtils.isBlank(uid) ? null : uid, response.getOutputStream());
        }catch (Exception e){
            log.error("streamChatWithWebV2 error",e);
            response.getOutputStream().write(BaseCodeEnum.SERVER_BUSY.getMsg().getBytes());
        }
    }

    /**
     *  走com.blue.cat.handler.OpenAiProxyServiceFactory配置的token
     * @param chatReq
     * @param response
     * @throws Exception
     */
    @VisitLimit(value = {LimitEnum.IP},scope = CommonConstant.NO_LOGIN_SCOPE)
    @PostMapping("/streamChatWithWeb/V3")
    public void streamChatWithWebV3(@RequestBody ChatReq chatReq, HttpServletResponse response) throws Exception{
        Long id = SessionUser.getUserId();
        String userId = id == null ? "" : String.valueOf(id);
        String uid = StringUtils.isBlank(chatReq.getConversationId()) ? userId : userId + "_" + chatReq.getConversationId();
        try {
            response.setContentType("text/event-stream");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Cache-Control", "no-cache");

            // todo 这个函数就是初始化属性：
            ChatBaseOpenAiProxyService proxyService = OpenAiProxyServiceFactory.createProxyService(Long.parseLong(chatReq.getConversationId()));

            // todo 就是通过这个函数，response往前端写入ChatGpt的返回值
            proxyService.createStreamChatCompletion(chatReq.getPrompt(), StringUtils.isBlank(uid) ? null : uid, response.getOutputStream());
        }catch (Exception e){
            log.error("streamChatWithWebV3 error ",e);
            response.getOutputStream().write(BaseCodeEnum.SERVER_BUSY.getMsg().getBytes());
        }
    }

}
