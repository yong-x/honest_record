package com.xy.honest_record.conf.mvc.Interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//检查请求头中是否有token,即该token是否正确

@Slf4j
public class CheckTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request);

        log.info("检查登录token拦截器, CheckTokenInterceptor.... preHandle.....");

        return true;
    }
}
