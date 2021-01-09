package com.xy.honest_record;


import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.honest_record.common.util.JWTutil;
import com.xy.honest_record.common.util.MyDateUtil;
import com.xy.honest_record.common.vo.TokenPayload;
import com.xy.honest_record.entity.*;

import com.xy.honest_record.mapper.AccusationMapper;
import com.xy.honest_record.mapper.FacultyMapper;
import com.xy.honest_record.mapper.PowerMapper;
import com.xy.honest_record.service.IFacultyService;
import com.xy.honest_record.service.IPowerService;
import com.xy.honest_record.service.IRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.*;

import static com.xy.honest_record.common.util.JWTutil.getPayLoadFromToken;

//@SpringBootTest
class HonestRecordApplicationTests {


    @Autowired
    IFacultyService facultyService;

    @Autowired
    IPowerService powerService;

    @Resource
    PowerMapper powerMapper;

    @Autowired
    IRoleService roleService;

    @Resource
    FacultyMapper facultyMapper;

    @Resource
    AccusationMapper accusationMapper;

    @Test
    void contextLoads() {
        List<String> noInterceptePathList = Arrays.asList("/analysis/download","/faculty/decisionDownload");
        String requestURI="/analysis/download";
        if(noInterceptePathList.contains(requestURI)){
            System.out.println("true");
        }else{
            System.out.println("false");
        }

    }



    @Test
    void testJWT(){ //测试JWTUtil工具类

        Faculty faculty = facultyService.getById(100002);
        TokenPayload tokenPayload = new TokenPayload();
        tokenPayload.setFaculty(faculty);
        tokenPayload.setPowers(powerService.getPowersByRid(faculty.getRId()));


        String token = JWTutil.getToken(tokenPayload);
        System.out.println(token);
        TokenPayload tokenPayloaded = getPayLoadFromToken(token,TokenPayload.class);
        System.out.println(tokenPayloaded);
    }

    @Test
    void testBatchQuery(){

        List<Integer> ridList =  new ArrayList<>();

        //批量查询，查询结果是无序的
        ridList.add(2);
        ridList.add(3);
        ridList.add(1);

        List<Role> roles = roleService.listByIds(ridList);

        System.out.println(roles);
    }
    @Test
    void testQuery(){
        Page<Faculty> page = new Page<>(1,2);
        QueryWrapper<Faculty> wrapper = new QueryWrapper<>();

        wrapper.like("user_name","理");
        List<Faculty> list = facultyMapper.allInfoQuery(page,wrapper).getRecords();

        System.out.println(list);
    }

    @Test
    void testPowerList(){

        List<Power> powers = powerService.getAllInfoPowersByRid(3);

        powers.forEach(e-> System.out.println(e));



    }

    @Test
    void testAccusationMapper(){

        Page<Accusation> page = new Page<>(1,3);
        QueryWrapper<Accusation> wrapper = new QueryWrapper<>();
        wrapper.like("accuser_name","三");

        List<Accusation> list = accusationMapper.allInfoQuery(page,wrapper).getRecords();

        list.forEach(System.out::println);

    }
    @Test
    void testDate(){
        List<MyDateUtil.DateRange> nearlyDays = MyDateUtil.getNearlyDays(5);

//        nearlyDays.forEach(e->{
//            System.out.println(e.getStartDate()+" 至 "+e.getEndDate());
//        });

//        List<MyDateUtil.DateRange> nearlyMonth = MyDateUtil.getNearlyMonth(10);
//        nearlyMonth.forEach(e->{
//            System.out.println(e.getStartDate()+" 至 "+e.getEndDate());
//        });

        List<MyDateUtil.DateRange> nearlyYear = MyDateUtil.getNearlyYear(5);
        nearlyYear.forEach(e->{
            System.out.println(e.getStartDate()+" 至 "+e.getEndDate());
        });

    }
    @Test
    void testAnalysis(){


        DateTime startDate = DateUtil.parse("2021-01-02 14:10:10");
        DateTime endDate = DateUtil.parse("2021-01-20 14:17:00");
        //List<AnaysisDataVo> anaysisDataVos = accusationMapper.analysisByTableName("problem_type", "pt_id", "pt_name", startDate, endDate);
        //List<AnaysisDataVo> anaysisDataVos = accusationMapper.analysisByTableName("problem_field", "pf_id", "pf_name", startDate, endDate);
        List<AnaysisDataVo> anaysisDataVos = accusationMapper.analysisByTableName("accuse_type", "at_id", "at_name", null, null);
        anaysisDataVos.forEach(System.out::println);

    }
    @Test
    void testDecision(){

        QueryWrapper<Accusation> wrapper = new QueryWrapper<>();
        wrapper.eq("accused_user_id",100019);
        wrapper.orderByAsc("accuse_date");
        Page<Accusation> page = new Page<>();

        page = accusationMapper.allInfoQuery(page, wrapper);

        //page.getRecords().forEach(System.out::println);

        List<Accusation> list = page.getRecords();
        System.out.println("收到的举报总数为 "+page.getTotal());
        list.forEach(e->{
            System.out.println("在"+DateUtil.format(e.getAccuseDate(),"yyyy年M月d日")+"收到举报，称该同志在"+e.getProblemField().getPfName()+"问题领域上存在"+e.getProblemType().getPtName()+"的问题；");
        });








    }


}
