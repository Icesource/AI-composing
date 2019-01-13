package com.example.zysx.Exception;

import com.example.zysx.Enum.ResultEnum;
import lombok.Getter;

@Getter
public class MyException extends RuntimeException {
    private Integer code;

    public MyException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public MyException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public MyException(String name, ResultEnum resultEnum) {
        super(name + resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }
}
