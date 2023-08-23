package com.gsq.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 实验室表
 * @TableName lab
 */
@TableName(value ="lab")
@Data
public class Lab implements Serializable {
    /**
     * 实验室id
     */
    @TableId(type = IdType.AUTO)
    private Long labId;

    /**
     * 实验室名称
     */
    private String labName;

    /**
     * 联系人
     */
    private String linkman;

    /**
     * 联系电话
     */
    private String tel;

    /**
     * 实验室布局
     */
    private String layout;

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
        Lab other = (Lab) that;
        return (this.getLabId() == null ? other.getLabId() == null : this.getLabId().equals(other.getLabId()))
            && (this.getLabName() == null ? other.getLabName() == null : this.getLabName().equals(other.getLabName()))
            && (this.getLinkman() == null ? other.getLinkman() == null : this.getLinkman().equals(other.getLinkman()))
            && (this.getTel() == null ? other.getTel() == null : this.getTel().equals(other.getTel()))
            && (this.getLayout() == null ? other.getLayout() == null : this.getLayout().equals(other.getLayout()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLabId() == null) ? 0 : getLabId().hashCode());
        result = prime * result + ((getLabName() == null) ? 0 : getLabName().hashCode());
        result = prime * result + ((getLinkman() == null) ? 0 : getLinkman().hashCode());
        result = prime * result + ((getTel() == null) ? 0 : getTel().hashCode());
        result = prime * result + ((getLayout() == null) ? 0 : getLayout().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", labId=").append(labId);
        sb.append(", labName=").append(labName);
        sb.append(", linkman=").append(linkman);
        sb.append(", tel=").append(tel);
        sb.append(", layout=").append(layout);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}