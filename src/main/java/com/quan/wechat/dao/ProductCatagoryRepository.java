package com.quan.wechat.dao;

import com.quan.wechat.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * \* @Author: Quan
 * \* @Date: 2019/8/6 0006
 * \
 */
public interface ProductCatagoryRepository extends JpaRepository<ProductCategory,Integer> {


    List<ProductCategory> findByCategoryTypeIn(List<Integer> typeList);
}
