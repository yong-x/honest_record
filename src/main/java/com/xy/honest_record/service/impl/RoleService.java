package com.xy.honest_record.service.impl;

import com.xy.honest_record.entity.Role;
import com.xy.honest_record.mapper.RoleMapper;
import com.xy.honest_record.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
