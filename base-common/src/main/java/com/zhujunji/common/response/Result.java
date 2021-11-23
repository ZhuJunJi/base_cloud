package com.zhujunji.common.response;

import com.zhujunji.common.exception.CommonBizException;
import com.zhujunji.common.exception.CommonSysException;

import java.io.Serializable;

/**
 * restful接口通用返回结果
 *
 * @Author J.zhu
 */
public class Result<T> implements Serializable {

    /**
     * 执行结果
     */
    private boolean isSuccess;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误原因
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 根据是否成功返回相应结果
     *
     * @param success 是否成功
     * @return Result<T>
     */
    public static <T> Result<T> newResult(boolean success) {
        return success ? newSuccessResult() : newFailureResult();
    }

    /**
     * 根据是否成功返回相应结果
     *
     * @param success 是否成功
     * @param message 信息
     * @return Result<T>
     */
    public static <T> Result<T> newResult(boolean success, String message) {
        return success ? newSuccessResult(message) : newFailureResult(message);
    }

    /**
     * 返回成功的结果
     *
     * @param <T>data 需返回的结果
     * @param message 返回信息
     * @return Result<T>
     */
    public static <T> Result<T> newSuccessResult(T data, String message) {
        Result<T> result = new Result<>();
        result.isSuccess = true;
        result.data = data;
        result.message = message;
        return result;
    }

    /**
     * 返回成功的结果
     *
     * @param data 需返回的结果
     * @return <T> Result<T>
     */
    public static <T> Result<T> newSuccessResult(T data) {
        Result<T> result = new Result<>();
        result.isSuccess = true;
        result.data = data;
        return result;
    }

    /**
     * 返回成功的结果
     *
     * @param message 成功信息
     * @return <T> Result<T>
     */
    public static <T> Result<T> newSuccessResult(String message) {
        Result<T> result = new Result<>();
        result.isSuccess = true;
        result.message = message;
        return result;
    }

    /**
     * 返回成功的结果
     *
     * @return <T> Result<T>
     */
    public static <T> Result<T> newSuccessResult() {
        Result<T> result = new Result<>();
        result.isSuccess = true;
        return result;
    }

    /**
     * 返回失败的结果
     *
     * @return <T> Result<T>
     */
    public static <T> Result<T> newFailureResult() {
        Result<T> result = new Result<>();
        result.isSuccess = false;
        return result;
    }

    /**
     * 返回失败的结果
     *
     * @param message 提示信息
     * @return <T> Result<T>
     */
    public static <T> Result<T> newFailureResult(String message) {
        Result<T> result = Result.newFailureResult();
        result.message = message;
        return result;
    }

    /**
     * 返回失败的结果
     *
     * @param message   提示信息
     * @param errorCode 错误码
     * @return <T> Result<T>
     */
    public static <T> Result<T> newFailureResult(String message, String errorCode) {
        Result<T> result = Result.newFailureResult(message);
        result.errorCode = errorCode;
        return result;
    }

    /**
     * 返回失败的结果
     *
     * @param commonBizException 异常
     * @return <T> Result<T>
     */
    public static <T> Result<T> newFailureResult(CommonBizException commonBizException) {
        Result<T> result = Result.newFailureResult();
        result.errorCode = commonBizException.getCode();
        result.message = commonBizException.getMessage();
        return result;
    }

    /**
     * 返回失败的结果
     *
     * @param commonBizException 异常
     * @param data               需返回的数据
     * @return <T> Result<T>
     */
    public static <T> Result<T> newFailureResult(CommonBizException commonBizException, T data) {
        Result<T> result = Result.newFailureResult(commonBizException);
        result.data = data;
        return result;
    }

    /**
     * 返回失败的结果
     *
     * @param commonSysException 异常
     * @return <T> Result<T>
     */
    public static <T> Result<T> newFailureResult(CommonSysException commonSysException) {
        Result<T> result = Result.newFailureResult();
        result.errorCode = commonSysException.getCodeEnum().getCode();
        result.message = commonSysException.getCodeEnum().getMessage();
        return result;
    }

    /**
     * 返回失败的结果
     *
     * @param commonSysException 异常
     * @param data               需返回的数据
     * @return <T> Result<T>
     */
    public static <T> Result<T> newFailureResult(CommonSysException commonSysException, T data) {
        Result<T> result = Result.newFailureResult(commonSysException);
        result.data = data;
        return result;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "isSuccess=" + isSuccess +
                ", errorCode=" + errorCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}

