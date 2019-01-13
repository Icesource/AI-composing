package com.example.zysx.Enum;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(00000, "成功"),

    ERROR(11111, "失败"),

    FILE_NULL(10001, "文件不能为空"),

    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
