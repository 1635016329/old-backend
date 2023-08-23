package com.gsq.backend.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/26 14:50
 * @description 正在被使用设备VO
 */
@Data
public class DeviceUsedVO {
    /**
     * 设备id
     */
    private Long deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 使用者
     */
    private UserVO userVO;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

}
