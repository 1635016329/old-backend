package com.gsq.backend.model.dto.device;

import com.gsq.backend.model.entity.Device;
import com.gsq.backend.model.entity.DeviceMaintenance;
import com.gsq.backend.service.DeviceMaintenanceService;
import com.gsq.backend.service.DeviceService;
import lombok.Data;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/20 16:22
 * @description 设备维护DTO
 */
@Data
public class DeviceMaintenanceDTO {
    /**
     * 设备维护id
     */
    private Long deviceMaintenanceId;

    /**
     * 设备id
     */
    private Long deviceId;

    /**
     * 设备名
     */
    private String deviceName;

    /**
     * 维护人员
     */
    private String maintenanceStaff;

    /**
     * 维护内容
     */
    private String content;

    /**
     * 备注
     */
    private String remark;

    /**
     * 维护开始时间
     */
    private Date startTime;

    /**
     * 预期结束时间
     */
    private Date expectedEndTime;

    /**
     * 实际结束时间
     */
    private Date actualEndTime;

    /**
     * 将 DeviceMaintenance 转化为 DeviceMaintenanceDTO
     * @param deviceMaintenance
     * @return
     */
    public static DeviceMaintenanceDTO parseDeviceMaintenanceDTO(DeviceMaintenance deviceMaintenance, String deviceName) {

        DeviceMaintenanceDTO deviceMaintenanceDTO = new DeviceMaintenanceDTO();
        deviceMaintenanceDTO.setDeviceMaintenanceId(deviceMaintenance.getDeviceMaintenanceId());
        deviceMaintenanceDTO.setDeviceId(deviceMaintenance.getDeviceId());
        deviceMaintenanceDTO.setDeviceName(deviceName);
        deviceMaintenanceDTO.setMaintenanceStaff(deviceMaintenance.getMaintenanceStaff());
        deviceMaintenanceDTO.setContent(deviceMaintenance.getContent());
        deviceMaintenanceDTO.setRemark(deviceMaintenance.getRemark());
        deviceMaintenanceDTO.setStartTime(deviceMaintenance.getStartTime());
        deviceMaintenanceDTO.setExpectedEndTime(deviceMaintenance.getExpectedEndTime());
        deviceMaintenanceDTO.setActualEndTime(deviceMaintenance.getActualEndTime());
        return deviceMaintenanceDTO;
    }

}
