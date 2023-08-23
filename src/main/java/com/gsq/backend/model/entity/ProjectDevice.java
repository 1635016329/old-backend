package com.gsq.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 项目设备联系表
 * @TableName project_device
 */
@TableName(value ="project_device")
@Data
public class ProjectDevice implements Serializable {
    /**
     * 项目设备联系id
     */
    @TableId(type = IdType.AUTO)
    private Long projectDeviceId;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 项目使用设备id数组，使用英文逗号分隔
     */
    private String deviceIds;

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
        ProjectDevice other = (ProjectDevice) that;
        return (this.getProjectDeviceId() == null ? other.getProjectDeviceId() == null : this.getProjectDeviceId().equals(other.getProjectDeviceId()))
            && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getDeviceIds() == null ? other.getDeviceIds() == null : this.getDeviceIds().equals(other.getDeviceIds()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getProjectDeviceId() == null) ? 0 : getProjectDeviceId().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getDeviceIds() == null) ? 0 : getDeviceIds().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", projectDeviceId=").append(projectDeviceId);
        sb.append(", projectId=").append(projectId);
        sb.append(", deviceIds=").append(deviceIds);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}