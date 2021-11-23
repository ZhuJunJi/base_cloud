package com.zhujunji.gateway.config;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

/**
 * Sentinel Nacos 数据元动态规则扩展
 */
public class NacosDataSourceInitFunc implements InitFunc {

    @Override
    public void init() throws Exception {
        final String remoteAddress = "localhost";
        final String groupId = "SENTINEL_GROUP";
        final String dataId = "com.alibaba.csp.sentinel.demo.flow.rule";

        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(remoteAddress,
                groupId, dataId, source -> JSON.parseObject(source, new TypeReference<>() {}));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
    }
}
