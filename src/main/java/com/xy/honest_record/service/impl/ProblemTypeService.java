package com.xy.honest_record.service.impl;

import com.xy.honest_record.entity.ProblemType;
import com.xy.honest_record.mapper.ProblemTypeMapper;
import com.xy.honest_record.service.IProblemTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 问题分类 服务实现类
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@Service
public class ProblemTypeService extends ServiceImpl<ProblemTypeMapper, ProblemType> implements IProblemTypeService {

}
