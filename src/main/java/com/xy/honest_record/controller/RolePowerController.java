package com.xy.honest_record.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xy.honest_record.common.vo.Code;
import com.xy.honest_record.common.vo.ResponseResult;
import com.xy.honest_record.entity.RolePower;
import com.xy.honest_record.service.IRolePowerService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色权限 前端控制器
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/rolePower")
public class RolePowerController {

    @Resource
    IRolePowerService rolePowerService;

    @PutMapping("/update/{rid}")
    @Transactional
    public ResponseResult update(@PathVariable("rid") Integer rid, @RequestBody List<String> pidList){

       QueryWrapper<RolePower> wrapper = new QueryWrapper<>();
        System.out.println(rid);
        System.out.println(pidList);
        wrapper.eq("r_id",rid);
        rolePowerService.remove(wrapper);
        List<RolePower> rolePowers = new ArrayList<>();
        pidList.forEach(pid->{
            if(!pid.endsWith("00")){ //角色权限表中 不保存一级权限(x00)
                RolePower rolePower = new RolePower();
                rolePower.setPId(pid);
                rolePower.setRId(rid);
                rolePowers.add(rolePower);
            }
        });
        boolean r = rolePowerService.saveBatch(rolePowers);

        if(r){
            return ResponseResult.success();
        }else{
            return ResponseResult.failure(Code.FAIL);
        }
    }
}

