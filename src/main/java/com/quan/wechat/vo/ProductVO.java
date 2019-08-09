package com.quan.wechat.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.quan.wechat.entity.ProductInfo;
import lombok.Data;

import java.util.List;

/**
 * \* @Author: Quan
 * \* @Date: 2019/8/7 0007
 * \
 */
@Data
public class ProductVO {

    @JsonProperty("name")
    private String  categoryName;

    @JsonProperty("type")
    private Integer  categoryType;
    /**
     * 商品详细信息列表
     */
    @JsonProperty("foods")
    private List<ProductInfoVO>  productInfoVOList;

}
