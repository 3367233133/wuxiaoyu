package com.niuma.rainapicommon.service;

import com.niuma.rainapicommon.model.entity.User;
import com.niuma.rainapicommon.model.vo.UserVO;

/**
 * @author niuma
 * @create 2023-02-27 15:40
 */
public interface InnerUserService  {

    /**
     * 通过ak获取调用的用户
     * @param ak
     * @return
     */
    User getInvokeUser(String ak);

    /**
     * 获取登录的用户
     * @param cookie
     * @return
     */
    UserVO getLoginUser(String cookie);

}
