package com.xy.honest_record.common.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.util.*;

public class MyDateUtil {

    public static class DateRange{
        private Date startDate;
        private Date endDate;
        DateRange(Date startDate,Date endDate){
            this.startDate=startDate;
            this.endDate=endDate;
        }
        public Date getEndDate() {
            return endDate;
        }
        public Date getStartDate() {
            return startDate;
        }
    }
    //返回距离最近前 N 年，每一年的开始和结束时间
    public static List<DateRange> getNearlyYear(int NearlyYearNumber){
        List<DateRange> list = new ArrayList<>();
        DateTime date = DateUtil.date();
        for(int k=0;k<=NearlyYearNumber;k++){
            //System.out.println(DateUtil.beginOfYear(date)+" 至 "+DateUtil.endOfYear(date));
            list.add(new DateRange(DateUtil.beginOfYear(date),DateUtil.endOfYear(date)));
            date = DateUtil.offset(date, DateField.YEAR,-1);
        }
        Collections.reverse(list);
        return list;
    }


    //返回距离最近前 N 个月，每个月的开始和结束时间
    public static List<DateRange> getNearlyMonth(int NearlyMonthNumber){
        DateTime date = DateUtil.date();
        //最近一年每个月的开始时间 和 结束时间
        List<DateRange> list = new ArrayList<>();
        for(int i=0;i<=NearlyMonthNumber;i++){
            //System.out.println("前"+i+"月："+DateUtil.beginOfMonth(date)+" 至 "+DateUtil.endOfMonth(date));

            list.add(new DateRange(DateUtil.beginOfMonth(date),DateUtil.endOfMonth(date)));
            int year = DateUtil.year(date); //2021
            int month = DateUtil.month(date);//0-11

            if(month==0){
                year--;
                month=11;
            }else{
                month--;
            }
            date = DateUtil.parse(""+year+"-"+(month+1)+"-01 00:00:00");
        }
        Collections.reverse(list);
        return list;
    }

    //返回距离最近前 N 天，每天的开始和结束时间
    public static List<DateRange> getNearlyDays(int NearlyDaysNumber){

        List<DateRange> list = new ArrayList<>(NearlyDaysNumber);
        DateTime date = DateUtil.date();
        for(int j=0;j<=NearlyDaysNumber;j++){
            //System.out.println(DateUtil.beginOfDay(date)+" 至 "+DateUtil.endOfDay(date));
            list.add(new DateRange(DateUtil.beginOfDay(date),DateUtil.endOfDay(date)));
            date = DateUtil.offsetDay(date,-1);
        }
        Collections.reverse(list);
        return list;
    }



}
