package com.xy.honest_record.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.honest_record.common.vo.Code;
import com.xy.honest_record.common.vo.ResponseResult;
import com.xy.honest_record.entity.Accusation;
import com.xy.honest_record.entity.Department;
import com.xy.honest_record.entity.Faculty;
import com.xy.honest_record.entity.Log;
import com.xy.honest_record.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 日志 前端控制器
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    ILogService logService;

    @GetMapping("/query")
    public ResponseResult querylist(Integer pageNum, Integer pageSize, Log log,
                                    String optDateStart,String optDateEnd){

        QueryWrapper<Log> wrapper = new QueryWrapper<>();


        if(log!=null&&log.getOperatorName()!=null&&log.getOperatorName().length()>0&&!log.getOperatorName().equals("所有")){
            wrapper.eq("operator_name",log.getOperatorName());
        }
        if(log!=null&&log.getOperatoerUserId()!=null){
            wrapper.like("operatoer_user_id",log.getOperatoerUserId());
        }
        //时间范围条件
        if(optDateStart!=null&&optDateStart.length()>0){
            wrapper.ge("operator_time",optDateStart);
        }
        if(optDateEnd!=null&&optDateEnd.length()>0){
            wrapper.le("operator_time",optDateEnd);
        }
        wrapper.orderByDesc("operator_time");


        Page<Log> page = new Page<>(pageNum,pageSize);
        page = logService.page(page,wrapper);

        long total = page.getTotal(); //总条数
        long size = page.getSize();//每页多少条
        boolean hasNext = page.hasNext();//是否有下一页
        boolean hasPrevious =  page.hasPrevious();//是否有上一页
        List<Log> list = page.getRecords(); //当前页数据

        Map<String,Object> rmap = new HashMap<>();
        rmap.put("total",total);
        rmap.put("logList",list);
        return ResponseResult.success(rmap);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseResult deletebyId(@PathVariable("id") int id, HttpServletRequest request){
        Log log = logService.getById(id);
        if(log!=null){
            boolean r = logService.removeById(log.getLId());
            if(r){
                return ResponseResult.success();//////////////////
            }else{
                return ResponseResult.failure(Code.FAIL);
            }
        }else{
            return ResponseResult.failure(Code.USER_NOT_FOUND);
        }
    }


}

