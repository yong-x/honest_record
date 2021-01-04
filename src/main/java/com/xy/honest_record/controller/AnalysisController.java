package com.xy.honest_record.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.honest_record.common.util.MyDateUtil;
import com.xy.honest_record.common.vo.Code;
import com.xy.honest_record.common.vo.ResponseResult;
import com.xy.honest_record.entity.Accusation;
import com.xy.honest_record.entity.AnaysisDataVo;
import com.xy.honest_record.mapper.AccusationMapper;
import com.xy.honest_record.service.IAccusationService;
import com.xy.honest_record.service.IFacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.xml.transform.Result;
import java.util.*;

/**
 * <p>
 * 举报信息 前端控制器
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/analysis")
public class AnalysisController {

    @Autowired
    IAccusationService accusationService;

    @Autowired
    IFacultyService facultyService;

    @Resource
    AccusationMapper accusationMapper;

    //fiveyears: 2015,2016,2017,2018,2019
    //oneyear: 2020-03,2020-04,2020-05,2020-06,2020-07,2020-08,2020-09,2020-10,2020-11,2020-12,2021-01,2021-02
    //onemonth: 2020-12-04,.......,2021-01-01,.....2020-01-04
    @GetMapping("/lineChart")
    public ResponseResult count(String dateRange){
        Map<String,Object> rmap = new HashMap<>();//待返回结果集
        List<String> xAxis = new ArrayList<>(); //x轴数据列表
        List<Integer> seriesData = new ArrayList<>(); //y轴数据列表



        QueryWrapper<Accusation> wrapper = new QueryWrapper<>();

        if(dateRange!=null&&dateRange.equals("onemonth")){ //近30天的数据
            List<MyDateUtil.DateRange> nearlyDays = MyDateUtil.getNearlyDays(30);
            wrapper.clear();
            nearlyDays.forEach(e->{
                wrapper.between("accuse_date",e.getStartDate(),e.getEndDate());
                xAxis.add(e.getStartDate().toString().subSequence(0,10).toString());
                seriesData.add(accusationService.count(wrapper));
                wrapper.clear();
            });
            rmap.put("xAxis",xAxis);
            rmap.put("seriesData",seriesData);
        }else if(dateRange!=null&&dateRange.equals("oneyear")){ //近一年每个月的数据
            List<MyDateUtil.DateRange> nearlyDays = MyDateUtil.getNearlyMonth(12);
            wrapper.clear();
            nearlyDays.forEach(e->{
                wrapper.between("accuse_date",e.getStartDate(),e.getEndDate());
                xAxis.add(e.getStartDate().toString().subSequence(0,7).toString());
                seriesData.add(accusationService.count(wrapper));
                wrapper.clear();
            });
            rmap.put("xAxis",xAxis);
            rmap.put("seriesData",seriesData);
        }else if(dateRange!=null&&dateRange.equals("fiveyears")){ //近5年每年的数据
            List<MyDateUtil.DateRange> nearlyDays = MyDateUtil.getNearlyYear(5);
            wrapper.clear();
            nearlyDays.forEach(e->{
                wrapper.between("accuse_date",e.getStartDate(),e.getEndDate());
                xAxis.add(e.getStartDate().toString().subSequence(0,4).toString());
                seriesData.add(accusationService.count(wrapper));
                wrapper.clear();
            });
            rmap.put("xAxis",xAxis);
            rmap.put("seriesData",seriesData);
        }

        return ResponseResult.success(rmap);
    }

    @GetMapping("/pieChart")
    public ResponseResult ptChart(String dateRange){

        List<Map<String,Object>> ptChartMap = new ArrayList<>(); //ptChart饼图数据集




        QueryWrapper<Accusation> wrapper = new QueryWrapper<>();

        List<MyDateUtil.DateRange> dateRanges = new ArrayList<>();
        if(dateRange!=null&&dateRange.equals("onemonth")){ //近30天的数据
            dateRanges = MyDateUtil.getNearlyDays(30);
        }else if(dateRange!=null&&dateRange.equals("oneyear")){ //近一年数据
            dateRanges = MyDateUtil.getNearlyMonth(12);
        }else if(dateRange!=null&&dateRange.equals("fiveyears")){ //近5年的数据
            dateRanges = MyDateUtil.getNearlyYear(5);
        }
        Date startDate = dateRanges.get(0).getStartDate();
        Date endDate = dateRanges.get(dateRanges.size()-1).getEndDate();

        List<AnaysisDataVo> ptChartData = accusationMapper.analysisByTableName("problem_type", "pt_id", "pt_name", startDate, endDate);
        List<AnaysisDataVo> pfChartData = accusationMapper.analysisByTableName("problem_field", "pf_id", "pf_name", startDate, endDate);
        List<AnaysisDataVo> atChartData = accusationMapper.analysisByTableName("accuse_type", "at_id", "at_name", startDate, endDate);

        Map<String,Object> rmap = new HashMap<>();//待返回结果集
        rmap.put("ptChartData",ptChartData);
        rmap.put("pfChartData",pfChartData);
        rmap.put("atChartData",atChartData);
        return ResponseResult.success(rmap);


    }

}

