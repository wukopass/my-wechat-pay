package com.quan.wechat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quan.wechat.enums.ProductStatusEnum;
import com.quan.wechat.util.EnumUtil;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * \* @Author: Quan
 * \* @Date: 2019/8/6 0006
 * \
 */
@Entity
@Data
public class ProductInfo {


    @Id
    private String  productId;

    private  String  productName;
    /**
     * 单价
     */
    private BigDecimal productPrice;
    /**
     * 小图
     */
    private String productIcon;
    /**
     * 描述
     */
    private String productDescription;
    /**
     * 状态0正常  1下架
     */
    private Integer productStatus =  ProductStatusEnum.UP.getCode();
    /**
     * 类目编号
     */
    private Integer categoryType;
    /**
     * 库存
     */
    private Integer productStock;

    private Date createTime;

    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum() {
        return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
    }
}
