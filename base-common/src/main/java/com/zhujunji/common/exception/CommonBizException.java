package com.zhujunji.common.exception;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * 通用业务异常（由异常状态码区分不同的业务异常）
 *  @Author J.zhu
 */
public class CommonBizException extends RuntimeException implements Serializable {
    private String code;

    private String message;

    public CommonBizException(ExpCodeEnum codeEnum){
        super(codeEnum.getMessage());
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
    }

    public CommonBizException(ExpCodeEnum codeEnum, String message){
        super(codeEnum.getMessage() + "," + message);
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage() + "," + message;
    }

    public CommonBizException(String code, String message){
        super(message);
        this.code = code;
        this.message = message;
    }

    public CommonBizException(){}

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
