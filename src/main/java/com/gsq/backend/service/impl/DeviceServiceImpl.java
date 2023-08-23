package com.gsq.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsq.backend.model.entity.Device;
import com.gsq.backend.service.DeviceService;
import com.gsq.backend.mapper.DeviceMapper;
import org.springframework.stereotype.Service;

/**
* @author 86178
* @description 针对表【device(设备信息表)】的数据库操作Service实现
* @createDate 2023-07-17 11:58:28
*/
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device>
    implements DeviceService{

}




