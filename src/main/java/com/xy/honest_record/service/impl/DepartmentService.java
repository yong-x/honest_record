package com.xy.honest_record.service.impl;

import com.xy.honest_record.entity.Department;
import com.xy.honest_record.mapper.DepartmentMapper;
import com.xy.honest_record.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 部门 服务实现类
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@Service
public class DepartmentService extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

}
