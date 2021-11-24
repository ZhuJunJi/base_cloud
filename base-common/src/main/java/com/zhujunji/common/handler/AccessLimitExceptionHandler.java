package com.zhujunji.common.handler;

/**
 * 限制请求检查接口
 * @Date 2021-11-24
 * @Author J.zhu
 */
public interface AccessLimitExceptionHandler {

    /**
     * 检查请求是否满足限制
     * @param uri       请求 uri
     * @param ip        请求者 ip
     * @param timeStamp 时间戳
     * @param nonce     nonce
     */
    void check(String uri, String ip, Long timeStamp, String nonce);
}
