package com.example.zysx.Controller;


import com.example.zysx.Result.ReturnResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(description = "文件接口")
public class testController {

    @GetMapping("/upload")
    @ApiOperation(value = "添加文件")
    public void upload() {

    }
}
