package com.quan.wechat.service;

import com.quan.wechat.dto.OrderDTO;

public interface BuyerService {


    OrderDTO findOrderOne(String openid, String orderId);

    OrderDTO cancelOrder(String openid, String orderId);
}
