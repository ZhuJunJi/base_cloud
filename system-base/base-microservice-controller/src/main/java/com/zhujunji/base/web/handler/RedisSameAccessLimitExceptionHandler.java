package com.zhujunji.base.web.handler;

import com.zhujunji.common.constant.GlobalConstants;
import com.zhujunji.common.exception.CommonBizException;
import com.zhujunji.common.exception.ExpCodeEnum;
import com.zhujunji.common.handler.AccessLimitExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * 限制重复请求
 *
 * @Date 2021-11-24
 * @Author J.zhu
 */
@Slf4j
@Component
public class RedisSameAccessLimitExceptionHandler implements AccessLimitExceptionHandler {

    /**
     * 默认同一个 ip 请求相同接口间隔时间(毫秒)
     */
    private static final Long DEFAULT_INTERVAL_MILLISECONDS = 3000L;

    private static final String PRE_REQUEST_KEY_PREFIX = "pre_request";

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void check(String uri, String ip, Long timeStamp, String nonce) {
        if (StringUtils.isBlank(uri) || StringUtils.isBlank(ip) || timeStamp == null
                || StringUtils.isBlank(nonce)) {
            // 任意一个参数为空时不执行任何逻辑 any param is null do noting
            return;
        }
        Long serverTimeMillis = System.currentTimeMillis();

        long intervalTimeMillis = Math.abs(serverTimeMillis - timeStamp);

        if (intervalTimeMillis > DEFAULT_INTERVAL_MILLISECONDS) {
            log.error("请求时间戳与服务器时间间隔超过 {} 秒请求失败！ timeStamp:{} serverTime: {}", intervalTimeMillis,
                    timeStamp, serverTimeMillis);
            throw new CommonBizException(ExpCodeEnum.TIME_STAMP_INVALID);
        }

        TimeStampNonce timeStampNonce = new TimeStampNonce(timeStamp, nonce);

        String preRequestKey = getPreRequestKey(uri, ip);

        Boolean hasPreRequest = redisTemplate.hasKey(preRequestKey);

        if (hasPreRequest != null && hasPreRequest) {
            // 获取上一次请求的 TimeStampNonce 对象
            TimeStampNonce preTimeStampNonce = (TimeStampNonce) redisTemplate.opsForValue().get(preRequestKey);
            if (timeStampNonce.equals(preTimeStampNonce)) {
                // 重复请求
                throw new CommonBizException(ExpCodeEnum.REPEAT_REQUEST);
            }
        }
        // 设置 TimStampNonce 过期时间
        redisTemplate.opsForValue().set(preRequestKey, timeStampNonce, DEFAULT_INTERVAL_MILLISECONDS,
                TimeUnit.MILLISECONDS);
    }


    private String getPreRequestKey(String uri, String ip) {
        return PRE_REQUEST_KEY_PREFIX + GlobalConstants.REDIS_KEY_DELIMITER + uri
                + GlobalConstants.REDIS_KEY_DELIMITER + ip;
    }

    /**
     * timeStamp 与 nonce 相同时判定为重复请求
     *
     * @Date 2021-11-24
     * @Author J.zhu
     */
    public static class TimeStampNonce implements Serializable {

        /**
         * 时间戳
         */
        private Long timeStamp;

        /**
         * nonce
         */
        private String nonce;

        public TimeStampNonce() {
        }

        public TimeStampNonce(Long timeStamp, String nonce) {
            this.timeStamp = timeStamp;
            this.nonce = nonce;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            TimeStampNonce that = (TimeStampNonce) o;

            return new EqualsBuilder().append(timeStamp, that.timeStamp).append(nonce, that.nonce).isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37).append(timeStamp).append(nonce).toHashCode();
        }
    }
}
