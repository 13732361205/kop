package com.github.kop.bbs.config;

import com.github.kop.bbs.Interceptor.TokenVerifyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @auth ahxiaoqi
 * @desc
 * @time 2022/8/9 23:23
 */
@Component
public class BbsWebConfig implements WebMvcConfigurer {

    @Resource
    private TokenVerifyInterceptor tokenVerifyInterceptor;

    /**
     * 拦截器注册
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenVerifyInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login","/user/create");
    }
}