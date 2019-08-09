package com.quan.wechat.vo;

import lombok.Data;

/**
 * \* @Author: Quan
 * \* @Date: 2019/8/7 0007
 * \
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;
}
