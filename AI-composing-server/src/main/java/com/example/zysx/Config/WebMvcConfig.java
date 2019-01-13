package com.example.zysx.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//虚拟地址映射
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    public static String Dir;

    @Value("${com.example.zysx.dir}")
    public void setDir(String dir) {
        Dir = dir;
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //文件磁盘图片url 映射
        //配置server虚拟路径，handler为前台访问的目录，locations为files相对应的本地路径
        registry.addResourceHandler("/resource/**").addResourceLocations("file:" + Dir);
    }
}
