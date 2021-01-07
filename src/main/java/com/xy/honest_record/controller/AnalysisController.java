package com.xy.honest_record.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.honest_record.common.util.MyDateUtil;
import com.xy.honest_record.common.util.WordUtils;
import com.xy.honest_record.common.vo.Code;
import com.xy.honest_record.common.vo.ResponseResult;
import com.xy.honest_record.entity.Accusation;
import com.xy.honest_record.entity.AnaysisDataVo;
import com.xy.honest_record.mapper.AccusationMapper;
import com.xy.honest_record.service.IAccusationService;
import com.xy.honest_record.service.IFacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.*;
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

    //分析报告 模板文件名称
    private static String ReportTemplateFileName="reportTemplate.ftl";
    //新生成分析报告文件所要保存的绝对路径
    private static String newReportFilePath="";
    //新生成分析报告文件 的文件名称
    private static final String newReportFileName="report.doc";

    static {
        try {
            newReportFilePath = new ClassPathResource("static/doc").getURI().getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //fiveyears: 2015,2016,2017,2018,2019
    //oneyear: 2020-03,2020-04,2020-05,2020-06,2020-07,2020-08,2020-09,2020-10,2020-11,2020-12,2021-01,2021-02
    //onemonth: 2020-12-04,.......,2021-01-01,.....2020-01-04
    @GetMapping("/lineChart")
    public ResponseResult count(String dateRange) {
        Map<String, Object> rmap = new HashMap<>();//待返回结果集
        List<String> xAxis = new ArrayList<>(); //x轴数据列表
        List<Integer> seriesData = new ArrayList<>(); //y轴数据列表


        QueryWrapper<Accusation> wrapper = new QueryWrapper<>();

        if (dateRange != null && dateRange.equals("onemonth")) { //近30天的数据
            List<MyDateUtil.DateRange> nearlyDays = MyDateUtil.getNearlyDays(30);
            wrapper.clear();
            nearlyDays.forEach(e -> {
                wrapper.between("accuse_date", e.getStartDate(), e.getEndDate());
                xAxis.add(e.getStartDate().toString().subSequence(0, 10).toString());
                seriesData.add(accusationService.count(wrapper));
                wrapper.clear();
            });
            rmap.put("xAxis", xAxis);
            rmap.put("seriesData", seriesData);
        } else if (dateRange != null && dateRange.equals("oneyear")) { //近一年每个月的数据
            List<MyDateUtil.DateRange> nearlyDays = MyDateUtil.getNearlyMonth(12);
            wrapper.clear();
            nearlyDays.forEach(e -> {
                wrapper.between("accuse_date", e.getStartDate(), e.getEndDate());
                xAxis.add(e.getStartDate().toString().subSequence(0, 7).toString());
                seriesData.add(accusationService.count(wrapper));
                wrapper.clear();
            });
            rmap.put("xAxis", xAxis);
            rmap.put("seriesData", seriesData);
        } else if (dateRange != null && dateRange.equals("fiveyears")) { //近5年每年的数据
            List<MyDateUtil.DateRange> nearlyDays = MyDateUtil.getNearlyYear(5);
            wrapper.clear();
            nearlyDays.forEach(e -> {
                wrapper.between("accuse_date", e.getStartDate(), e.getEndDate());
                xAxis.add(e.getStartDate().toString().subSequence(0, 4).toString());
                seriesData.add(accusationService.count(wrapper));
                wrapper.clear();
            });
            rmap.put("xAxis", xAxis);
            rmap.put("seriesData", seriesData);
        }

        return ResponseResult.success(rmap);
    }

    @GetMapping("/pieChart")
    public ResponseResult ptChart(String dateRange) {

        List<Map<String, Object>> ptChartMap = new ArrayList<>(); //ptChart饼图数据集


        QueryWrapper<Accusation> wrapper = new QueryWrapper<>();

        List<MyDateUtil.DateRange> dateRanges = new ArrayList<>();
        if (dateRange != null && dateRange.equals("onemonth")) { //近30天的数据
            dateRanges = MyDateUtil.getNearlyDays(30);
        } else if (dateRange != null && dateRange.equals("oneyear")) { //近一年数据
            dateRanges = MyDateUtil.getNearlyMonth(12);
        } else if (dateRange != null && dateRange.equals("fiveyears")) { //近5年的数据
            dateRanges = MyDateUtil.getNearlyYear(5);
        }
        Date startDate = dateRanges.get(0).getStartDate();
        Date endDate = dateRanges.get(dateRanges.size() - 1).getEndDate();

        List<AnaysisDataVo> ptChartData = accusationMapper.analysisByTableName("problem_type", "pt_id", "pt_name", startDate, endDate);
        List<AnaysisDataVo> pfChartData = accusationMapper.analysisByTableName("problem_field", "pf_id", "pf_name", startDate, endDate);
        List<AnaysisDataVo> atChartData = accusationMapper.analysisByTableName("accuse_type", "at_id", "at_name", startDate, endDate);

        Map<String, Object> rmap = new HashMap<>();//待返回结果集
        rmap.put("ptChartData", ptChartData);
        rmap.put("pfChartData", pfChartData);
        rmap.put("atChartData", atChartData);
        return ResponseResult.success(rmap);


    }

    @PostMapping("/report")
    public ResponseResult report(@RequestBody Map<String,String> reqMap){

        String title = reqMap.get("title");
        String content = reqMap.get("content");
        String lineImg = reqMap.get("lineImg");
        String ptChartImg = reqMap.get("ptChartImg");
        String pfChartImg = reqMap.get("pfChartImg");
        String atChartImg = reqMap.get("atChartImg");

        Map<String, Object> map = new HashMap<>();
        String base64 = lineImg.replaceAll(" ", "+");
        // 数据格式为data:image/png;base64,iVBORw0KGgoAA...  在"base64,"之后的才是图片信息
        String[] arr = base64.split("base64,");
        String image = arr[1];


        //添加模板数据
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("title", title);
        dataMap.put("content", content);
        dataMap.put("lineImg", WordUtils.turn(lineImg));
        dataMap.put("ptChartImg", WordUtils.turn(ptChartImg));
        dataMap.put("pfChartImg", WordUtils.turn(pfChartImg));
        dataMap.put("atChartImg", WordUtils.turn(atChartImg));


        //String staticPath = new ClassPathResource("static").getURI().getPath(); //获取 resources/static 的绝对路径

        //文件生成路径
        String wordFilePath = newReportFilePath;
        //文件生成名称
        String wordFileName = newReportFileName;
        //模板文件名称
        String templateFileName = ReportTemplateFileName;

        //生成word文档
        Boolean result = new WordUtils().writeWordReport(wordFilePath, wordFileName, templateFileName, dataMap);
        if (result) {
            return ResponseResult.success();
        } else {
            return ResponseResult.failure(Code.SERVER_EXECEPTION);
        }
    }

    @GetMapping("/download")
    public void downloadByIOUtil(HttpServletResponse response) throws Exception{

        BufferedInputStream in = FileUtil.getInputStream(newReportFilePath+"/"+newReportFileName); //客户端要下载的文件在服务器上的位置
        String newFileName = RandomUtil.randomString(10)+".doc"; //客户端下载时默认保存的文件名

        response.setHeader("content-type","application/msword"); //word文档的 content-type
        response.setHeader("Content-disposition", "attachment;filename=" + newFileName);//文件下载时必须这样设置
        response.setContentType("application/octet-stream;charset=UTF-8");//文件下载时必须这样设置

        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        long copySize = IoUtil.copy(in, out, IoUtil.DEFAULT_BUFFER_SIZE);
        response.flushBuffer();
    }


    //原生下载方式，未被使用
    @GetMapping("/download1")
    public void download1(HttpServletResponse response) throws Exception{

        //1、设置输入、输出文件名
        String fileName = "D:/word.doc";//客户端要下载的文件在服务器上的位置
        String newFileName = UUID.randomUUID().toString().substring(0,10)+".doc"; //客户端下载时默认保存的文件名


        //2、response与文件相关设置
        response.setHeader("content-type","application/msword"); //word文档的 content-type
        response.setHeader("Content-disposition", "attachment;filename=" + newFileName);//文件下载时必须这样设置
        response.setContentType("application/octet-stream;charset=UTF-8");//文件下载时必须这样设置

        //3、输入输出流相关设置
        byte[] buff = new byte[1024];
        //创建缓冲输入流
        BufferedInputStream bis = null;
        OutputStream outputStream = null;

        try {
            outputStream = response.getOutputStream();

            //这个路径为待下载文件的路径
            bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
            int read = bis.read(buff);

            //通过while循环写入到指定了的文件夹中
            while (read != -1) {
                outputStream.write(buff, 0, buff.length);
                outputStream.flush();
                read = bis.read(buff);
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        response.flushBuffer();
    }



}

