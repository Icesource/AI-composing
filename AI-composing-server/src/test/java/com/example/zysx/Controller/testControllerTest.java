package com.example.zysx.Controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class testControllerTest {

    @Test
    public void upload() {
        String cmd = "cmd /k cd C:/Users/xxdn/Desktop/item/Java/zysx/Python/AI_composing && D:/Python/Python3.6.7/python test.py";
        Process project = null;
        String result;
        try {
            log.info("开始执行命令");
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            InputStreamReader error = new InputStreamReader(process.getErrorStream());
            String line = null;
            while ((line = bufferedReader.readLine()) != null && !line.equals("")) {
                log.info("当前line的取值:" + line);
                if (line != null) {
                    log.info("getInputStream: " + line);
                }
                if (line == null) {
                    log.info("null");
                }
            }
            log.info("输出结束");
            bufferedReader.close();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            log.error("调用python脚本并读取结果时出错：" + e.getMessage());
        }
    }
}