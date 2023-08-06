package com.niuma.rainapiorder.service.impl;

import com.niuma.rainapicommon.model.entity.Order;
import com.niuma.rainapicommon.service.InnerOrderService;
import com.niuma.rainapiorder.service.OrderService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author niuma
 * @create 2023-05-11 23:00
 */
@DubboService
public class InnerOrderServiceImpl implements InnerOrderService {
    @Resource
    OrderService orderService;
    @Override
    public List<Order> listTopBuyInterfaceInfo(int limit) {
        return orderService.listTopBuyInterfaceInfo(limit);
    }
}
