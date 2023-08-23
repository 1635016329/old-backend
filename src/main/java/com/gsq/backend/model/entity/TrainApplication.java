package com.gsq.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 培训申请表
 * @TableName train_application
 */
@TableName(value ="train_application")
@Data
public class TrainApplication implements Serializable {
    /**
     * 培训申请id
     */
    @TableId(type = IdType.AUTO)
    private Long trainApplicationId;

    /**
     * 申请用户id
     */
    private Long userId;

    /**
     * 所申请的培训id
     */
    private Long trainId;

    /**
     * 培训介绍
     */
    private String trainIntroduction;

    /**
     * 申请状态，0：未审核，1 审核通过， 2：审核不通过
     */
    private Integer state;

    /**
     * 驳回原因
     */
    private String rejectReason;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}