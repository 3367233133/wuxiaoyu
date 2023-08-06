package com.niuma.rainapi.service.impl.inner;


import com.niuma.rainapi.service.UserInterfaceInfoService;
import com.niuma.rainapicommon.model.dto.UpdateUserInterfaceInfoDTO;
import com.niuma.rainapicommon.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author niuma
 * @create 2023-02-27 15:47
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    UserInterfaceInfoService userInterfaceInfoService;

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {

        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }

    @Override
    public boolean updateUserInterfaceInfo(UpdateUserInterfaceInfoDTO updateUserInterfaceInfoDTO) {
        return userInterfaceInfoService.updateUserInterfaceInfo(updateUserInterfaceInfoDTO);
    }

    @Override
    public boolean checkUserInvokeAuth(Long userId, Long interfaceId) {
        return userInterfaceInfoService.checkUserInvokeAuth(userId, interfaceId);
    }


}
