package com.quan.wechat.aspect;

import com.quan.wechat.constant.CookieConstant;
import com.quan.wechat.constant.RedisConstant;
import com.quan.wechat.exception.SellerAuthorizeException;
import com.quan.wechat.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * \* @Author: Quan
 * \* @Date: 2019/8/12 0012
 * \
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;
    /**排除 登陆的controller，拦截sellercontroller的所有方法*/
    @Pointcut("execution(public * com.quan.wechat.controller.Seller*.*(..))"+
    "&& !execution(public * com.quan.wechat.controller.SellerUserController.*(..))")
    public void verify() {


    }

    @Before("verify()")
    public void doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 查询cookie

        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null) {
            log.warn("【登陆校验】cookie 中查不到token");
            throw  new SellerAuthorizeException();
        }
        //查寻token
        String  tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)) {
            log.warn("【登陆校验】redis 中查不到token");
            throw new SellerAuthorizeException();
        }
    }

}
