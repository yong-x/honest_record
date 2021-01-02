package com.xy.honest_record.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.honest_record.common.vo.Code;
import com.xy.honest_record.common.vo.ResponseResult;
import com.xy.honest_record.entity.AccuseType;
import com.xy.honest_record.entity.Power;
import com.xy.honest_record.service.IAccusationService;
import com.xy.honest_record.service.IAccuseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 举报类型 前端控制器
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/accuseType")
public class AccuseTypeController {

    @Autowired
    IAccuseTypeService accuseTypeService;

    @GetMapping("/query")
    public ResponseResult querylist(Integer pageNum, Integer pageSize, AccuseType accuseType){
        QueryWrapper<AccuseType> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted",0);

        if(pageNum==null&&pageSize==null){ //非分页全部列表查询
            return ResponseResult.success(accuseTypeService.list(wrapper));
        }



        wrapper.like("at_name",accuseType.getAtName());
        wrapper.orderByDesc("update_time","create_time");
        Page<AccuseType> page = new Page<>(pageNum,pageSize);
        page = accuseTypeService.page(page,wrapper);

        long total = page.getTotal(); //总条数
        List<AccuseType> list = page.getRecords(); //当前页数据

        Map<String,Object> rmap = new HashMap<>();
        rmap.put("total",total);
        rmap.put("accuseTypeList",list);
        return ResponseResult.success(rmap);
    }

    @PostMapping("/add")
    public ResponseResult add(@RequestBody AccuseType accuseType){
        accuseType.setDeleted(0);//未删除
        accuseType.setCreateTime(new Date()); //创建时间
        accuseType.setUpdateTime(new Date()); //修改时间
        boolean r = accuseTypeService.save(accuseType);
        if(r){
            return ResponseResult.success();
        }else{
            return ResponseResult.failure(Code.FAIL);
        }
    }

    @GetMapping("/query/{atid}")//单个查询时可以查询出已经被删除了的对象，deleted=1
    public ResponseResult querybyId(@PathVariable("atid") int atId){
        return ResponseResult.success(accuseTypeService.getById(atId));
    }

    @PutMapping("/update")
    public ResponseResult update(@RequestBody AccuseType accuseType){
        accuseType.setUpdateTime(new Date()); //修改时间
        boolean r = accuseTypeService.updateById(accuseType);
        if(r){
            return ResponseResult.success();
        }else{
            return ResponseResult.failure(Code.FAIL);
        }
    }

    @DeleteMapping("/delete/{atid}")
    public ResponseResult deletebyId(@PathVariable("atid") int atId){
        AccuseType accuseType = accuseTypeService.getById(atId);
        if(accuseType!=null){
            accuseType.setDeleted(1); //软删除，删除操作变为更新操作
            boolean r = accuseTypeService.updateById(accuseType);
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

