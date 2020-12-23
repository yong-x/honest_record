package com.xy.honest_record.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.honest_record.entity.Power;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
public interface IPowerService extends IService<Power> {
    public Power getPowerById(int pid);

    public Page<Power> getPowerByPageNumber(int page);

    public List<Power> getPowersByRid(int rid); //根据角色id查询所有权限


}
