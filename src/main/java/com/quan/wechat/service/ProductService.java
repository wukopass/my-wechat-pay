package com.quan.wechat.service;

import com.quan.wechat.dto.CartDTO;
import com.quan.wechat.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProductService {

    List<ProductInfo> findAll();
    /**
     * 查询所有在架商品列表
     * @return
     */
    List<ProductInfo> findUpAll();

    List<ProductInfo> findByCategoryTypeIn(Integer categoryType);
    /**
     * 查询一个商品信息
     * @param  productId
     * @return
     */
    ProductInfo findOne(String productId);
    /**
     * 加库存
     */
    void increaseStock(List<CartDTO> cartDTOList);

    /**
     * 减库存
     * @param cartDTOList
     */
    void decreaseStock(List<CartDTO> cartDTOList);

    /**
     * 商品入库
     */
    ProductInfo save(ProductInfo productInfo);

    Page<ProductInfo> findAll(PageRequest request);

    /**
     * 上架
     * @param productId
     * @return
     */
    ProductInfo onSale(String productId);

    /**
     * 下架
     * @param productId
     * @return
     */
    ProductInfo offSale(String productId);
}
