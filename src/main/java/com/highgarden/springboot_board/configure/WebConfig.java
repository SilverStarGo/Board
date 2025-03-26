package com.highgarden.springboot_board.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // resourcePath : 요청이 들어오는 url패턴, 즉 클라이언트로부터 들어오는 요청
    private String resourcePath = "/upload/**";

    // savePath : 실제 파일이 저장될 경로, 절대경로로 작성
    private String savePath = "file:///D:/Board/springboot-board/src/main/resources/upload_files/";
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcePath)
                .addResourceLocations(savePath);
    }
}
