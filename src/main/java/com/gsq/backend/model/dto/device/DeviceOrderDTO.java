package com.gsq.backend.model.dto.device;

import com.gsq.backend.model.vo.DeviceVO;
import com.gsq.backend.model.vo.UserVO;
import lombok.Data;

import java.util.Date;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/25 10:10
 * @description 设备预约申请
 */
@Data
public class DeviceOrderDTO {
    /**
     * 预约用户id
     */
    private UserVO userVO;

    /**
     * 预约设备id
     */
    private DeviceVO deviceVO;

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
}
