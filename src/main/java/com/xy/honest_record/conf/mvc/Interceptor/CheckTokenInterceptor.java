package com.xy.honest_record.conf.mvc.Interceptor;

import cn.hutool.json.JSONUtil;
import com.xy.honest_record.common.util.JWTutil;
import com.xy.honest_record.common.vo.Code;
import com.xy.honest_record.common.vo.ResponseResult;
import com.xy.honest_record.common.vo.TokenPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

//检查请求头中是否有token,即该token是否正确

@Slf4j
public class CheckTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("检查登录token拦截器, CheckTokenInterceptor preHandle.  RequestURI is"+request.getRequestURI());
        //当DispatcherServlet 出错时（如找不到对应处理器报404），会把对应 /error 请求交给本拦截器
        //当DispatcherServlet 找到对应处理器时，会把真正对应 请求路径 交给本拦截器，
        // 所以 requestURI 可能为 /error 或者 真实客户端请求路径
        String requestURI = request.getRequestURI();
        //1、本拦截器不处理 /error 请求，会直接把 404 等错误抛给客户端
        if(requestURI.equals("/error") || request.getMethod().equals("OPTIONS")){
            return true;
        }

        //对于下面的请求无法验证token暂不拦截
        List<String> noInterceptePathList = Arrays.asList("/analysis/download","/faculty/download");
        if(noInterceptePathList.contains(requestURI)){
            return true;
        }

        String tokenStr = request.getHeader("Authorization");
        //2、请求头中没有token,且当前请求路径不是登录则拦截请求，返回未登录错误
        if(tokenStr==null && !requestURI.equals("/faculty/login")){
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-type","application/json");
            ResponseResult  responseResult = ResponseResult.failure(Code.UNAUTHENTICATED);
            response.getWriter().write(JSONUtil.toJsonStr(responseResult));
            return false;
        }else{//3、请求头中有 token，或者当前为登录，则直接放行
            return true;
        }
    }
}
