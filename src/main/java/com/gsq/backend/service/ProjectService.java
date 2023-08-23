package com.gsq.backend.service;

import com.gsq.backend.model.dto.project.AddProjectRequest;
import com.gsq.backend.model.dto.project.ProjectDetailDTO;
import com.gsq.backend.model.dto.project.ProjectInfoDTO;
import com.gsq.backend.model.dto.project.UpdateProjectRequest;
import com.gsq.backend.model.entity.Project;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86178
* @description 针对表【project(项目管理表)】的数据库操作Service
* @createDate 2023-07-13 14:33:15
*/
public interface ProjectService extends IService<Project> {

    /**
     * 添加项目
     * @param addProjectRequest 添加项目请求
     * @return
     */
    Boolean addProject(AddProjectRequest addProjectRequest);

    /**
     * 更新项目信息
     * @param updateProjectRequest 添加项目请求
     * @return
     */
    Boolean updateProject(UpdateProjectRequest updateProjectRequest);

    /**
     * 根据项目id返回项目详细信息
     * @param projectId 项目id
     * @return
     */
    ProjectDetailDTO getProjectDetailById(Long projectId);

    /**
     * 根据用户id获取用户所参与的所有项目基本信息
     * @param userId 用户id
     * @return
     */
    List<ProjectInfoDTO> getSingleUserAllProjectInfoByUserId(Long userId);

    /**
     * 根据项目id删除项目
     * @param projectId
     * @return
     */
    Boolean deleteProjectById(Long projectId);

}
