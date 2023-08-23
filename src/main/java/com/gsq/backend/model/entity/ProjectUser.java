package com.gsq.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 项目用户联系表
 * @TableName project_user
 */
@TableName(value ="project_user")
@Data
public class ProjectUser implements Serializable {
    /**
     * 项目用户联系id
     */
    @TableId(type = IdType.AUTO)
    private Long projectUserId;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 所属成员id
     */
    private Long userId;

    /**
     * 项目组成员职责
     */
    private Integer userJob;

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
        ProjectUser other = (ProjectUser) that;
        return (this.getProjectUserId() == null ? other.getProjectUserId() == null : this.getProjectUserId().equals(other.getProjectUserId()))
            && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getUserJob() == null ? other.getUserJob() == null : this.getUserJob().equals(other.getUserJob()))
            ;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getProjectUserId() == null) ? 0 : getProjectUserId().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getUserJob() == null) ? 0 : getUserJob().hashCode());

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", projectUserId=").append(projectUserId);
        sb.append(", projectId=").append(projectId);
        sb.append(", userId=").append(userId);
        sb.append(", userJob=").append(userJob);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}