package com.quan.wechat.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * \* @Author: Quan
 * \* @Date: 2019/8/7 0007
 * \
 */
@Data
public class ProductInfoVO {

    @JsonProperty("id")
    private  String  productId;

    @JsonProperty("name")
    private String  productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String  productDescription;

    @JsonProperty("icon")
    private String  productIcon;
}
