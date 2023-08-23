package com.gsq.backend.model.dto.device;

import lombok.Data;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/20 11:25
 * @description 添加设备请求DTO
 */
@Data
public class AddDeviceRequest {

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
