package com.xy.honest_record.conf.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.util.Date;

/*
* 当插入和更新时，createTime，updateTime 字段为 null 时，自动填充。
* */

@Slf4j
@Component
public class FieldFillHandler implements MetaObjectHandler {

    //当执行insert 语句时，填充实体的属性
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("FieldFillHandler 填充属性createTime和updateTime");
        this.setFieldValByName("createTime", new Date(),metaObject);
        this.setFieldValByName("updateTime", new Date(),metaObject);
    }

    //当执行 update 语句时，填充实体的属性
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("FieldFillHandler 填充属性 updateTime");
        this.setFieldValByName("updateTime", new Date(),metaObject);
    }
}
