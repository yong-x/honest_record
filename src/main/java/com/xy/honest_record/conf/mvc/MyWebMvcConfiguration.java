package com.xy.honest_record.conf.mvc;

import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.xy.honest_record.conf.mvc.Interceptor.CheckPowerInterceptor;
import com.xy.honest_record.conf.mvc.Interceptor.CheckTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.List;

/*
* 全局 MVC 配置类
* */

@Configuration
@EnableWebMvc
public class MyWebMvcConfiguration implements WebMvcConfigurer {


    /*
    * 拦截器配置
    * */
    @Override
    public void addInterceptors(InterceptorRegistry registry) { //拦截器的执行顺序与加入顺序一致
        registry.addInterceptor(new CheckTokenInterceptor());
        registry.addInterceptor(new CheckPowerInterceptor());

    }

    /*
    * 添加时间格式转换器，封装参数时自动把前端request中 yyyy-MM-dd HH:mm:ss 格式字符串转换为 java类型 Date，
    * 返回json给前端时，把java类型Date转换为 yyyy-MM-dd HH:mm:ss 格式字符串。
    * */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                .indentOutput(true)
                .dateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                .modulesToInstall(new ParameterNamesModule());
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
    }

    /*
    * 全局允许跨域配置
    * */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowedHeaders("*")
                .maxAge(3600)
                .allowCredentials(true);
    }


}
