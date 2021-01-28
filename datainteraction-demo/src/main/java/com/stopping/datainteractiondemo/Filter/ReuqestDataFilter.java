package com.stopping.datainteractiondemo.Filter;


import com.stopping.datainteractiondemo.model.User;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
@Component
public class ReuqestDataFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
       User user = new User();
       user.setName("xdp");
       user.setPassword("123");
       servletRequest.setAttribute("user",user);
       filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
