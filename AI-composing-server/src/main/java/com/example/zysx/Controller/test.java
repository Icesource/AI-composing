package com.example.zysx.Controller;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


@Slf4j
public class test {

    public static void main(String[] args) {

        Process project = null;
        String cmd = "cmd /k cd C:/Users/xxdn/Desktop/item/Java/zysx/Python/AI_composing && D:/Python/Python3.6.7/python test.py";
        log.info(cmd);

        try {
            log.info("开始生成音乐");
            project = Runtime.getRuntime().exec(cmd);
            InputStream br = project.getInputStream();
            InputStream br_error = project.getErrorStream();
            new Thread() {
                public void run() {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(br));
                    try {
                        String line1 = null;
                        while ((line1 = br1.readLine()) != null && !line1.equals("")) {
                            if (line1 != null) {
                                log.info("getInputStream: " + line1);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
//            new Thread() {
//                public void run() {
//                    BufferedReader br2 = new BufferedReader(new InputStreamReader(br_error));
//                    try {
//                        String line2 = null;
//                        while ((line2 = br2.readLine()) != null && !line2.equals("")) {
//                            if (line2 != null) {
//                                log.info("getErrorStream: " + line2);
//                            }
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } finally {
//                        try {
//                            br_error.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }.start();

//            try {
//                log.info("等待python脚本运行");
//                int exitcode = project.waitFor();
//                project.destroy();
//                log.info("运行完毕：" + String.valueOf(exitcode));
//                if (exitcode == 0) {
//                    log.info("============ is Passed============");
//                } else {
//                    Boolean resultFlag = false;
//                    log.info("============ is Failed============");
//                }
//            } catch (Throwable e) {
//                e.printStackTrace();
//            }

        } catch (IOException e) {
            e.printStackTrace();
            try {
                project.getErrorStream().close();
                project.getInputStream().close();
                project.getOutputStream().close();
            } catch (Exception ee) {
            }
        }
        log.info("正在生成音乐");
//        String cmd = "cmd /k cd C:/Users/xxdn/Desktop/item/Java/zysx/Python/AI_composing && D:/Python/Python3.6.7/python test.py";
//        Process project = null;
//        String result;
//        try {
//            log.info("开始执行命令");
//            Process process = Runtime.getRuntime().exec(cmd);
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            InputStreamReader error = new InputStreamReader(process.getErrorStream());
//
//            String line = null;
//
//            while ((line = bufferedReader.readLine()) != null && !line.equals("")) {
//                if (line != null && line != "") {
//                    log.info("getInputStream: " + line);
//                }
//            }
//            log.info("输出结束");
//            bufferedReader.close();
//        } catch (IOException e) {
//            log.error("调用python脚本并读取结果时出错：" + e.getMessage());
//        }
    }
}
