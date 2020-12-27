package com.xy.honest_record.controller;


import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.xy.honest_record.common.util.JWTutil;
import com.xy.honest_record.common.vo.Code;
import com.xy.honest_record.common.vo.ResponseResult;
import com.xy.honest_record.common.vo.TokenPayload;
import com.xy.honest_record.entity.Faculty;
import com.xy.honest_record.entity.Role;
import com.xy.honest_record.mapper.FacultyMapper;
import com.xy.honest_record.service.IFacultyService;
import com.xy.honest_record.service.IPowerService;
import com.xy.honest_record.service.IRoleService;
import org.apache.velocity.util.ArrayListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 教职工 前端控制器
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/faculty")
public class FacultyController {

    @Autowired
    IFacultyService facultyService;

    @Autowired
    IPowerService powerService;

    @Autowired
    IRoleService roleService;

    @RequestMapping("/login")
    public ResponseResult login(@RequestBody Map<String,String> map){
        int userId = Integer.parseInt(map.get("userId"));
        String password = map.get("password");

        Map<String,Object> rmap = new HashMap<>();
        TokenPayload tokenPayload = new TokenPayload();

        System.out.println("接收的参数" +userId+"  ;  "+ password);

        Faculty faculty = facultyService.getById(userId);

        if(faculty==null){ //1、教工号不存在
            return ResponseResult.failure(Code.USER_NOT_FOUND);
        } else if(!faculty.getPassword().equals(password)){ //2、用户存在但密码错误
            return ResponseResult.failure(Code.PASSWORD_ERROR);

        }else{ //3、用户存在且密码正确
            tokenPayload.setFaculty(faculty);
            tokenPayload.setPowers(powerService.getPowersByRid(faculty.getRId()));
            String token = JWTutil.getToken(tokenPayload,30*60);

            rmap.put("token",token);
            rmap.put("payload",tokenPayload);

            return ResponseResult.success(rmap);
        }
    }

    @GetMapping("/query")
    public ResponseResult query(int pageNum,int pageSize,Faculty faculty){

        QueryWrapper<Faculty> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted",0);

        if(faculty.getCheckState()!=null && (faculty.getCheckState().intValue()==0 || faculty.getCheckState().intValue()==1)){
            wrapper.eq("check_state",faculty.getCheckState());
        }
        if(faculty.getRId()!=null && faculty.getRId()!=-1){
            wrapper.eq("f.r_id",faculty.getRId());
        }
        if(faculty.getUserId()!=null){
            wrapper.like("user_id",faculty.getUserId());
        }
        if(faculty.getUserName()!=null && faculty.getUserName().length()>0){
            wrapper.like("user_name",faculty.getUserName());
        }

        //Page<Faculty> page = facultyService.getFacultysByPage(pageNum,pageSize,wrapper);
        Page<Faculty> page = facultyService.allInfoQueryByPage(pageNum,pageSize,wrapper);

        long total = page.getTotal(); //总条数
        long size = page.getSize();//每页多少条
        boolean hasNext = page.hasNext();//是否有下一页
        boolean hasPrevious =  page.hasPrevious();//是否有上一页
        List<Faculty> list = page.getRecords(); //当前页数据

        Map<String,Object> rmap = new HashMap<>();
        rmap.put("total",total);
        rmap.put("size",size);
        rmap.put("hasNext",hasNext);
        rmap.put("hasPrevious",hasPrevious);
        rmap.put("facultyList",list);
        return ResponseResult.success(rmap);

    }

}

