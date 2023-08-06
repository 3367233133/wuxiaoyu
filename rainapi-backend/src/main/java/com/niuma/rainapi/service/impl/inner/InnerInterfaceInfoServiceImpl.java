package com.niuma.rainapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.niuma.rainapi.mapper.InterfaceInfoMapper;
import com.niuma.rainapicommon.common.ErrorCode;
import com.niuma.rainapicommon.exception.BusinessException;
import com.niuma.rainapicommon.model.entity.InterfaceInfo;
import com.niuma.rainapicommon.service.InnerInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author niuma
 * @create 2023-02-27 16:13
 */
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
    @Resource
    InterfaceInfoMapper interfaceInfoMapper;
    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        if(StringUtils.isAnyBlank(url,method)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url",url);
        queryWrapper.eq("method",method);
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectOne(queryWrapper);

        return interfaceInfo;
    }

    @Override
    public InterfaceInfo getInterfaceInfoById(Long id) {

        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectById(id);
        return interfaceInfo;
    }
}
