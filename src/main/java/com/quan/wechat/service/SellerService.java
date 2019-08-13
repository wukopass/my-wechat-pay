package com.quan.wechat.service;

import com.quan.wechat.entity.SellerInfo;

/**
 * \* @Author: Quan
 * \* @Date: 2019/8/12 0012
 * \
 */
public interface  SellerService {


    SellerInfo findSellerInfoByOpenid(String openid);
}
