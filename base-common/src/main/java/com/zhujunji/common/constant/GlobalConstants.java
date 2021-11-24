package com.zhujunji.common.constant;

import java.time.ZoneOffset;

/**
 * 全局常量类
 */
public class GlobalConstants {

    /**
     * 请求 ID
     */
    public static final String REQUEST_ID = "requestId";

    public static final String AUTHORIZATION = "Authorization";

    public static final String ACCEPT_LANGUAGE = "Accept-Language";

    public static final String TIME_STAMP = "Time_Stamp";

    public static final String NONCE = "Nonce";

    /**
     * Redis key 分隔符
     */
    public static final String REDIS_KEY_DELIMITER = ":";
    /**
     * 默认通用字段处理器
     */
    public static final String DEFAULT_FIELD_HANDLER = "DefaultFieldHandler";

    /**
     * 版本
     */
    public static final String VERSION = "version";

    /**
     * 中国上海时区
     */
    public static final ZoneOffset ZONE_CN_SHANGHAI = ZoneOffset.ofHours(8);
}
