package com.quan.wechat.dao;

import com.quan.wechat.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {


    List<ProductInfo> findByProductStatus(Integer status);

}
