package com.example.zysx.Utils;


import com.example.zysx.Enum.ResultEnum;
import com.example.zysx.Result.ReturnResult;

public class ReturnResultUtil {

    public static ReturnResult success(Object object){
        ReturnResult returnResult = new ReturnResult();
        returnResult.setData(object);
        returnResult.setCode(0);
        returnResult.setMsg("成功");

        return returnResult;
    }

    public static ReturnResult success(){
        return success(null);
    }

    public static ReturnResult error(Integer code, String msg) {
        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(code);
        returnResult.setMsg(msg);
        return returnResult;
    }

    public static ReturnResult error(ResultEnum resultEnum) {
        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(resultEnum.getCode());
        return returnResult;
    }

}
