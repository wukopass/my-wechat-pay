package com.quan.wechat.dao;

import com.quan.wechat.entity.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    private final String OPENID = "4008208820";
    @Autowired
    private  OrderMasterRepository repository;

    @Test
    public void saveTest() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("10083");
        orderMaster.setBuyerName("吴业全");
        orderMaster.setBuyerPhone("15797690595");
        orderMaster.setBuyerAddress("广信区花厅镇");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(1000));
        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);
    }
    @Test
    public void findByBuyerOpenid() {
        PageRequest request = new PageRequest(0, 1);

        Page<OrderMaster> result = repository.findByBuyerOpenid(OPENID, request);
        Assert.assertNotEquals(0,result.getTotalElements());
        System.err.println(result.getTotalElements());

    }
}