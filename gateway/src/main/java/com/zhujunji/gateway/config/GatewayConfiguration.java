package com.zhujunji.gateway.config;

import com.alibaba.cloud.sentinel.custom.SentinelDataSourceHandler;
import com.alibaba.cloud.sentinel.datasource.config.AbstractDataSourceProperties;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.Collections;
import java.util.List;

/**
 * Gateway 配置类
 * @Date 2021-11-23
 * @Author J.zhu
 */
@Configuration
public class GatewayConfiguration {

    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public GatewayConfiguration(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        // Register the block exception handler for Spring Cloud Gateway.
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    /**
     * 	converter type now support xml or json.
     * 	The bean name of these converters wrapped by
     * 	'sentinel-{converterType}-{ruleType}-converter'
     * {@link SentinelDataSourceHandler#registerBean(AbstractDataSourceProperties, String)}
     * {@link com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule}
     * @return GatewayFlowRuleConvert
     */
    @Bean("sentinel-json-gw-flow-converter")
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GatewayFlowRuleConvert gatewayFlowRuleConvert(){
        return new GatewayFlowRuleConvert();
    }
}
