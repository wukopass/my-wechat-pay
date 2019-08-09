package com.quan.wechat.dto;

import lombok.Data;

/**
 * \* @Author: Quan
 * \* @Date: 2019/8/7 0007
 * \  gouwuche
 */
@Data
public class CartDTO {
    /**
     * 商品id
     */
    private String productId;
    /**
     * 商品数量
     */
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
