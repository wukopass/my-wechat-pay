package com.quan.wechat.service.impl;

import com.quan.wechat.dao.SellerInfoRepository;
import com.quan.wechat.entity.SellerInfo;
import com.quan.wechat.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * \* @Author: Quan
 * \* @Date: 2019/8/12 0012
 * \
 */
@Service
public class SellerServiceImpl implements SellerService {


    @Autowired
    private SellerInfoRepository repository;
    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
