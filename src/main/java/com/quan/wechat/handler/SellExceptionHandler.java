package com.quan.wechat.handler;

import com.quan.wechat.config.ProjectUrlConfig;
import com.quan.wechat.exception.SellerAuthorizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.rmi.MarshalledObject;
import java.util.HashMap;
import java.util.Map;

/**
 * \* @Author: Quan
 * \* @Date: 2019/8/12 0012
 * \
 */
@ControllerAdvice
public class SellExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    //拦截登陆异常
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException() {
       /*return  new ModelAndView("redirect:".
               concat(projectUrlConfig.getWechatOpenAuthorize())
       .concat("/sell/wechat/qrAuthorize")
       .concat("?returnUrl=")
       .concat(projectUrlConfig.getSell()
       .concat("/sell/seller/login")));*/
        Map<String,Object> map  = new HashMap<>();
        map.put("url","/sell/seller/loginPage");
        map.put("msg","您还没登陆，跳转至登陆页面");
        return new ModelAndView("common/error", map);
    }


}
