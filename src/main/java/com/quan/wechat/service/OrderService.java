package com.quan.wechat.service;

import com.quan.wechat.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    /**
     * 创建订单
     */
    OrderDTO create(OrderDTO orderMaster);

    /**
     * 查询单个订单
     * @param orderId
     * @return
     */
    OrderDTO findOne(String orderId);

    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    /**
     * 取消订单
     */
    OrderDTO cancel(OrderDTO orderDTO);

    /**
     * 完结订单
     */
    OrderDTO finish(OrderDTO orderDTO);

    /**
     * 支付订单
     */
    OrderDTO paid(OrderDTO orderDTO);

    /**
     * 卖家查询订单列表
     * @param request
     * @return
     */
    Page<OrderDTO> findList(PageRequest request);
}
