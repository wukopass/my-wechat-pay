package com.quan.wechat.dao;

import com.quan.wechat.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * \* @Author: Quan
 * \* @Date: 2019/8/7 0007
 * \
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
