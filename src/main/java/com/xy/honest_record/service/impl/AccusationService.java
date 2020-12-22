package com.xy.honest_record.service.impl;

import com.xy.honest_record.entity.Accusation;
import com.xy.honest_record.mapper.AccusationMapper;
import com.xy.honest_record.service.IAccusationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
