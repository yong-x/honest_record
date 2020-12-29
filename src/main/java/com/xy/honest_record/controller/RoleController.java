package com.xy.honest_record.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xy.honest_record.common.vo.ResponseResult;
import com.xy.honest_record.entity.Role;
import com.xy.honest_record.service.IPowerService;
import com.xy.honest_record.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    IRoleService roleService;

    @Autowired
    IPowerService powerService;

    @GetMapping("/query")
    public ResponseResult getRoleList(@RequestParam(value = "rId",required = false) Integer rId){
        List<Role> roleList = new ArrayList<>();
        if(rId==null){
            roleList = roleService.list();
        }else{
            roleList.add(roleService.getById(rId));
        }
        roleList.forEach(role->{
            role.setPowerList(powerService.getAllInfoPowersByRid(role.getRId()));
        });
        return ResponseResult.success(roleList);
    }
}

