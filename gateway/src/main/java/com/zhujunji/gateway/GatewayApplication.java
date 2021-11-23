package com.zhujunji.gateway;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.zhujunji.gateway.config.GatewayBlockRequestHandler;
import com.zhujunji.gateway.config.GrayRoundLoadbalancerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;

@SpringBootApplication
@EnableDiscoveryClient
@LoadBalancerClients(value = {
        @LoadBalancerClient(value = "user-microservice-web", configuration = GrayRoundLoadbalancerConfig.class),
        @LoadBalancerClient(value = "base-microservice-web", configuration = GrayRoundLoadbalancerConfig.class),
})
public class GatewayApplication {

    public static void main(String[] args) {
        GatewayCallbackManager.setBlockHandler(new GatewayBlockRequestHandler());
        SpringApplication.run(GatewayApplication.class);
    }

}
