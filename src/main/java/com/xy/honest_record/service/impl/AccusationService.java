package com.xy.honest_record.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.honest_record.entity.Accusation;
import com.xy.honest_record.mapper.AccusationMapper;
import com.xy.honest_record.service.IAccusationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 举报信息 服务实现类
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@Service
public class AccusationService extends ServiceImpl<AccusationMapper, Accusation> implements IAccusationService {

    @Resource
    AccusationMapper accusationMapper;

    @Override
    public Page<Accusation> allInfoQueryByPage(int pageNum, int pageSize, Wrapper<Accusation> wrapper) {
        Page<Accusation> page = new Page<>(pageNum,pageSize);
        return accusationMapper.allInfoQuery(page,wrapper);
    }
}

