package com.gsq.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsq.backend.model.entity.DeviceMaintenance;
import com.gsq.backend.service.DeviceMaintenanceService;
import com.gsq.backend.mapper.DeviceMaintenanceMapper;
import org.springframework.stereotype.Service;

/**
* @author 86178
* @description 针对表【device_maintenance(设备维护表)】的数据库操作Service实现
* @createDate 2023-07-17 11:33:19
*/
@Service
public class DeviceMaintenanceServiceImpl extends ServiceImpl<DeviceMaintenanceMapper, DeviceMaintenance>
    implements DeviceMaintenanceService{

}




