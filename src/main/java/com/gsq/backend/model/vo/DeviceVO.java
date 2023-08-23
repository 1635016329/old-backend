package com.gsq.backend.model.vo;

import com.gsq.backend.model.entity.Device;
import com.gsq.backend.model.entity.DeviceType;
import com.gsq.backend.model.entity.Lab;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/18 10:16
 * @description 设备详细信息
 */
@Data
public class DeviceVO implements Serializable {

    /**
     * 设备id
     */
    private Long deviceId;

    /**
     * 设备分类名
     */
    private DeviceType deviceType;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 所属实验室
     */
    private Lab lab;

    /**
     * 设备功能
     */
    private String deviceFunc;

    /**
     * 设备使用说明书存储路径
     */
    private String operationInstructionUrl;

    /**
     * 是否被使用 0 不是， 1 是
     */
    private Integer isInUse;

    /**
     * 是否被维护 0 不是， 1 是
     */
    private Integer isUnderMaintenance;

    public static DeviceVO parseDeviceVO(Device device, DeviceType deviceType, Lab lab) {
        DeviceVO deviceVO = new DeviceVO();
        deviceVO.setDeviceId(device.getDeviceId());
        deviceVO.setDeviceType(deviceType);
        deviceVO.setDeviceName(device.getDeviceName());
        deviceVO.setLab(lab);
        deviceVO.setDeviceFunc(device.getDeviceFunc());
        deviceVO.setOperationInstructionUrl(device.getOperationInstructionUrl());
        deviceVO.setIsInUse(device.getIsInUse());
        deviceVO.setIsUnderMaintenance(device.getIsUnderMaintenance());
        return deviceVO;
    }

    private static final long serialVersionUID = 1L;
}
