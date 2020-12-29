package com.xy.honest_record.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.honest_record.common.vo.ResponseResult;
import com.xy.honest_record.entity.Faculty;
import com.xy.honest_record.entity.Power;
import com.xy.honest_record.mapper.PowerMapper;
import com.xy.honest_record.service.IPowerService;
import com.xy.honest_record.service.impl.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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




    @GetMapping("/query")
    public  ResponseResult querylist(int pageNum, int pageSize, Power power){
        QueryWrapper<Power> wrapper = new QueryWrapper<>();
        wrapper.like("p_name",power.getPName());

        Page<Power> page = powerService.getPowersByPage(pageNum,pageSize,wrapper);

        long total = page.getTotal(); //总条数
        long size = page.getSize();//每页多少条
        boolean hasNext = page.hasNext();//是否有下一页
        boolean hasPrevious =  page.hasPrevious();//是否有上一页
        List<Power> list = page.getRecords(); //当前页数据

        Map<String,Object> rmap = new HashMap<>();
        rmap.put("total",total);
        rmap.put("size",size);
        rmap.put("hasNext",hasNext);
        rmap.put("hasPrevious",hasPrevious);
        rmap.put("powerList",list);
        return ResponseResult.success(rmap);
    }

    @RequestMapping("/add")
    public Power addPower(){
        Power power = new Power();

        return power;
    }

    @RequestMapping("/update")
    public Power updatePower(){
        Power power = new Power();

        return power;
    }


    @GetMapping("/query/{rid}")
    public  ResponseResult queryAllInfoPowerByRid(@PathVariable("rid") Integer rid){ //查询单个角色的全部消息，以包含子权限列表的形式
        return ResponseResult.success(powerService.getAllInfoPowersByRid(rid));
    }

}

