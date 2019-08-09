package com.quan.wechat.dao;

import com.quan.wechat.entity.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    ProductInfoRepository  repository;
    @Test
    public void saveTest() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("1223454");
        productInfo.setProductName("皮蛋粥");
        productInfo.setProductDescription("這是好吃的皮蛋粥");
        productInfo.setCategoryType(1);
        productInfo.setProductStatus(1);
        productInfo.setProductIcon("https://www.baidu.com");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(999);
        ProductInfo save = repository.save(productInfo);
        Assert.assertNotNull(save);

    }

    @Test
    public  void findByProductStatus() {
        List<ProductInfo>  productInfoList = repository.findByProductStatus(1);

        Assert.assertNotEquals(0,productInfoList.size());
    }
}