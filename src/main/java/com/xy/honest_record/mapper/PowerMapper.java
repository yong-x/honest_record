package com.xy.honest_record.mapper;

import com.xy.honest_record.entity.Power;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
public interface PowerMapper extends BaseMapper<Power> {

    public Power getPowerById(int pid);

    public List<Power> getPowersByRid(int rid); //根据角色id查询所有权限
}
