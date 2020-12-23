package com.xy.honest_record.common.vo;


import com.xy.honest_record.entity.Faculty;
import com.xy.honest_record.entity.Power;
import com.xy.honest_record.entity.Role;
import lombok.Data;

import java.util.List;

/*
* 定义 token的数据体 Payload，包括用户信息 和 权限信息
* */
@Data
public class TokenPayload {

    private Faculty faculty;
    private List<Power> powers;





}
