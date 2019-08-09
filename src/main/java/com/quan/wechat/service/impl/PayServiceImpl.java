package com.quan.wechat.service.impl;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.quan.wechat.dto.OrderDTO;
import com.quan.wechat.service.PayService;
import org.springframework.stereotype.Service;

/**
 * \* @Author: Quan
 * \* @Date: 2019/8/8 0008
 * \
 */
@Service
public class PayServiceImpl  implements PayService {

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        return null;
    }
}
