package com.xy.honest_record.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.honest_record.entity.Accusation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 举报信息 服务类
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
public interface IAccusationService extends IService<Accusation> {
    public Page<Accusation> allInfoQueryByPage(int pageNum, int pageSize, Wrapper<Accusation> wrapper);
}
