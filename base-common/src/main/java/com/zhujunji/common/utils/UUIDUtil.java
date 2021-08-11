package com.zhujunji.common.utils;

import java.util.UUID;

public class UUIDUtil {
    /**
     * 生成 uuid
     * @return String
     */
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
