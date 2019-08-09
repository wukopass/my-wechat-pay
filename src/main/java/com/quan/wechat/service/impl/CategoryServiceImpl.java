package com.quan.wechat.service.impl;

import com.quan.wechat.dao.ProductCatagoryRepository;
import com.quan.wechat.entity.ProductCategory;
import com.quan.wechat.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * \* @Author: Quan
 * \* @Date: 2019/8/6 0006
 * \
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private  ProductCatagoryRepository repository;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return repository.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return  repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> typeList) {
        return repository.findByCategoryTypeIn(typeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
