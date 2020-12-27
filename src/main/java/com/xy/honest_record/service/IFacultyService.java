package com.xy.honest_record.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.honest_record.entity.Faculty;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 教职工 服务类
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
public interface IFacultyService extends IService<Faculty> {

    public Page<Faculty> getFacultysByPage(int pageNum, int pageSize, QueryWrapper<Faculty> queryWrapper);

    public Page<Faculty> allInfoQueryByPage(int pageNum, int pageSize, @Param("ew") Wrapper<Faculty> wrapper);

}
