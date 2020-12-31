package com.xy.honest_record.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.honest_record.common.vo.Code;
import com.xy.honest_record.common.vo.ResponseResult;
import com.xy.honest_record.entity.Department;
import com.xy.honest_record.entity.ProblemField;
import com.xy.honest_record.entity.Role;
import com.xy.honest_record.service.IProblemFieldService;
import com.xy.honest_record.service.impl.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 部门 前端控制器
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;


    @GetMapping("/query")
    public ResponseResult querylist(Integer pageNum, Integer pageSize, Department department){
        if(pageNum==null&&pageSize==null){ //非分页全部列表查询
            return ResponseResult.success(departmentService.list());
        }

        QueryWrapper<Department> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted",0);
        wrapper.like("d_name",department.getDName());
        wrapper.orderByDesc("update_time","create_time");
        Page<Department> page = new Page<>(pageNum,pageSize);
        page = departmentService.page(page,wrapper);

        long total = page.getTotal(); //总条数
        List<Department> list = page.getRecords(); //当前页数据

        Map<String,Object> rmap = new HashMap<>();
        rmap.put("total",total);
        rmap.put("departmentList",list);
        return ResponseResult.success(rmap);
    }

    @PostMapping("/add")
    public ResponseResult add(@RequestBody Department department){
        department.setDeleted(0);//未删除
        department.setCreateTime(new Date()); //创建时间
        department.setUpdateTime(new Date()); //修改时间
        boolean r = departmentService.save(department);
        if(r){
            return ResponseResult.success();
        }else{
            return ResponseResult.failure(Code.FAIL);
        }
    }

    @GetMapping("/query/{id}") //单个查询时可以查询出已经被删除了的对象，deleted=1
    public ResponseResult querybyId(@PathVariable("id") int id){
        return ResponseResult.success(departmentService.getById(id));
    }

    @PutMapping("/update")
    public ResponseResult update(@RequestBody Department department){
        department.setUpdateTime(new Date()); //修改时间
        boolean r = departmentService.updateById(department);
        if(r){
            return ResponseResult.success();
        }else{
            return ResponseResult.failure(Code.FAIL);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseResult deletebyId(@PathVariable("id") int id){
        Department department = departmentService.getById(id);
        if(department!=null){
            department.setDeleted(1); //软删除，删除操作变为更新操作
            boolean r = departmentService.updateById(department);
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

