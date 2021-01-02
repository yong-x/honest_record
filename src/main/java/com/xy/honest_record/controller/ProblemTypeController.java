package com.xy.honest_record.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.honest_record.common.vo.Code;
import com.xy.honest_record.common.vo.ResponseResult;
import com.xy.honest_record.entity.ProblemType;
import com.xy.honest_record.service.IProblemTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 问题分类 前端控制器
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/problemType")
public class ProblemTypeController {

    @Autowired
    IProblemTypeService problemTypeService;

    @GetMapping("/query")
    public ResponseResult querylist(Integer pageNum, Integer pageSize, ProblemType problemType){
        QueryWrapper<ProblemType> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted",0);

        if(pageNum==null&&pageSize==null){ //非分页全部列表查询
            return ResponseResult.success(problemTypeService.list(wrapper));
        }



        wrapper.like("pt_name",problemType.getPtName());
        wrapper.orderByDesc("update_time","create_time");
        Page<ProblemType> page = new Page<>(pageNum,pageSize);
        page = problemTypeService.page(page,wrapper);

        long total = page.getTotal(); //总条数
        List<ProblemType> list = page.getRecords(); //当前页数据

        Map<String,Object> rmap = new HashMap<>();
        rmap.put("total",total);
        rmap.put("problemTypeList",list);
        return ResponseResult.success(rmap);
    }

    @PostMapping("/add")
    public ResponseResult add(@RequestBody ProblemType problemType){
        problemType.setDeleted(0);//未删除
        problemType.setCreateTime(new Date()); //创建时间
        problemType.setUpdateTime(new Date()); //修改时间
        boolean r = problemTypeService.save(problemType);
        if(r){
            return ResponseResult.success();
        }else{
            return ResponseResult.failure(Code.FAIL);
        }
    }

    @GetMapping("/query/{id}")
    public ResponseResult querybyId(@PathVariable("id") int ptId){
        return ResponseResult.success(problemTypeService.getById(ptId));
    }

    @PutMapping("/update")
    public ResponseResult update(@RequestBody ProblemType problemType){
        problemType.setUpdateTime(new Date()); //修改时间
        boolean r = problemTypeService.updateById(problemType);
        if(r){
            return ResponseResult.success();
        }else{
            return ResponseResult.failure(Code.FAIL);
        }
    }

    @DeleteMapping("/delete/{atid}")
    public ResponseResult deletebyId(@PathVariable("atid") int ptId){
        ProblemType problemType = problemTypeService.getById(ptId);
        if(problemType!=null){
            problemType.setDeleted(1); //软删除，删除操作变为更新操作
            boolean r = problemTypeService.updateById(problemType);
            if(r){
                return ResponseResult.success();
            }else{
                return ResponseResult.failure(Code.FAIL);
            }
        }else{
            return ResponseResult.failure(Code.USER_NOT_FOUND);
        }
    }
}

