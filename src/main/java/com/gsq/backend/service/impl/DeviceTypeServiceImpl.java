package com.gsq.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsq.backend.model.entity.DeviceType;
import com.gsq.backend.service.DeviceTypeService;
import com.gsq.backend.mapper.DeviceTypeMapper;
import org.springframework.stereotype.Service;

/**
* @author 86178
* @description 针对表【device_type(设备分类表)】的数据库操作Service实现
* @createDate 2023-07-13 14:33:14
*/
@Service
public class DeviceTypeServiceImpl extends ServiceImpl<DeviceTypeMapper, DeviceType>
    implements DeviceTypeService {

}




