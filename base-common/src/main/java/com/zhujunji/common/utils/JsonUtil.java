package com.zhujunji.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtil {

    /**
     * obj to format json
     *
     * @param obj   obj
     * @return String   格式化的 json 字符串
     */
    private static String jsonStringFormat(Object obj) {
        if (obj == null) {
            return null;
        }
        return JSON.toJSONString(
                obj,
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
    }

    /**"
     * json format
     *
     * @param json 未格式化的 json
     * @return String 格式化的 json
     */
    private static String jsonStringFormat(String json) {
        Object object = JSONObject.parse(json);
        return jsonStringFormat(object);
    }
}