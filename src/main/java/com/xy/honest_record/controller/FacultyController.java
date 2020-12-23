package com.xy.honest_record.controller;


import com.auth0.jwt.JWT;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.xy.honest_record.common.util.JWTutil;
import com.xy.honest_record.common.vo.Code;
import com.xy.honest_record.common.vo.ResponseResult;
import com.xy.honest_record.common.vo.TokenPayload;
import com.xy.honest_record.entity.Faculty;
import com.xy.honest_record.mapper.FacultyMapper;
import com.xy.honest_record.service.IFacultyService;
import com.xy.honest_record.service.IPowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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

    //@CrossOrigin
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

}

