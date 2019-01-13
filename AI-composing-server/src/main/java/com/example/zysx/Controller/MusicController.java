package com.example.zysx.Controller;

import com.example.zysx.Enum.ResultEnum;
import com.example.zysx.Result.ReturnResult;
import com.example.zysx.Utils.ReturnResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/music")
@Slf4j
@Api(description = "文件接口")
public class MusicController {

    public static String tempDir = "E:/";

    @PostMapping("/upload")
    @ApiOperation(value = "添加文件")
    public ReturnResult upload(@RequestParam("file") MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.getSize() == 0) {
            return ReturnResultUtil.error(ResultEnum.FILE_NULL);
        }
        String saveURL = tempDir + multipartFile.getOriginalFilename();
        try {
            log.info("开始写入本地磁盘/服务器");
            InputStream is = multipartFile.getInputStream();
            OutputStream os = new FileOutputStream(new File(saveURL));
            int len = 0;
            byte[] buffer = new byte[2048];
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            os.close();
            os.flush();
            is.close();
            log.info("临时路径写入成功:" + saveURL);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String dir = System.getProperty("user.dir");
        log.info("dir:" + dir);
        Process project = null;
        String cmd = "cmd /k cd C:/Users/xxdn/Desktop/item/Java/zysx/Python/AI_composing && D:/Python/Python3.6.7/python generate.py " + saveURL;
        log.info(cmd);

        String musicDir = null;

        try {
            log.info("开始生成音乐");
            project = Runtime.getRuntime().exec(cmd);
            InputStream br = project.getInputStream();
            InputStream br_error = project.getErrorStream();
            BufferedReader br1 = new BufferedReader(new InputStreamReader(br));
            String line1 = null;
            while ((line1 = br1.readLine()) != null && !line1.equals("")) {
                if (line1 != null) {
                    if (line1.contains("E:/output/")) {
                        musicDir = line1;
                    }
                    log.info("getInputStream: " + line1);
                }
            }
//            new Thread() {
//                public void run() {
//                    BufferedReader br1 = new BufferedReader(new InputStreamReader(br));
//                    try {
//                        String line1 = null;
//                        while ((line1 = br1.readLine()) != null && !line1.equals("")) {
//                            if (line1 != null) {
//                                log.info("getInputStream: " + line1);
//                            }
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } finally {
//                        try {
//                            br.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }.start();

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
//                int exitcode = project.waitFor();
//                project.destroy();
//                log.info(String.valueOf(exitcode));
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
        log.info("生成音乐完毕");
        musicDir= musicDir.replace("E:/output","/resource");
        return ReturnResultUtil.success(musicDir);
    }
}
