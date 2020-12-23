package com.xy.honest_record;


import com.xy.honest_record.common.util.JWTutil;
import com.xy.honest_record.common.vo.TokenPayload;
import com.xy.honest_record.entity.Faculty;

import com.xy.honest_record.service.IFacultyService;
import com.xy.honest_record.service.IPowerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static com.xy.honest_record.common.util.JWTutil.getPayLoadFromToken;

@SpringBootTest
class HonestRecordApplicationTests {


    @Autowired
    IFacultyService facultyService;

    @Autowired
    IPowerService powerService;

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


}
