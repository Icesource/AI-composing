package com.example.zysx.Controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MusicControllerTest {

    @Test
    public void upload() {
        String dir = System.getProperty("user.dir");
        log.info("dir:" + dir);
        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec(
                    "cmd /k cd C:/Users/xxdn/Desktop/item/Java/zysx/Python/AI_composing && D:/Python/Python3.6.7/python generate.py E:/5.mid");
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}