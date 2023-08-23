package com.gsq.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsq.backend.model.entity.Notice;
import com.gsq.backend.service.NoticeService;
import com.gsq.backend.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

/**
* @author 86178
* @description 针对表【notice(公告表)】的数据库操作Service实现
* @createDate 2023-07-21 15:25:19
*/
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice>
    implements NoticeService{

}




