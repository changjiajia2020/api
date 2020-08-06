package com.fh.common.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TransdomainInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String yuming = request.getHeader("Origin");

        response.setHeader("Access-Control-Allow-Origin", yuming);
        response.setHeader("Access-Control-Allow-Methods","POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers","x-requested-with");
        response.setHeader("Access-Control-Allow-Credentials","true");
        //当客户端修改了头信息     发起两个请求  第一个是预请求   options （是否允许修改头信息）     发起真正的请求
        String method = request.getMethod();
        System.out.println(method);
        //
        if(method.equalsIgnoreCase("options")){
            //允许修改头信息  添加一个name属性
            response.setHeader("Access-Control-Allow-Headers","token");
            return false;
        }
        //从header 里去数据
        String name = request.getHeader("login_token");
        System.out.println(name);
        return true;
    }
}
