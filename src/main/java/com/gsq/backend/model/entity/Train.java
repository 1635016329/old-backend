package com.gsq.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 培训表
 * @TableName train
 */
@TableName(value ="train")
@Data
public class Train implements Serializable {
    /**
     * 培训项目id
     */
    @TableId(type = IdType.AUTO)
    private Long trainId;

    /**
     * 培训项目名称
     */
    private String trainName;

    /**
     * 培训介绍
     */
    private String trainIntroduction;

    /**
     * 培训内容电子文档
     */
    private String docUrl;

    /**
     * 培训预计时长
     */
    private String trainDuration;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}