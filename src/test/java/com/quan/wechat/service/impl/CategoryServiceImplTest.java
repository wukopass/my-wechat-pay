package com.quan.wechat.service.impl;

import com.quan.wechat.entity.ProductCategory;
import com.quan.wechat.service.CategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;
    @Test
    public void findOne() {

        ProductCategory one = categoryService.findOne(2);
        Assert.assertNotEquals(new Integer(1),one.getCategoryId());

    }

    @Test
    public void findAll() {
    }

    @Test
    public void findByCategoryTypeIn() {
    }

    @Test
    public void save() {
    }
}