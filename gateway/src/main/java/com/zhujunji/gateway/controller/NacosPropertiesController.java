package com.zhujunji.gateway.controller;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.client.config.NacosConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/nacos/config")
public class NacosPropertiesController {

    @Resource
    private NacosConfigManager nacosConfigManager;

    @GetMapping
    public String getConfig() throws NacosException {
        String content = nacosConfigManager.getConfigService().getConfig("gateway-sentinel.json","gateway",3000L);
        return content;
    }


}
