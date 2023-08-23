package com.gsq.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsq.backend.model.entity.SendMail;
import com.gsq.backend.service.SendMailService;
import com.gsq.backend.mapper.SendMailMapper;
import org.springframework.stereotype.Service;

/**
* @author 86178
* @description 针对表【send_mail(发送邮件记录表)】的数据库操作Service实现
* @createDate 2023-07-21 14:20:21
*/
@Service
public class SendMailServiceImpl extends ServiceImpl<SendMailMapper, SendMail>
    implements SendMailService{

}




