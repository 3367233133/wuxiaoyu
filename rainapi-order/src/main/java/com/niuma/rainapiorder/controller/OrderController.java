package com.niuma.rainapiorder.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.niuma.rainapicommon.common.BaseResponse;
import com.niuma.rainapicommon.common.ResultUtils;
import com.niuma.rainapiorder.model.dto.OrderAddRequest;
import com.niuma.rainapiorder.model.dto.OrderQueryRequest;
import com.niuma.rainapicommon.model.vo.OrderVO;
import com.niuma.rainapiorder.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author niuma
 * @create 2023-05-03 15:53
 */
@RestController
@RequestMapping("/")
public class OrderController {

    @Resource
    OrderService orderService;

    @PostMapping("/addOrder")
    public BaseResponse<OrderVO> addOrder(@RequestBody OrderAddRequest addOrderRequest, HttpServletRequest request) {
        OrderVO orderVO = orderService.addOrder(addOrderRequest, request);
        return ResultUtils.success(orderVO);
    }

    @GetMapping("/list")
    public BaseResponse<Page<OrderVO>> listPageOrder(OrderQueryRequest orderQueryRequest, HttpServletRequest request){
        Page<OrderVO> orderPage = orderService.listPageOrder(orderQueryRequest, request);
        return ResultUtils.success(orderPage);
    }
}
