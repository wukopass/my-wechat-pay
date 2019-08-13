package com.quan.wechat.dao;

import com.quan.wechat.entity.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoRepository  extends JpaRepository<SellerInfo,String> {

    SellerInfo findByOpenid(String openid);


}
