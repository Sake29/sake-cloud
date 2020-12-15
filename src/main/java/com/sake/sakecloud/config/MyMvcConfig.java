package com.sake.sakecloud.config;

import com.sake.sakecloud.interceptor.LoginInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 自定义mvc配置
 * 用于注册拦截器
 *
 * @author WSY
 * @date 2020/12/15
 */
@Component
public class MyMvcConfig implements WebMvcConfigurer {

    /**
     * 权限白名单
     */
    private static final String[] AUTH_WHITELIST = {
            //放行样式、脚本、图片等资源文件
            "/css/**","/js/**","/images/**","/fonts/**",
            //放行登录界面和接口
            "/login.html","/login",
            //放行注册界面
            "/register.html","/register",
            //放行404界面
            "/404.html"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns(AUTH_WHITELIST)
                .addPathPatterns("/**");
    }

}
