package com.quan.wechat.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * \* @Author: Quan
 * \* @Date: 2019/8/12 0012
 * \
 */
@Data
@Entity
public class SellerInfo {

    @Id
    private  String  sellerId;

    private  String  username;

    private  String  password;

    private  String  openid;



}
