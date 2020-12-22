package com.xy.honest_record.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.honest_record.entity.Power;
import com.xy.honest_record.mapper.PowerMapper;
import com.xy.honest_record.service.IPowerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@Service
public class PowerService extends ServiceImpl<PowerMapper, Power> implements IPowerService {

    @Resource
    PowerMapper powerMapper;

    @Override
    public Power getPowerById(int pid) {
        return powerMapper.getPowerById(pid);
    }

    @Override
    public Page<Power> getPowerByPageNumber(int pageno) {

        Page<Power> page = new Page<>(pageno,5);
        Map<String,Object> map = new HashMap<>();
        QueryWrapper<Power> queryWrapper = new QueryWrapper<>();


        powerMapper.selectPage(page,queryWrapper);
        return page;
    }
}
