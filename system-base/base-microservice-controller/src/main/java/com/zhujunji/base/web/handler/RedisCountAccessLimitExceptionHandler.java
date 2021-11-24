package com.zhujunji.base.web.handler;

import com.zhujunji.common.constant.GlobalConstants;
import com.zhujunji.common.exception.CommonBizException;
import com.zhujunji.common.exception.ExpCodeEnum;
import com.zhujunji.common.handler.AccessLimitExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 限制频繁请求
 * @Date 2021-11-24
 * @Author J.zhu
 */
@Component
public class RedisCountAccessLimitExceptionHandler implements AccessLimitExceptionHandler {

    /**
     * 默认同一个 ip 请求相同接口间隔时间(毫秒)
     */
    private static final Long DEFAULT_INTERVAL_MILLISECONDS = 500L;

    private static final String REQUEST_COUNT_KEY_PREFIX = "request_count";

    private static final Long REQUEST_COUNT_THRESHOLD = 2L;

    @Resource
    private RedisTemplate<Object,Object> redisTemplate;


    @Override
    public void check(String uri, String ip, Long timeStamp, String nonce) {

        if(StringUtils.isBlank(uri) || StringUtils.isBlank(ip)){
            // 任意一个参数为空时不执行任何逻辑 any param is null do noting
            return;
        }

        String requestCountKey = getRequestCountKey(uri,ip);

        Long count = redisTemplate.opsForValue().increment(requestCountKey);
        // 保存一秒
        redisTemplate.expire(requestCountKey, DEFAULT_INTERVAL_MILLISECONDS, TimeUnit.MILLISECONDS);
        if(count != null && count > REQUEST_COUNT_THRESHOLD){
            throw new CommonBizException(ExpCodeEnum.REQUEST_TOO_FREQUENTLY);
        }

    }

    private String getRequestCountKey(String uri, String ip) {
        return REQUEST_COUNT_KEY_PREFIX + GlobalConstants.REDIS_KEY_DELIMITER + uri + GlobalConstants.REDIS_KEY_DELIMITER + ip;
    }
}
