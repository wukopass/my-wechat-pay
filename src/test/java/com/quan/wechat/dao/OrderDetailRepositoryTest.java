package com.quan.wechat.dao;

import com.quan.wechat.entity.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private  OrderDetailRepository repository;

    @Test
    public void save() {
        OrderDetail detail = new OrderDetail();
        detail.setDetailId("123446");
        detail.setOrderId("10083");
        detail.setProductIcon("www.abcd.com");
        detail.setProductId("s10001");
        detail.setProductName("酒鬼花生");
        detail.setProductPrice(new BigDecimal(3.2));
        detail.setProductQuantity(8);
        OrderDetail result = repository.save(detail);
        Assert.assertNotNull(result);
    }
    @Test
    public void findByOrderId() {
        List<OrderDetail>  result = repository.findByOrderId("10083");
        Assert.assertNotEquals(0,result.size());

    }
}