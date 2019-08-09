package com.quan.wechat.exception;

import com.quan.wechat.enums.ResultEnum;

/**
 * \* @Author: Quan
 * \* @Date: 2019/8/7 0007
 * \
 */
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum  resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
