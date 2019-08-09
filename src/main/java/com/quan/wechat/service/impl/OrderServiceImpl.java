package com.quan.wechat.service.impl;

import com.quan.wechat.converter.OrderMaster2OrderDTOConverter;
import com.quan.wechat.dao.OrderDetailRepository;
import com.quan.wechat.dao.OrderMasterRepository;
import com.quan.wechat.dto.CartDTO;
import com.quan.wechat.dto.OrderDTO;
import com.quan.wechat.entity.OrderDetail;
import com.quan.wechat.entity.OrderMaster;
import com.quan.wechat.entity.ProductInfo;
import com.quan.wechat.enums.OrderStatusEnum;
import com.quan.wechat.enums.PayStatusEnum;
import com.quan.wechat.enums.ResultEnum;
import com.quan.wechat.exception.SellException;
import com.quan.wechat.service.OrderService;
import com.quan.wechat.service.ProductService;
import com.quan.wechat.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
/**
 * \* @Author: Quan
 * \* @Date: 2019/8/7 0007
 * \
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        //1.查询商品(数量，价格)
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2.计算总价(专门乘法方法)
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            //订单详情入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            //属性拷贝
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);
        }
        //3.写入订单数据库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        //4.扣库存（lamada表达式只包含一条语句时，可以省略大括号,return和语句结尾的分号）
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().
                stream().map(e -> {
            return new CartDTO(e.getProductId(), e.getProductQuantity());
        }).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);
        //发送websocket消息


        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {

        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());

    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        //又是属性复制
        OrderMaster orderMaster = new OrderMaster();

        //判断状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单状态不正确,orderId = {} ,orderStatus = {} 】", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【取消订单】更新失败,orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【取消订单】订单中无商品详情,orderDTO ={}", orderDTO);
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);
        //已支付要退款
        if (orderDTO.getOrderStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            //todo
        }
        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        //1 判断状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态不正确,orderId= {} orderStatus ={} ", orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2.修改状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【完结订单】更新失败,orderMaster={]", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单支付完成】订单状态不正确,orderId= {} orderStatus ={} ", orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【订单支付完成】订单支付状态不正确,orderDTO ={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付狀態
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【订单支付完成】更新失败,orderMaster={]", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(PageRequest request) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(request);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

        return new PageImpl<>(orderDTOList, request, orderMasterPage.getTotalElements());
    }
}
