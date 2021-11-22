package com.zhujunji.base.web.controller;

import com.zhujunji.base.service.BaseWorkItemDataService;
import com.zhujunji.common.request.BaseJSONObjectCreateRequest;
import com.zhujunji.common.response.Result;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workitem/{workItemId}")
public class BaseWorkItemDataController {

    @DubboReference(protocol = "dubbo", version = "1.0.0", check = false)
    private BaseWorkItemDataService baseWorkItemDataService;

    @PostMapping
    public Result<Boolean> create(@PathVariable(name = "workItemId") Long workItemId,
                                  @RequestBody BaseJSONObjectCreateRequest jsonObjectCreateRequest){
        boolean success = baseWorkItemDataService.create(workItemId,jsonObjectCreateRequest);
        return Result.newResult(success);
    }
}