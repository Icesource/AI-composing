package com.example.zysx.Exception;

import com.example.zysx.Enum.ResultEnum;
import com.example.zysx.Result.ReturnResult;
import com.example.zysx.Utils.ReturnResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ReturnResult Handle(Exception e) {
        if (e instanceof MyException) {
            MyException myException = (MyException) e;
            log.info("[异常]{}", myException.getMessage());
            return ReturnResultUtil.error(myException.getCode(), myException.getMessage());
        } else {
            log.info("[其他异常]{}", e);
            return ReturnResultUtil.error(ResultEnum.ERROR);
        }

    }
}
