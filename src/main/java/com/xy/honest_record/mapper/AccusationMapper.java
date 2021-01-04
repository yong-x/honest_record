package com.xy.honest_record.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.honest_record.entity.Accusation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.honest_record.entity.AnaysisDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

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


    public List<AnaysisDataVo> analysisByTableName(@Param("ForeigntableName") String ForeigntableName,
                                                   @Param("ForeignId") String ForeignId,
                                                   @Param("ForeignFeildName") String ForeignFeildName,
                                                   @Param("startDate") Date startDate,
                                                   @Param("endDate") Date endDate);

}
