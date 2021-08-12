package com.zhujunji.common.enums;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

/**
 * 操作类型枚举
 */
public enum OperationTypeEnum implements Serializable {

    CREATE(0,"CREATE"),
    UPDATE(1,"UPDATE"),
    DELETE(2,"DELETE");

    /**
     * 编号
     */
    private final Integer code;
    /**
     * 描述
     */
    private final String description;

    OperationTypeEnum(Integer code, String description){
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static Optional<OperationTypeEnum> getByCode(Integer code){
        return Arrays.stream(OperationTypeEnum.values())
                .filter(operationTypeEnum -> operationTypeEnum.getCode().equals(code))
                .findFirst();
    }
}
