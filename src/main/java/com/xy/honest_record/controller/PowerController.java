package com.xy.honest_record.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.honest_record.common.vo.ResponseResult;
import com.xy.honest_record.entity.Power;
import com.xy.honest_record.service.IPowerService;
import com.xy.honest_record.service.impl.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/power")
public class PowerController {

    @Autowired
    IPowerService powerService;

    @RequestMapping("/hello")
    public  ResponseResult<List<Power>> hello(){
        ResponseResult<List<Power>> responseResult = ResponseResult.success(powerService.list());
        return responseResult;
    }

    @RequestMapping("/{page}")
    public List<Power> hello1(@PathVariable("page") Integer curpage){

        List<Power> powers = powerService.getPowerByPageNumber(curpage).getRecords();
        return powers;
    }

    @RequestMapping("/add")
    public Power addPower(){
        Power power = new Power();
        power.setPId("900");
        power.setPName("测试addPower");
        power.setActionPath("/test/url");
        powerService.save(power);
        return power;
    }

    @RequestMapping("/update")
    public Power updatePower(){
        Power power = new Power();
        power.setPId("900");
        power.setPName("测试updatePower");
        power.setActionPath("/test/url");

        powerService.updateById(power);
        return power;
    }


    @RequestMapping("/hello1/{pid}")
    public Power hello2(@PathVariable(value = "pid") int pid){
        System.out.println(pid);
        return powerService.getPowerById(pid);
    }

}

