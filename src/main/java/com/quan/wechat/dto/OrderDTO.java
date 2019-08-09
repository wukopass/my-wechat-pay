package com.quan.wechat.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.quan.wechat.entity.OrderDetail;
import com.quan.wechat.enums.OrderStatusEnum;
import com.quan.wechat.enums.PayStatusEnum;
import com.quan.wechat.util.EnumUtil;
import com.quan.wechat.util.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * \* @Author: Quan
 * \* @Date: 2019/8/7 0007
 * \  商品订单传输对象
 */
@Data
/*@JsonInclude(JsonInclude.Include.NON_NULL)*/
public class OrderDTO {

    /** 订单id. */
    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家手机号. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 买家微信Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus;

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus;

    /** 创建时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 更新时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
    /**
     * 返回订单列表是非必须的字段  如果是必须的要赋初始值
     */
    List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
