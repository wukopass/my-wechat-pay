package com.quan.wechat.controller;

import com.mysql.jdbc.TimeUtil;
import com.quan.wechat.config.ProjectUrlConfig;
import com.quan.wechat.constant.CookieConstant;
import com.quan.wechat.constant.RedisConstant;
import com.quan.wechat.entity.SellerInfo;
import com.quan.wechat.enums.ResultEnum;
import com.quan.wechat.service.SellerService;
import com.quan.wechat.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * \* @Author: Quan
 * \* @Date: 2019/8/12 0012
 * \ 卖家用户
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response, Map<String, Object> map) {
        //1.openid 和数据库匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error");
        }

        //2.设置token到redis  opsvalue表示操作某些value
        String token = UUID.randomUUID().toString();

        Integer expire = RedisConstant.EXPIRE;
        /** 设置 key value  过期时间 时间单位*/
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);
        //3.设置token到cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
        return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/sell/seller/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView  logout(HttpServletRequest request, HttpServletResponse response,
                       Map<String, Object> map) {
        //1从cookie里面查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        //2,清除redis
        if (cookie != null) {
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        //3清除cookie(设置为0的方式清除)
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }
        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/loginPage")
    public ModelAndView loginPage() {
        return new ModelAndView("/common/notLogin");
    }
}
