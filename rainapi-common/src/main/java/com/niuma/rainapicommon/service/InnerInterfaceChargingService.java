package com.niuma.rainapicommon.service;

import com.niuma.rainapicommon.model.dto.UnLockAvailablePiecesDTO;
import com.niuma.rainapicommon.model.dto.UpdateAvailablePiecesDTO;

/**
 * @author niuma
 * @create 2023-05-03 16:22
 */
public interface InnerInterfaceChargingService {
    /**
     * 检查某个接口的库存是否充足
     * @param id
     * @return
     */
    boolean checkInventory(Long id);

    /**
     * 更新某个接口的库存
     * @param updateAvailablePiecesDTO
     * @return
     */
    boolean updateAvailablePieces(UpdateAvailablePiecesDTO updateAvailablePiecesDTO);

    /**
     * 解锁某个接口的库存
     * @param unLockAvailablePiecesDTO
     * @return
     */
    boolean unLockAvailablePieces(UnLockAvailablePiecesDTO unLockAvailablePiecesDTO);
}
