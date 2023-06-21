package com.mumi.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.mumi.reggie.common.BaseContext;
import com.mumi.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        log.info("拦截到请求 {}", request.getRequestURI());

        String requestURI = request.getRequestURI();

        String[] URLList = {
                "/employee/logout",
                "/employee/login",
                "/user/sendMsg",
                "/backend/**",
                "/front/**",
                "/common/**"
        };

        Boolean check = check(URLList, requestURI);

        if(check) {
            log.info("放行url {}", requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        Long emp =(Long) request.getSession().getAttribute("employee");
        if(emp != null) {
            BaseContext.setCurrentId(emp);
            log.info("已经登录的用户id {}", emp);
            filterChain.doFilter(request, response);
            return;
        }

        // 输出流的方式写数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));

    }

    public Boolean check(String[] urls, String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match) {
                return true;
            }
        }
        return false;
    }
}
