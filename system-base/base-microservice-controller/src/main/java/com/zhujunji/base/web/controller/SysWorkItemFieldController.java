package com.zhujunji.base.web.controller;

import com.zhujunji.base.service.SysWorkItemFieldService;
import com.zhujunji.base.service.dto.SysWorkItemFieldCreateDTO;
import com.zhujunji.base.service.dto.SysWorkItemFieldUpdateDTO;
import com.zhujunji.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/workitem/field")
public class SysWorkItemFieldController {

    @DubboReference(protocol = "dubbo", version = "1.0.0")
    private SysWorkItemFieldService sysWorkItemFieldService;

    @PostMapping
    public Result<?> create(@RequestBody SysWorkItemFieldCreateDTO sysWorkItemFieldCreateDTO){
        sysWorkItemFieldService.create(sysWorkItemFieldCreateDTO);
        return Result.newSuccessResult();
    }

    @PostMapping
    public Result<?> update(@RequestBody SysWorkItemFieldUpdateDTO sysWorkItemFieldUpdateDTO){
        sysWorkItemFieldService.update(sysWorkItemFieldUpdateDTO);
        return Result.newSuccessResult();
    }
}
