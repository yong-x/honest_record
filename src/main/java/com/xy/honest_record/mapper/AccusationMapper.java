package com.xy.honest_record.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.honest_record.entity.Accusation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 举报信息 Mapper 接口
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
public interface AccusationMapper extends BaseMapper<Accusation> {

    public Page<Accusation> allInfoQuery(IPage page, @Param("ew") Wrapper<Accusation> wrapper);

}
