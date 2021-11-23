package com.zhujunji.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Set;

/**
 * @Description gateway限流规则转换器
 * @Author J.zhu
 * @Date 2019/7/17
 */
public class GatewayFlowRuleConvert implements Converter<String,Set<GatewayFlowRule>> {
    @Override
    public Set<GatewayFlowRule> convert(String source) {
        return JSON.parseObject(source, new TypeReference<>() {});
    }
}
