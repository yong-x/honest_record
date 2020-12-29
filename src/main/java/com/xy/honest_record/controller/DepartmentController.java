package com.xy.honest_record.controller;


import com.xy.honest_record.common.vo.ResponseResult;
import com.xy.honest_record.entity.Department;
import com.xy.honest_record.entity.Role;
import com.xy.honest_record.service.impl.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseResult getRoleList(@RequestParam(value = "dId",required = false) Integer dId){
        List<Department> departmentList = new ArrayList<>();
        if(dId==null){
            departmentList = departmentService.list();
        }else{
            departmentList.add(departmentService.getById(dId));
        }
        return ResponseResult.success(departmentList);
    }
}

