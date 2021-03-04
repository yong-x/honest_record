package com.xy.honest_record.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.honest_record.common.vo.Code;
import com.xy.honest_record.common.vo.ResponseResult;
import com.xy.honest_record.entity.Accusation;
import com.xy.honest_record.entity.Department;
import com.xy.honest_record.service.IAccusationService;
import com.xy.honest_record.service.IFacultyService;
import com.xy.honest_record.service.impl.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 举报信息 前端控制器
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/accusation")
public class AccusationController {

    @Autowired
    IAccusationService accusationService;

    @Autowired
    IFacultyService facultyService;

    @GetMapping("/query")
    public ResponseResult querylist(Integer pageNum, Integer pageSize, Accusation accusation,
                                    String accuseDateStart,String accuseDateEnd,
                                    String dealDateStart,String dealDateEnd){


        //多表连接查询由于存在同名字段，所以查询子句中带上零时表名 t
        QueryWrapper<Accusation> wrapper = new QueryWrapper<>();
        wrapper.eq("t.deleted",0);

        if(accusation.getAccusedUserId()!=null){
            wrapper.like("t.accused_user_id",accusation.getAccusedUserId());
        }
        if(accusation.getDealerUserId()!=null){
            wrapper.like("t.dealer_user_id",accusation.getDealerUserId());
        }
        //问题领域，问题类型，举报类型条件
        if(accusation.getPfId()!=null&&accusation.getPfId()!=-1){
            wrapper.eq("t.pf_id",accusation.getPfId());
        }
        if(accusation.getPtId()!=null&&accusation.getPtId()!=-1){
            wrapper.eq("t.pt_id",accusation.getPtId());
        }
        if(accusation.getAtId()!=null&&accusation.getAtId()!=-1){
            wrapper.eq("t.at_id",accusation.getAtId());
        }

        //时间范围条件
        if(accuseDateStart!=null&&accuseDateStart.length()>0){
            wrapper.ge("t.accuse_date",accuseDateStart);
        }
        if(accuseDateEnd!=null&&accuseDateEnd.length()>0){
            wrapper.le("t.accuse_date",accuseDateEnd);
        }
        if(dealDateStart!=null&&dealDateStart.length()>0){
            wrapper.ge("t.deal_date",dealDateStart);
        }
        if(dealDateEnd!=null&&dealDateEnd.length()>0){
            wrapper.le("t.deal_date",dealDateEnd);
        }

        if(accusation.getCheckState()!=null&&accusation.getCheckState()!=-1){
            wrapper.eq("t.check_state",accusation.getCheckState());
        }

        wrapper.orderByDesc("t.update_time","t.create_time");
        Page<Accusation> page = accusationService.allInfoQueryByPage(pageNum,pageSize,wrapper);


        long total = page.getTotal(); //总条数
        List<Accusation> list = page.getRecords(); //当前页数据

        list.forEach(e->{
            e.setDealerFaculty(facultyService.getById(e.getDealerUserId())); //把档案受理人用多次查询的方式查出
        });
        Map<String,Object> rmap = new HashMap<>();
        rmap.put("total",total);
        rmap.put("accusationList",list);
        return ResponseResult.success(rmap);
    }


    @PostMapping("/add")
    public ResponseResult add(@RequestBody Accusation accusation, HttpServletRequest request){
        accusation.setDeleted(0);//未删除
        accusation.setCheckState(0);//未审核
        accusation.setCreateTime(new Date()); //创建时间
        accusation.setUpdateTime(new Date()); //修改时间
        boolean r = accusationService.save(accusation);
        if(r){
            return ResponseResult.success(accusation);/////////////////////
        }else{
            return ResponseResult.failure(Code.FAIL);
        }
    }

    @GetMapping("/query/{id}") //单个查询时可以查询出已经被删除了的对象，deleted=1
    public ResponseResult querybyId(@PathVariable("id") int id){
        return ResponseResult.success(accusationService.getById(id));
    }

    @PutMapping("/update")
    public ResponseResult update(@RequestBody Accusation accusation, HttpServletRequest request){
        accusation.setUpdateTime(new Date()); //修改时间

        boolean r = accusationService.updateById(accusation);
        if(r){
            return ResponseResult.success(accusation);//////////////////
        }else{
            return ResponseResult.failure(Code.FAIL);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseResult deletebyId(@PathVariable("id") int id, HttpServletRequest request){
        Accusation accusation = accusationService.getById(id);
        if(accusation!=null){
            accusation.setDeleted(1); //软删除，删除操作变为更新操作
            boolean r = accusationService.updateById(accusation);
            if(r){
                return ResponseResult.success(accusation);//////////////////
            }else{
                return ResponseResult.failure(Code.FAIL);
            }
        }else{
            return ResponseResult.failure(Code.USER_NOT_FOUND);
        }
    }



}

