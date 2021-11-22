package com.zhujunji.base.web.controller;

import com.zhujunji.base.service.SysWorkItemFieldService;
import com.zhujunji.base.service.dto.SysWorkItemFieldCreateDTO;
import com.zhujunji.base.service.dto.SysWorkItemFieldUpdateDTO;
import com.zhujunji.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/workitem/field")
public class SysWorkItemFieldController {

    @DubboReference(protocol = "dubbo", version = "1.0.0", check = false)
    private SysWorkItemFieldService sysWorkItemFieldService;

    @PostMapping
    public Result<?> create(@RequestBody SysWorkItemFieldCreateDTO sysWorkItemFieldCreateDTO){
        sysWorkItemFieldService.create(sysWorkItemFieldCreateDTO);
        return Result.newSuccessResult();
    }

    @PutMapping
    public Result<?> update(@RequestBody SysWorkItemFieldUpdateDTO sysWorkItemFieldUpdateDTO){
        sysWorkItemFieldService.update(sysWorkItemFieldUpdateDTO);
        return Result.newSuccessResult();
    }

    @Resource
    private Environment environment;

    @GetMapping("/hello")
    public Result<String> hello(){
        String port = environment.getProperty("server.port",String.class);
        return Result.newSuccessResult("127.0.0.1:" + port + "提供服务");
    }
}
