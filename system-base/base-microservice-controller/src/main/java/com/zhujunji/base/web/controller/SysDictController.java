package com.zhujunji.base.web.controller;

import com.zhujunji.base.service.SysDictService;
import com.zhujunji.base.service.dto.SysDictCreateDTO;
import com.zhujunji.base.service.dto.SysDictUpdateDTO;
import com.zhujunji.base.service.vo.SysDictVO;
import com.zhujunji.common.enums.LanguageEnum;
import com.zhujunji.common.response.Result;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import static com.zhujunji.common.constant.GlobalConstants.ACCEPT_LANGUAGE;

/**
 * 字典 Controller
 */
@RestController
@RequestMapping("/dict")
public class SysDictController {

    @DubboReference(protocol = "dubbo", version = "1.0.0")
    private SysDictService sysDictService;

    @GetMapping("/{dictId}")
    public Result<SysDictVO> getDictById(@PathVariable("dictId") Long dictId,
                                         @RequestHeader(ACCEPT_LANGUAGE) String acceptLanguage){
        LanguageEnum language = LanguageEnum.getByDescription(acceptLanguage,LanguageEnum.CHINESE);
        return Result.newSuccessResult(sysDictService.getById(dictId,language));
    }

    @PostMapping
    public Result<Boolean> createDict(@RequestBody SysDictCreateDTO sysDictCreateDTO){
        boolean success = sysDictService.save(sysDictCreateDTO);
        return Result.newResult(success);
    }

    @PutMapping
    public Result<Boolean> updateDict(@RequestBody SysDictUpdateDTO sysDictUpdateDTO){
        boolean success = sysDictService.update(sysDictUpdateDTO);
        return Result.newResult(success);
    }
}
