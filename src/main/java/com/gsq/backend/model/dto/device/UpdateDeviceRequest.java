package com.gsq.backend.model.dto.device;

import lombok.Data;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/20 14:12
 * @description 更新设备请求
 */
@Data
public class UpdateDeviceRequest {
    /**
     * 设备id
     */
    private Long deviceId;

    /**
     * 设备分类id
     */
    private Long deviceTypeId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 所属实验室id
     */
    private Long labId;

    /**
     * 设备功能
     */
    private String deviceFunc;

    /**
     * uuid, 设备上传后，后端返回前端的一个uuid, 用于 pdf 存储命名
     */
    private String uuid;


}
