package com.gsq.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 设备信息表
 * @TableName device
 */
@TableName(value ="device")
@Data
public class Device implements Serializable {
    /**
     * 设备id
     */
    @TableId(type = IdType.AUTO)
    private Long deviceId;

    /**
     * 设备分类id
     */
    private Long deviceTypeId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 所属实验室
     */
    private Long labId;

    /**
     * 设备功能
     */
    private String deviceFunc;

    /**
     * 设备使用说明书存储路径
     */
    private String operationInstructionUrl;

    /**
     * 是否被使用 0 不是， 1 是
     */
    private Integer isInUse;

    /**
     * 是否被维护 0 不是， 1 是
     */
    private Integer isUnderMaintenance;

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
        Device other = (Device) that;
        return (this.getDeviceId() == null ? other.getDeviceId() == null : this.getDeviceId().equals(other.getDeviceId()))
            && (this.getDeviceTypeId() == null ? other.getDeviceTypeId() == null : this.getDeviceTypeId().equals(other.getDeviceTypeId()))
            && (this.getDeviceName() == null ? other.getDeviceName() == null : this.getDeviceName().equals(other.getDeviceName()))
            && (this.getLabId() == null ? other.getLabId() == null : this.getLabId().equals(other.getLabId()))
            && (this.getDeviceFunc() == null ? other.getDeviceFunc() == null : this.getDeviceFunc().equals(other.getDeviceFunc()))
            && (this.getOperationInstructionUrl() == null ? other.getOperationInstructionUrl() == null : this.getOperationInstructionUrl().equals(other.getOperationInstructionUrl()))
            && (this.getIsInUse() == null ? other.getIsInUse() == null : this.getIsInUse().equals(other.getIsInUse()))
            && (this.getIsUnderMaintenance() == null ? other.getIsUnderMaintenance() == null : this.getIsUnderMaintenance().equals(other.getIsUnderMaintenance()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDeviceId() == null) ? 0 : getDeviceId().hashCode());
        result = prime * result + ((getDeviceTypeId() == null) ? 0 : getDeviceTypeId().hashCode());
        result = prime * result + ((getDeviceName() == null) ? 0 : getDeviceName().hashCode());
        result = prime * result + ((getLabId() == null) ? 0 : getLabId().hashCode());
        result = prime * result + ((getDeviceFunc() == null) ? 0 : getDeviceFunc().hashCode());
        result = prime * result + ((getOperationInstructionUrl() == null) ? 0 : getOperationInstructionUrl().hashCode());
        result = prime * result + ((getIsInUse() == null) ? 0 : getIsInUse().hashCode());
        result = prime * result + ((getIsUnderMaintenance() == null) ? 0 : getIsUnderMaintenance().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", deviceId=").append(deviceId);
        sb.append(", deviceTypeId=").append(deviceTypeId);
        sb.append(", deviceName=").append(deviceName);
        sb.append(", labId=").append(labId);
        sb.append(", deviceFunc=").append(deviceFunc);
        sb.append(", operationInstructionUrl=").append(operationInstructionUrl);
        sb.append(", isInUse=").append(isInUse);
        sb.append(", isUnderMaintenance=").append(isUnderMaintenance);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}