package com.quan.wechat.service.impl;

import com.quan.wechat.dao.OrderMasterRepository;
import com.quan.wechat.dto.OrderDTO;
import com.quan.wechat.entity.OrderDetail;
import com.quan.wechat.enums.OrderStatusEnum;
import com.quan.wechat.enums.PayStatusEnum;
import com.quan.wechat.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID = "1101110";

    private final String ORDER_ID = "1565231155445432554";

    @Test
    public void crate() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("方翔鸣");
        orderDTO.setBuyerAddress("上海");
        orderDTO.setBuyerPhone("4008208820");
        orderDTO.setBuyerOpenid(BUYER_OPENID);
        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("s10000");
        o1.setProductQuantity(50);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("s10003");
        o2.setProductQuantity(2);

        orderDetailList.add(o1);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("【创建订单】result={}", result);
        Assert.assertNotNull(result);

    }

    @Test
    public void findOne() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        log.info("【查询单个订单】 result = " + orderDTO);
        Assert.assertEquals(ORDER_ID, orderDTO.getOrderId());
    }

    @Test
    public void findList() {
        PageRequest request = new PageRequest(0, 2);
        Page<OrderDTO> result = orderService.findList(BUYER_OPENID, request);
        Assert.assertNotEquals(0, result.getTotalElements());
    }
    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }
    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO reuslt = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),reuslt.getOrderStatus());
    }
    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO reuslt = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),reuslt.getPayStatus());
    }
}