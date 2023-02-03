package com.example.ruiji.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
/*@Configuration*/
public class MyHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getSession().getAttribute("employee") == null) {
            log.info("未登录跳转到登陆界面");
            request.getRequestDispatcher("/backend/page/login/login.html").forward(request, response);
            /*response.sendRedirect("/backend/page/login/login.html");*/
            return false;
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
