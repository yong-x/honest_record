package com.xy.honest_record.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.honest_record.entity.Faculty;
import com.xy.honest_record.mapper.FacultyMapper;
import com.xy.honest_record.service.IFacultyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 教职工 服务实现类
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@Service
public class FacultyService extends ServiceImpl<FacultyMapper, Faculty> implements IFacultyService {

    @Resource
    FacultyMapper facultyMapper;

    @Override
    public Page<Faculty> getFacultysByPage(int pageNum, int pageSize, QueryWrapper<Faculty> queryWrapper) {
        Page<Faculty> page = new Page<>(pageNum,pageSize);
        page = facultyMapper.selectPage(page,queryWrapper);
        return page;
    }



    @Override
    public Page<Faculty> allInfoQueryByPage(int pageNum, int pageSize, Wrapper<Faculty> wrapper) {
        Page<Faculty> page = new Page<>(pageNum,pageSize);
        return facultyMapper.allInfoQuery(page,wrapper);
    }
}
