package com.example.itachi.filter;

import com.example.itachi.entity.User;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;

@Slf4j
@WebFilter(urlPatterns = "/*",filterName = "urlFilter")
public class UrlFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String url = request.getRequestURI();
        log.info("【url】:{}",url);
        log.info("服务端地址IP：{}",InetAddress.getLocalHost().getHostAddress());
        log.info("服务端端口号：{}",request.getLocalPort());
        //暂不打印body里的数据，需要requestWrapper
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
