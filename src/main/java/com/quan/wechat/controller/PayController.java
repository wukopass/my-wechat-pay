package com.quan.wechat.controller;

import com.lly835.bestpay.model.PayResponse;
import com.quan.wechat.dto.OrderDTO;
import com.quan.wechat.enums.ResultEnum;
import com.quan.wechat.exception.SellException;
import com.quan.wechat.service.OrderService;
import com.quan.wechat.service.PayService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * \* @Author: Quan
 * \* @Date: 2019/8/8 0008
 * \
 */
@Controller
@RequestMapping("/pay")
public class PayController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String, Object> map) {
        //1. 查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //2. 发起支付
        PayResponse payResponse = payService.create(orderDTO);

        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);

        return new ModelAndView("pay/create", map);
    }

}
