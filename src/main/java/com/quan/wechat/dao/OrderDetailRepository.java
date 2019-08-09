package com.quan.wechat.dao;

import com.quan.wechat.entity.OrderDetail;
import com.quan.wechat.entity.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {

    List<OrderDetail> findByOrderId(String orderId);
}
