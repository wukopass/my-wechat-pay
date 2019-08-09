package com.quan.wechat.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quan.wechat.dto.OrderDTO;
import com.quan.wechat.entity.OrderDetail;
import com.quan.wechat.form.OrderForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * \* @Author: Quan
 * \* @Date: 2019/8/8 0008
 * \
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {
        Gson  gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        BeanUtils.copyProperties(orderForm, orderDTO);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        } catch (Exception e) {
           log.error("【对象转换】错误，string ={}", orderForm.getItems());
        }
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }
}
