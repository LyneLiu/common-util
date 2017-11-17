package com.lyne.servlet;

import javax.servlet.*;
import java.io.IOException;

/**
 * 自定义Filter
 *
 * @author nn_liu
 * @Created 2017-07-20-16:54
 */

public class RegisteredFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("==>" + this.getClass().getSimpleName() + "启动!");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("==>" + this.getClass().getSimpleName() + "拦截请求!");
    }

    @Override public void destroy() {

    }
}
