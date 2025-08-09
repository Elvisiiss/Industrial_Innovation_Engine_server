package com.IIE.Industrial_Innovation_Engine_server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 开发环境配置
        String resourcePath = "src/main/resources/pictures/";
        String absolutePath = new File(System.getProperty("user.dir"), resourcePath).getAbsolutePath() + "/";

        // 映射URL路径到实际文件目录
        registry.addResourceHandler("/pictures/**")
                .addResourceLocations("file:" + absolutePath);
    }
}
