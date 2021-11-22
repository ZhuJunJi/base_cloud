package com.zhujunji.base.web.controller;

import com.zhujunji.base.service.SysWorkItemFieldService;
import com.zhujunji.base.service.SysWorkItemService;
import com.zhujunji.base.service.dto.SysWorkItemCreateDTO;
import com.zhujunji.base.service.vo.SysWorkItemVO;
import com.zhujunji.common.enums.LanguageEnum;
import com.zhujunji.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import static com.zhujunji.common.constant.GlobalConstants.ACCEPT_LANGUAGE;

@Slf4j
@RestController
@RequestMapping("/workitem")
public class SysWorkItemController {


    @DubboReference(protocol = "dubbo", version = "1.0.0", check = false)
    private SysWorkItemService sysWorkItemService;

    @DubboReference(protocol = "dubbo", version = "1.0.0", check = false)
    private SysWorkItemFieldService sysWorkItemFieldService;

    @PostMapping
    public Result<Boolean> createWorkItem(@RequestBody SysWorkItemCreateDTO sysWorkItemCreateDTO){
        boolean success = sysWorkItemService.createWorkitem(sysWorkItemCreateDTO);
        return Result.newResult(success);
    }

    @GetMapping("/{workItemId}")
    public Result<SysWorkItemVO> getWorkItem(@PathVariable("workItemId") Long workItemId,
                                             @RequestHeader(ACCEPT_LANGUAGE) String acceptLanguage){
        LanguageEnum language = LanguageEnum.getByDescription(acceptLanguage,LanguageEnum.CHINESE);
        return Result.newSuccessResult(sysWorkItemService.getById(workItemId,language));
    }
}
