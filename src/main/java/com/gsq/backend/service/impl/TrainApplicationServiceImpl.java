package com.gsq.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsq.backend.model.entity.TrainApplication;
import com.gsq.backend.service.TrainApplicationService;
import com.gsq.backend.mapper.TrainApplicationMapper;
import org.springframework.stereotype.Service;

/**
* @author 86178
* @description 针对表【train_application(培训申请表)】的数据库操作Service实现
* @createDate 2023-07-31 10:03:02
*/
@Service
public class TrainApplicationServiceImpl extends ServiceImpl<TrainApplicationMapper, TrainApplication>
    implements TrainApplicationService{

}




