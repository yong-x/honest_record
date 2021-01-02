package com.xy.honest_record.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.honest_record.common.vo.Code;
import com.xy.honest_record.common.vo.ResponseResult;
import com.xy.honest_record.entity.AccuseType;
import com.xy.honest_record.entity.ProblemField;
import com.xy.honest_record.service.IAccuseTypeService;
import com.xy.honest_record.service.IProblemFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 问题领域 前端控制器
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/problemField")
public class ProblemFieldController {

    @Autowired
    IProblemFieldService problemFieldService;

    @GetMapping("/query")
    public ResponseResult querylist(Integer pageNum, Integer pageSize, ProblemField problemField){
        QueryWrapper<ProblemField> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted",0);

        if(pageNum==null&&pageSize==null){ //非分页全部列表查询
            return ResponseResult.success(problemFieldService.list(wrapper));
        }


        wrapper.like("pf_name",problemField.getPfName());
        wrapper.orderByDesc("update_time","create_time");
        Page<ProblemField> page = new Page<>(pageNum,pageSize);
        page = problemFieldService.page(page,wrapper);

        long total = page.getTotal(); //总条数
        List<ProblemField> list = page.getRecords(); //当前页数据

        Map<String,Object> rmap = new HashMap<>();
        rmap.put("total",total);
        rmap.put("problemFieldList",list);
        return ResponseResult.success(rmap);
    }

    @PostMapping("/add")
    public ResponseResult add(@RequestBody ProblemField problemField){
        problemField.setDeleted(0);//未删除
        problemField.setCreateTime(new Date()); //创建时间
        problemField.setUpdateTime(new Date()); //修改时间
        boolean r = problemFieldService.save(problemField);
        if(r){
            return ResponseResult.success();
        }else{
            return ResponseResult.failure(Code.FAIL);
        }
    }

    @GetMapping("/query/{id}")//单个查询时可以查询出已经被删除了的对象，deleted=1
    public ResponseResult querybyId(@PathVariable("id") int id){
        return ResponseResult.success(problemFieldService.getById(id));
    }

    @PutMapping("/update")
    public ResponseResult update(@RequestBody ProblemField problemField){
        problemField.setUpdateTime(new Date()); //修改时间
        boolean r = problemFieldService.updateById(problemField);
        if(r){
            return ResponseResult.success();
        }else{
            return ResponseResult.failure(Code.FAIL);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseResult deletebyId(@PathVariable("id") int id){
        ProblemField problemField = problemFieldService.getById(id);
        if(problemField!=null){
            problemField.setDeleted(1); //软删除，删除操作变为更新操作
            boolean r = problemFieldService.updateById(problemField);
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

