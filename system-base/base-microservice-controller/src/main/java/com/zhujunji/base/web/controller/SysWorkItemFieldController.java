package com.zhujunji.base.web.controller;

import com.zhujunji.base.service.SysWorkItemFieldService;
import com.zhujunji.base.service.dto.SysWorkItemFieldCreateDTO;
import com.zhujunji.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/workitem/field")
public class SysWorkItemFieldController {

    @DubboReference(protocol = "dubbo", version = "1.0.0")
    private SysWorkItemFieldService sysWorkItemFieldService;

    @PostMapping
    public Result<?> createWorkItem(@RequestBody SysWorkItemFieldCreateDTO sysWorkItemFieldCreateDTO){
        sysWorkItemFieldCreateDTO.setRequester(1L);
        sysWorkItemFieldCreateDTO.setCreateBy(1L);
        sysWorkItemFieldService.createWorkitemField(sysWorkItemFieldCreateDTO);
        return Result.newSuccessResult();
    }
}
