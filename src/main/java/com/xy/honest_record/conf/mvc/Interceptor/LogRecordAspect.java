package com.xy.honest_record.conf.mvc.Interceptor;

import cn.hutool.json.JSONUtil;
import com.xy.honest_record.common.util.JWTutil;
import com.xy.honest_record.common.vo.Code;
import com.xy.honest_record.common.vo.ResponseResult;
import com.xy.honest_record.common.vo.TokenPayload;
import com.xy.honest_record.entity.Accusation;
import com.xy.honest_record.entity.Faculty;
import com.xy.honest_record.entity.Log;
import com.xy.honest_record.service.ILogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

/***
 *
 *拦截所有控制器的返回，记录登录日志和廉洁档案操作日志
 */
@Aspect
@Component
@Slf4j
public class LogRecordAspect{

    @Autowired
    @Qualifier(value = "crawlExecutorPool")
    private ExecutorService pool;//线程池执行日志记录的异步任务


    @Autowired
    ILogService LogService;




    //获取客户端ip地址的方法
    private String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }

    //登录操作切入点,只切入login方法
    @Pointcut("execution(public com.xy.honest_record.common.vo.ResponseResult com.xy.honest_record.controller.FacultyController.login(..))")
    public void loginPointCutMethod() {
    }
    //档案更新操作切入点，要切入add、update、delete方法
    @Pointcut("execution(public com.xy.honest_record.common.vo.ResponseResult com.xy.honest_record.controller.AccusationController.*(..))")
    public void accusationPointCutMethod() {
    }

    // 登录成功的日志记录代理方法
    @Around("loginPointCutMethod()")
    public Object loginSuccessLogging(ProceedingJoinPoint pjp) throws Throwable {
        Object object = pjp.proceed();
        ResponseResult<Map> ret = null;
        try{
            ret = (ResponseResult<Map>)object;
        }catch (Exception e){
            return object;
        }
        //登录成功则记录日志
        if(Objects.equals( ret.getCode(),Code.SUCCESS.code())){
            //登录用户
            Faculty loginFaculty = ((TokenPayload)ret.getData().get("payload")).getFaculty();
            log.info("登录成功！");
            //异步记录登录日志
            pool.execute(()->{
                Log log = new Log();
                log.setOperatoerUserId(loginFaculty.getUserId());
                log.setOperatorName("用户登录");
                log.setOperatorTime(new Date());
                log.setLogType(loginFaculty.getUserId().toString());
                log.setHostIp(getClientIp((HttpServletRequest)pjp.getArgs()[1]));
                LogService.save(log);
            });
        }
        return object;
    }

    // 档案更新成功的日志记录代理方法
    @Around("accusationPointCutMethod()")
    public Object accusationSuccessLogging(ProceedingJoinPoint pjp) throws Throwable {
        Object object = pjp.proceed();
        ResponseResult ret = null;
        try{
            ret = (ResponseResult)object;
        }catch (Exception e){
            return object;
        }
        //1、操作失败则不记录日志
        if(!Objects.equals( ret.getCode(),Code.SUCCESS.code())){
            return object;
        }
        //2、操作成功开始记录日志
        //获取目标方法名称,若为添加、更新、删除方法，则记录日志
        String method = pjp.getSignature().getName();
        if(method.contains("add")||method.contains("update")||method.contains("delete")) {
            //获取目标方法第一个参数
            Object arg = pjp.getArgs()[0];
            //获取目标方法第二个参数，目标方法必须有第二个参数HttpServletRequest，即add，update,delete必须有第二个参数HttpServletRequest
            HttpServletRequest request = (HttpServletRequest) pjp.getArgs()[1];
            //从HttpServletRequest中获取请求头以查询当前请求用户
            String tokenStr = request.getHeader("Authorization");
            Faculty optFaculty = JWTutil.getPayLoadFromToken(tokenStr, TokenPayload.class).getFaculty();
            //从返回结果中获取操作资源
            Accusation optAccusation = (Accusation) ret.getData();

            //2.1 添加廉洁档案日志
            if (method.contains("add")) {
                log.info("添加廉洁档案成功！");
                pool.execute(() -> {
                    Log log = new Log();
                    log.setOperatoerUserId(optFaculty.getUserId());
                    log.setOperatorName("添加廉洁档案");
                    log.setOperatorTime(new Date());
                    log.setLogType(optAccusation.getAId().toString());
                    log.setHostIp(getClientIp(request));
                    LogService.save(log);
                });


                //2.2 更新廉洁档案日志
            } else if (method.contains("update")) {
                log.info("更新廉洁档案成功！");
                log.error("update成功的档案为 " + (Accusation) arg);
                pool.execute(() -> {
                    Log log = new Log();
                    log.setOperatoerUserId(optFaculty.getUserId());
                    log.setOperatorName("更新廉洁档案");
                    log.setOperatorTime(new Date());
                    log.setLogType(optAccusation.getAId().toString());
                    log.setHostIp(getClientIp(request));
                    LogService.save(log);
                });


                //2.3 删除廉洁档案日志
            } else if (method.contains("delete")) {
                log.info("删除廉洁档案成功！");
                pool.execute(() -> {
                    Log log = new Log();
                    log.setOperatoerUserId(optFaculty.getUserId());
                    log.setOperatorName("删除廉洁档案");
                    log.setOperatorTime(new Date());
                    log.setLogType(optAccusation.getAId().toString());
                    log.setHostIp(getClientIp(request));
                    LogService.save(log);
                });

            }
        }
        return object;
    }



}
