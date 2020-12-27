package com.xy.honest_record.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.honest_record.entity.Faculty;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * 教职工 Mapper 接口
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
public interface FacultyMapper extends BaseMapper<Faculty> {

    public Page<Faculty> allInfoQuery(IPage page, @Param("ew")Wrapper<Faculty> wrapper);

}
