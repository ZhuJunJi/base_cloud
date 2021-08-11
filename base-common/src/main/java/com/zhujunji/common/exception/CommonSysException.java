package com.zhujunji.common.exception;

/**
 * 通用系统异常
 *  @author J.zhu
 */
public class CommonSysException extends RuntimeException {

    /**
     * 异常枚举
     */
    private ExpCodeEnum codeEnum;

    public CommonSysException(ExpCodeEnum codeEnum) {
        super(codeEnum.getMessage());
        this.codeEnum = codeEnum;
    }

    public CommonSysException() {

    }

    public ExpCodeEnum getCodeEnum() {
        return codeEnum;
    }
}
