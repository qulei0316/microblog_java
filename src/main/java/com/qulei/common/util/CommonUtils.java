package com.qulei.common.util;

import java.util.UUID;

public class CommonUtils {

    /**
     * 随机生成uuid
     * @return
     */
    public static synchronized String createUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 判断字符是否为空
     * @param str
     * @return
     */
    public static boolean isStringEmpty(String str){
        return str == null || "".equals(str);
    }
}
