package com.xy.honest_record;


import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.honest_record.common.util.JWTutil;
import com.xy.honest_record.common.vo.TokenPayload;
import com.xy.honest_record.entity.Accusation;
import com.xy.honest_record.entity.Faculty;

import com.xy.honest_record.entity.Power;
import com.xy.honest_record.entity.Role;
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

@SpringBootTest
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
    void testDate(){

        Page<Accusation> page = new Page<>(1,3);
        QueryWrapper<Accusation> wrapper = new QueryWrapper<>();
        wrapper.like("accuser_name","三");

        List<Accusation> list = accusationMapper.allInfoQuery(page,wrapper).getRecords();

        list.forEach(System.out::println);

    }


}
