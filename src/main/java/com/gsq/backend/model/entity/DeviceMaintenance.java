package com.gsq.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 设备维护表
 * @TableName device_maintenance
 */
@TableName(value ="device_maintenance")
@Data
public class DeviceMaintenance implements Serializable {
    /**
     * 设备维护id
     */
    @TableId(type = IdType.AUTO)
    private Long deviceMaintenanceId;

    /**
     * 设备id
     */
    private Long deviceId;

    /**
     * 维护人员
     */
    private String maintenanceStaff;

    /**
     * 维护内容
     */
    private String content;

    /**
     * 备注
     */
    private String remark;

    /**
     * 维护开始时间
     */
    private Date startTime;

    /**
     * 预期结束时间
     */
    private Date expectedEndTime;

    /**
     * 实际结束时间
     */
    private Date actualEndTime;

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
        DeviceMaintenance other = (DeviceMaintenance) that;
        return (this.getDeviceMaintenanceId() == null ? other.getDeviceMaintenanceId() == null : this.getDeviceMaintenanceId().equals(other.getDeviceMaintenanceId()))
            && (this.getDeviceId() == null ? other.getDeviceId() == null : this.getDeviceId().equals(other.getDeviceId()))
            && (this.getMaintenanceStaff() == null ? other.getMaintenanceStaff() == null : this.getMaintenanceStaff().equals(other.getMaintenanceStaff()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getExpectedEndTime() == null ? other.getExpectedEndTime() == null : this.getExpectedEndTime().equals(other.getExpectedEndTime()))
            && (this.getActualEndTime() == null ? other.getActualEndTime() == null : this.getActualEndTime().equals(other.getActualEndTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDeviceMaintenanceId() == null) ? 0 : getDeviceMaintenanceId().hashCode());
        result = prime * result + ((getDeviceId() == null) ? 0 : getDeviceId().hashCode());
        result = prime * result + ((getMaintenanceStaff() == null) ? 0 : getMaintenanceStaff().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getExpectedEndTime() == null) ? 0 : getExpectedEndTime().hashCode());
        result = prime * result + ((getActualEndTime() == null) ? 0 : getActualEndTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", deviceMaintenanceId=").append(deviceMaintenanceId);
        sb.append(", deviceId=").append(deviceId);
        sb.append(", maintenanceStaff=").append(maintenanceStaff);
        sb.append(", content=").append(content);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(startTime);
        sb.append(", expectedEndTime=").append(expectedEndTime);
        sb.append(", actualEndTime=").append(actualEndTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}