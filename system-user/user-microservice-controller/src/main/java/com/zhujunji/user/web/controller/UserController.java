package com.zhujunji.user.web.controller;


import com.zhujunji.common.response.Result;
import com.zhujunji.user.dto.SysUserDTO;
import com.zhujunji.user.service.SysUserService;
import com.zhujunji.user.vo.SysUserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @Author J.zhu
 * @Date 2019/7/11
 */
@Slf4j
@Service
@RestController
@RequestMapping("/user")
public class UserController {

    @DubboReference(version = "1.0.0",timeout = 10000, check = false)
    private SysUserService userService;

    @Resource
    private Environment environment;

    @GetMapping("/hello")
    public Result<String> hello(){
        String port = environment.getProperty("server.port",String.class);
        return Result.newSuccessResult("127.0.0.1:" + port + "提供服务");
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ADD')")
    public Result<Boolean> save(@RequestBody SysUserVO user) {
        return Result.newSuccessResult(userService.save(user));
    }

    @GetMapping("/get/{userId}")
    @PreAuthorize("hasAnyRole('VIEW')")
    public Result<SysUserDTO> get(@PathVariable("userId") Long userId){
        SysUserDTO sysUserDTO = userService.getById(userId);
        return Result.newSuccessResult(sysUserDTO);
    }

    /**
     * 获取所有用户列表
     * @return Result<List<SysUser>>
     */
    @GetMapping("/findList")
    @PreAuthorize("hasAnyRole('VIEW')")
    public Result<List<SysUserDTO>> findList() {
        return Result.newSuccessResult(userService.findList());
    }

    @GetMapping("/token")
    public Object get(Authentication authentication){

        Object p = authentication.getPrincipal();
        Object object = authentication.getCredentials();
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)authentication.getDetails();
        return details.getTokenValue();
    }
}
