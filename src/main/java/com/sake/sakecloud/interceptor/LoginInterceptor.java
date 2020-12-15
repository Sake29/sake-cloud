package com.sake.sakecloud.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 * 防止在未登录的情况下访问数据
 *
 * @author WSY
 * @date 2020/12/15
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("userName") != null){
            return true;
        }
        //跳转到login界面
        response.sendRedirect(request.getContextPath()+"/login");
        return false;
    }
}
