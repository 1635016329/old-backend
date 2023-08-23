package com.gsq.backend.model.dto.project;

import com.gsq.backend.model.vo.DeviceVO;
import com.gsq.backend.model.vo.ProjectUserVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/18 10:13
 * @description 项目详细信息DTO
 */
@Data
public class ProjectDetailDTO implements Serializable {
    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 项目名
     */
    private String projectName;

    /**
     * 项目分类
     */
    private String projectTypeName;

    /**
     * 项目目的
     */
    private String target;

    /**
     * 项目使用设备列表
     */
    private List<DeviceVO> deviceList;

    /**
     * 项目成员列表
     */
    private List<ProjectUserVO> projectMemberList;

    private static final long serialVersionUID = 1L;
}
