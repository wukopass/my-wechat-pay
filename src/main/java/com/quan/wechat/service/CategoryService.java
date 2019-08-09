package com.quan.wechat.service;

import com.quan.wechat.entity.ProductCategory;

import java.util.List;

public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> typeList);

    ProductCategory save(ProductCategory productCategory);
}
