package com.quan.wechat.service.impl;

import com.quan.wechat.dto.OrderDTO;
import com.quan.wechat.enums.ResultEnum;
import com.quan.wechat.exception.SellException;
import com.quan.wechat.service.BuyerService;
import com.quan.wechat.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * \* @Author: Quan
 * \* @Date: 2019/8/7 0007
 * \
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;
    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if (orderDTO == null) {
            log.error("【取消订单】查不到改订单, orderId={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        return orderService.cancel(orderDTO);
    }

    private OrderDTO checkOrderOwner(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            return null;
        }
        //判断是否是自己的订单
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            log.error("【查询订单】订单的openid不一致. openid={}, orderDTO={}", openid, orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }

}
