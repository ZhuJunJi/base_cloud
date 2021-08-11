package com.zhujunji.base.web.controller;

import com.zhujunji.base.service.SysFiledService;
import com.zhujunji.base.service.dto.SysFieldCreateDTO;
import com.zhujunji.base.service.dto.SysFieldUpdateDTO;
import com.zhujunji.base.service.vo.SysFieldVO;
import com.zhujunji.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ResponseBody
@RestController
@RequestMapping("/field")
public class SysFieldController {

    @DubboReference(protocol = "dubbo", version = "1.0.0")
    private SysFiledService sysFiledService;

    @GetMapping("/{fieldId}")
    public Result<SysFieldVO> getFieldById(@PathVariable("fieldId") Long fieldId){
        return Result.newSuccessResult(sysFiledService.getById(fieldId));
    }

    @PostMapping
    public Result<?> create(@RequestBody SysFieldCreateDTO sysFieldCreateDTO){
        boolean success = sysFiledService.create(sysFieldCreateDTO);
        return Result.newResult(success);
    }

    @PutMapping
    public Result<?> update(@RequestBody SysFieldUpdateDTO sysFieldUpdateDTO){
        boolean success = sysFiledService.update(sysFieldUpdateDTO);
        return Result.newResult(success);
    }
}
