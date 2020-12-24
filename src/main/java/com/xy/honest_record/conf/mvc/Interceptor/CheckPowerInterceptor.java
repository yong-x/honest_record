package com.xy.honest_record.conf.mvc.Interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//检查token中是否有相应权限

@Slf4j
public class CheckPowerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request);

        log.info("检查权限拦截器, CheckPowerInterceptor  preHandler.  RequestURI is"+request.getRequestURI());

        return true;
    }
}
