package com.quan.wechat.enums;

import lombok.Getter;

/**
 * \* @Author: Quan
 * \* @Date: 2019/8/7 0007
 * \
 */
@Getter
public enum ProductStatusEnum implements  CodeEnum {

    UP(0,"在架"),
    DOWN(1,"下架");

    private Integer code;

    private String  message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
