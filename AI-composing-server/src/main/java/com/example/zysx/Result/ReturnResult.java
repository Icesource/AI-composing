package com.example.zysx.Result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("返回信息")
public class ReturnResult<T> {

    /** 错误码. */
    @ApiModelProperty(value = "错误码",name = "code")
    private Integer code;

    /** 提示信息. */
    @ApiModelProperty(value = "提示信息",name = "msg")
    private String msg;

    /** 具体内容. */
    @ApiModelProperty(value = "具体内容",name = "data")
    private T data;

}
