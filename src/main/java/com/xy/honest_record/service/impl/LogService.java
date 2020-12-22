package com.xy.honest_record.service.impl;

import com.xy.honest_record.entity.Log;
import com.xy.honest_record.mapper.LogMapper;
import com.xy.honest_record.service.ILogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志 服务实现类
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@Service
public class LogService extends ServiceImpl<LogMapper, Log> implements ILogService {

}
