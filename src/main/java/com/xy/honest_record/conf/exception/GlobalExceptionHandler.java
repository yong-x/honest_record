package com.xy.honest_record.conf.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
* 全局异常处理器
* */

@Slf4j
@RestControllerAdvice //所有 RestController 增强器，不能处理 Controller
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e){
        log.error(e.getMessage());
        e.printStackTrace(); //打印错误详情便于调试

        return e.getMessage(); //这里应该返回给浏览器异常原因
    }

}
