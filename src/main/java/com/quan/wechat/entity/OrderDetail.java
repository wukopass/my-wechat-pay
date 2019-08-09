package com.quan.wechat.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * \* @Author: Quan
 * \* @Date: 2019/8/7 0007
 * \
 */
@Entity
@Data
public class OrderDetail {

    @Id
    private  String  detailId;

    private  String   orderId;

    private  String  productId;

    private  String  productName;

    private  BigDecimal productPrice;

    private  Integer  productQuantity;

    private  String  productIcon;
}
