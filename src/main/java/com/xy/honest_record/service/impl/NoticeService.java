package com.xy.honest_record.service.impl;

import com.xy.honest_record.entity.Notice;
import com.xy.honest_record.mapper.NoticeMapper;
import com.xy.honest_record.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通知公告 服务实现类
 * </p>
 *
 * @author xuyong
 * @since 2020-12-22
 */
@Service
public class NoticeService extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

}
