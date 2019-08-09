package com.quan.wechat.service;

import com.lly835.bestpay.model.PayResponse;
import com.quan.wechat.dto.OrderDTO;

public interface PayService {


    PayResponse create(OrderDTO orderDTO);
}
