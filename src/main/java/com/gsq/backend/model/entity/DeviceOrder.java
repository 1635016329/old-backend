package com.gsq.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 设备预约表
 * @TableName device_order
 */
@TableName(value ="device_order")
@Data
public class DeviceOrder implements Serializable {
    /**
     * 设备预约id
     */
    @TableId(type = IdType.AUTO)
    private Long orderId;

    /**
     * 预约用户id
     */
    private Long userId;

    /**
     * 预约设备id
     */
    private Long deviceId;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 预约状态：0 未审核 1 审核通过 2 审核未通过 3 正在生效 4 过期
     */
    private Integer orderState;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}