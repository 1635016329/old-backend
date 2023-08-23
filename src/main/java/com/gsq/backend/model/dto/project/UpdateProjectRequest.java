package com.gsq.backend.model.dto.project;

import lombok.Data;

import java.util.List;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/17 16:14
 * @description 更新项目信息
 */
@Data
public class UpdateProjectRequest {

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
    private String projectType;

    /**
     * 项目目的
     */
    private String target;

    /**
     * 项目使用设备id列表
     */
    private List<Long> deviceIdList;

    /**
     * 项目成员(成员id, 职责[组长，成员，外聘专家])列表
     */
    private List<ProjectMemberDTO> projectMemberList;

}
