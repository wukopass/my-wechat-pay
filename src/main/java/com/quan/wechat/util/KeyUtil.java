package com.quan.wechat.util;

import java.util.Random;

/**
 * \* @Author: Quan
 * \* @Date: 2019/8/7 0007
 * \
 */
public class KeyUtil {
    /**
     * 生成唯一的主键
     * 时间加随机数
     * @return
     */
    public static synchronized  String genUniqueKey() {

        Random random = new Random();

        Integer  number  = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);

    }
}
