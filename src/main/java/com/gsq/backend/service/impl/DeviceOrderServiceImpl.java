package com.gsq.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsq.backend.model.entity.DeviceOrder;
import com.gsq.backend.service.DeviceOrderService;
import com.gsq.backend.mapper.DeviceOrderMapper;
import org.springframework.stereotype.Service;

/**
* @author 86178
* @description 针对表【device_order(设备预约表)】的数据库操作Service实现
* @createDate 2023-07-13 14:33:14
*/
@Service
public class DeviceOrderServiceImpl extends ServiceImpl<DeviceOrderMapper, DeviceOrder>
    implements DeviceOrderService {

}




