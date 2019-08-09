package com.quan.wechat.entity;

import com.quan.wechat.enums.OrderStatusEnum;
import com.quan.wechat.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * \* @Author: Quan
 * \* @Date: 2019/8/7 0007
 * \
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    @Id
    private  String orderId;

    private  String buyerName;

    private  String  buyerPhone;

    private  String  buyerAddress;
    /**
     * 买家的微信openid
     */
    private  String  buyerOpenid;

    private  BigDecimal orderAmount;
    /**
     * 订单状态
     */
    private  Integer orderStatus = OrderStatusEnum.NEW.getCode();
    /**
     * 支付状态
     */
    private Integer  payStatus = PayStatusEnum.WAIT.getCode();
    /** 创建时间.*/
    private Date createTime;

    /** 更新时间.*/
    private Date updateTime;
    /** 避免jpa和数据库对应时候忽略掉 */
   /* @Transient  一般不这样定义而是定义在数据传输对象
    private List<OrderDetail>  orderDetailList;*/
}
