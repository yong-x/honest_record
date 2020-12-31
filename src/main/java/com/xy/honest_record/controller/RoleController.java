package com.xy.honest_record.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xy.honest_record.common.vo.Code;
import com.xy.honest_record.common.vo.ResponseResult;
import com.xy.honest_record.entity.Faculty;
import com.xy.honest_record.entity.Role;
import com.xy.honest_record.entity.RolePower;
import com.xy.honest_record.service.IFacultyService;
import com.xy.honest_record.service.IPowerService;
import com.xy.honest_record.service.IRolePowerService;
import com.xy.honest_record.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    IRolePowerService rolePowerService;

    @Autowired
    IFacultyService facultyService;

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

    @PostMapping("/add")
    public ResponseResult addRole(@RequestBody Role role){

        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        boolean r = roleService.save(role);
        if(r){
            return ResponseResult.success();
        }else{
            return ResponseResult.failure(Code.FAIL);
        }
    }

    @DeleteMapping("/delete/{rid}")
    @Transactional
    public ResponseResult deletebyId(@PathVariable("rid") int rid){
        //1、把所有角色为rid的用户 角色id修改为默认角色id 3
        UpdateWrapper<Faculty> wrapper1 = new UpdateWrapper<>();
        wrapper1.eq("r_id",rid);
        Faculty faculty = new Faculty();
        faculty.setRId(3);
        facultyService.update(faculty,wrapper1);
        //2、先把该角色rid拥有的所有权限删除
        QueryWrapper<RolePower> wrapper = new QueryWrapper<>();
        wrapper.eq("r_id",rid);
        rolePowerService.remove(wrapper);
        //3、删除掉该角色本身
        boolean r = roleService.removeById(rid);
        if(r){
            return ResponseResult.success();
        }else{
            return ResponseResult.failure(Code.FAIL);
        }
    }
}

