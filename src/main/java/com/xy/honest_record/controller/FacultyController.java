package com.xy.honest_record.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.xy.honest_record.common.util.JWTutil;
import com.xy.honest_record.common.util.WordUtils;
import com.xy.honest_record.common.vo.Code;
import com.xy.honest_record.common.vo.ResponseResult;
import com.xy.honest_record.common.vo.TokenPayload;
import com.xy.honest_record.entity.Accusation;
import com.xy.honest_record.entity.Faculty;
import com.xy.honest_record.entity.Role;
import com.xy.honest_record.mapper.AccusationMapper;
import com.xy.honest_record.mapper.FacultyMapper;
import com.xy.honest_record.service.IFacultyService;
import com.xy.honest_record.service.IPowerService;
import com.xy.honest_record.service.IRoleService;
import org.apache.velocity.util.ArrayListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.*;

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

    @Resource
    AccusationMapper accusationMapper;

    //分析报告 模板文件名称
    private static String DesicionTemplateFileName="decisionTemplate.ftl";
    //新生成文件所要保存的绝对路径
    private static String newDesicionFilePath="";
    //新生成文件 的文件名称
    private static final String newDesicionFileName="decision.doc";

    static {
        try {
            newDesicionFilePath = new ClassPathResource("static/doc").getURI().getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @RequestMapping("/login")
    public ResponseResult login(@RequestBody Map<String,String> map, HttpServletRequest request){
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
    public ResponseResult querylist(int pageNum,int pageSize,Faculty faculty){

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
        wrapper.orderByDesc("update_time","create_time");



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

    @GetMapping("/query/{userid}")//单个查询时可以查询出已经被删除了的对象，deleted=1
    public ResponseResult querybyId(@PathVariable("userid") int userid){
        return ResponseResult.success(facultyService.getById(userid));
    }

    @PostMapping("/add")
    public ResponseResult add(@RequestBody Faculty faculty){
        System.out.println(faculty);

        faculty.setCheckState(0);//未审核
        faculty.setDeleted(0);//未删除
        faculty.setCreateTime(new Date()); //创建时间
        faculty.setUpdateTime(new Date()); //修改时间
        boolean r = facultyService.save(faculty);
        if(r){
            return ResponseResult.success();
        }else{
            return ResponseResult.failure(Code.FAIL);
        }

    }

    @PutMapping("/update")
    public ResponseResult update(@RequestBody Faculty faculty){
        faculty.setUpdateTime(new Date()); //修改时间

        boolean r = facultyService.updateById(faculty);
        if(r){
            return ResponseResult.success();
        }else{
            return ResponseResult.failure(Code.FAIL);
        }
    }

    @DeleteMapping("/delete/{userid}")
    public ResponseResult deletebyId(@PathVariable("userid") int userid){
        Faculty faculty = facultyService.getById(userid);
        if(faculty!=null){
            faculty.setDeleted(1); //软删除，删除操作变为更新操作
            boolean r = facultyService.updateById(faculty);
            if(r){
                return ResponseResult.success();
            }else{
                return ResponseResult.failure(Code.FAIL);
            }
        }else{
            return ResponseResult.failure(Code.USER_NOT_FOUND);
        }
    }

    @PostMapping("/decision")
    public ResponseResult report(@RequestBody Map<String,String> reqMap) throws Exception{

        String accusedUserId = reqMap.get("accusedUserId");//要生成决策书的用户id
        String replyName = reqMap.get("replyName");//要回复的单位名称
        String inscribeName = reqMap.get("inscribeName"); //落款单位名称


        QueryWrapper<Accusation> wrapper = new QueryWrapper<>();
        wrapper.eq("accused_user_id",accusedUserId);
        wrapper.orderByAsc("accuse_date");
        Page<Accusation> page = new Page<>();

        page = accusationMapper.allInfoQuery(page, wrapper);
        List<Accusation> list = page.getRecords();

        StrBuilder builder = StrBuilder.create();
        builder.append("对于").append(list.get(0).getFaculty().getUserName()).append("同志，学校纪委收到的投诉举报总条数为").append(page.getTotal()).append("条。学校曾");
        String template = "在{}收到举报，经调查了解，该同志在{}问题领域上存在{}的问题。";
        list.forEach(e->{
            builder.append(StrUtil.format(template, DateUtil.format(e.getAccuseDate(),"yyyy年M月d日"), e.getProblemField().getPfName(), e.getProblemType().getPtName()));
            //System.out.println("在"+ DateUtil.format(e.getAccuseDate(),"yyyy年M月d日")+"收到举报，称该同志在"+e.getProblemField().getPfName()+"问题领域上存在"+e.getProblemType().getPtName()+"的问题；");
        });


        //添加模板数据
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("username", list.get(0).getFaculty().getUserName()); //用户姓名
        dataMap.put("replyName", replyName); //回复单位名
        dataMap.put("content", builder); //主体内容区
        dataMap.put("inscribeName", inscribeName); //落款单位
        dataMap.put("inscribeTime", DateUtil.format(new Date(),"yyyy年M月d日"));//落款时间

        //String staticPath = new ClassPathResource("static").getURI().getPath(); //获取 resources/static 的绝对路径

        //文件生成路径
        String wordFilePath = newDesicionFilePath;
        //文件生成名称
        String wordFileName = newDesicionFileName;
        //模板文件名称
        String templateFileName = DesicionTemplateFileName;

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

        BufferedInputStream in = FileUtil.getInputStream(newDesicionFilePath+"/"+newDesicionFileName); //客户端要下载的文件在服务器上的位置
        String newFileName = RandomUtil.randomString(10)+".doc"; //客户端下载时默认保存的文件名

        response.setHeader("content-type","application/msword"); //word文档的 content-type
        response.setHeader("Content-disposition", "attachment;filename=" + newFileName);//文件下载时必须这样设置
        response.setContentType("application/octet-stream;charset=UTF-8");//文件下载时必须这样设置

        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        long copySize = IoUtil.copy(in, out, IoUtil.DEFAULT_BUFFER_SIZE);
        response.flushBuffer();
    }
}

