package com.gsq.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 培训表
 * @TableName train_video
 */
@TableName(value ="train_video")
@Data
public class TrainVideo implements Serializable {
    /**
     * 培训视频id
     */
    @TableId(type = IdType.AUTO)
    private Long trainVideoId;

    /**
     * 视频标题
     */
    private String videoTitle;

    /**
     * 视频描述
     */
    private String videoDescription;

    /**
     * 视频存储地址
     */
    private String videoUrl;

    /**
     * 培训id
     */
    private Long trainId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}