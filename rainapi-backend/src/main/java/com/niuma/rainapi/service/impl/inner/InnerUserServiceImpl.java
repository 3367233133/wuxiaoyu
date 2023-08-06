package com.niuma.rainapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.niuma.rainapi.mapper.UserMapper;
import com.niuma.rainapi.service.UserService;
import com.niuma.rainapicommon.common.ErrorCode;
import com.niuma.rainapicommon.exception.BusinessException;
import com.niuma.rainapicommon.model.entity.User;
import com.niuma.rainapicommon.model.vo.UserVO;
import com.niuma.rainapicommon.service.InnerUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;


/**
 * @author niuma
 * @create 2023-02-27 16:13
 */
@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    UserMapper userMapper;
    @Resource
    UserService userService;

    @Override
    public User getInvokeUser(String ak) {
        if (StringUtils.isAnyBlank(ak)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey", ak);
        User user = userMapper.selectOne(queryWrapper);

        return user;
    }

    @Override
    public UserVO getLoginUser(String cookie) {
        return userService.getLoginUser(cookie);
    }
}
