package com.xy.honest_record.conf.exception;


import com.xy.honest_record.common.vo.Code;
import com.xy.honest_record.common.vo.ResponseResult;
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
    public ResponseResult handleException(Exception e){
        log.error("全局异常处理器捕获服务器异常...");
        log.error(e.getMessage());
        e.printStackTrace(); //打印错误详情便于调试
        return ResponseResult.failure(Code.SERVER_EXECEPTION); //这里应该返回给浏览器异常原因
    }

}
