package com.gsq.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 项目培训表
 * @TableName train_program
 */
@TableName(value ="train_program")
@Data
public class TrainProgram implements Serializable {
    /**
     * 培训项目编号
     */
    @TableId(type = IdType.AUTO)
    private Long trainProgramId;

    /**
     * 培训项目名称
     */
    private String programName;

    /**
     * 培训项目介绍
     */
    private String programIntroduction;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TrainProgram other = (TrainProgram) that;
        return (this.getTrainProgramId() == null ? other.getTrainProgramId() == null : this.getTrainProgramId().equals(other.getTrainProgramId()))
            && (this.getProgramName() == null ? other.getProgramName() == null : this.getProgramName().equals(other.getProgramName()))
            && (this.getProgramIntroduction() == null ? other.getProgramIntroduction() == null : this.getProgramIntroduction().equals(other.getProgramIntroduction()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTrainProgramId() == null) ? 0 : getTrainProgramId().hashCode());
        result = prime * result + ((getProgramName() == null) ? 0 : getProgramName().hashCode());
        result = prime * result + ((getProgramIntroduction() == null) ? 0 : getProgramIntroduction().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", trainProgramId=").append(trainProgramId);
        sb.append(", programName=").append(programName);
        sb.append(", programIntroduction=").append(programIntroduction);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}