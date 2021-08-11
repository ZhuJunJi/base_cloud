package com.zhujunji.base.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BaseServerWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseServerWebApplication.class, args);
    }
}
