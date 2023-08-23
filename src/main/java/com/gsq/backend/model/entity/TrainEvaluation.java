package com.gsq.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 培训考评表
 * @TableName train_evaluation
 */
@TableName(value ="train_evaluation")
@Data
public class TrainEvaluation implements Serializable {
    /**
     * 培训考评表
     */
    @TableId(type = IdType.AUTO)
    private Long trainEvaluationId;

    /**
     * 培训学员id
     */
    private Long userId;

    /**
     * 培训项目id
     */
    private Long trainId;

    /**
     * 考评分数
     */
    private String evaluationScore;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}