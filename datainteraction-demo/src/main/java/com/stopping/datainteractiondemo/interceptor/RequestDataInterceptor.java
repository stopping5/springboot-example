package com.stopping.datainteractiondemo.interceptor;

import com.stopping.datainteractiondemo.model.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.RequestWrapper;

/**
 * 请求接口数据拦截器
 * */
public class RequestDataInterceptor implements HandlerInterceptor {
    /**
     * 请求前拦截
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("请求拦截");
        String token = request.getHeader("token");
        String requestBody = request.getParameter("requestBody");
        System.out.println("获取token:"+token);
        System.out.println("获取request:");
        User user = new User();
        user.setName("xdp");
        user.setPassword("1234567");
        request.setAttribute("user",user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
