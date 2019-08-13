package com.quan.wechat.dao;

import com.quan.wechat.entity.SellerInfo;
import com.quan.wechat.util.KeyUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {
    @Autowired
    private  SellerInfoRepository repository;
    @Test
    public void save() {
        SellerInfo  sellerInfo = new SellerInfo();
        sellerInfo.setPassword("1234556");
        sellerInfo.setUsername("admin");
        sellerInfo.setOpenid("o_5yxwLdMnZ8wvyGhdhuj1eiWC4w");
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        repository.save(sellerInfo);
    }

}