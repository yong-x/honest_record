package com.xy.honest_record.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestExceptionController {

    @RequestMapping("/excep")
    public String get(){
        int i=1/0;
        return "TestExceptionController";
    }
}
