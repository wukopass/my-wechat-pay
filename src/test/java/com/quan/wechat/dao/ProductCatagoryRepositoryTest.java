package com.quan.wechat.dao;

import com.quan.wechat.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCatagoryRepositoryTest {

    @Autowired
    private  ProductCatagoryRepository repository;


    @Test
    public void findOneTest() {
        List<ProductCategory> productCategory = repository.findAll();
        for (ProductCategory category : productCategory) {
            System.err.println(category.toString());

        }
    }
    @Test
    public void saveTest() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryType(1);
        productCategory.setCategoryName("男生最爱");
        ProductCategory result = repository.save(productCategory);
        //不期望是null
        Assert.assertNotNull(result);
    }

    @Test
    public void findByCategoryTypeIn() {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 8);
        List<ProductCategory> byCategoryTypeIn = repository.findByCategoryTypeIn(list);
        System.err.println("jelel");
    }



}