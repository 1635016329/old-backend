package com.gsq.backend.controller;

import com.gsq.backend.common.BaseResponse;
import com.gsq.backend.common.DeleteRequest;
import com.gsq.backend.common.ErrorCode;
import com.gsq.backend.common.ResultUtils;
import com.gsq.backend.constant.UserConstant;
import com.gsq.backend.exception.BusinessException;
import com.gsq.backend.exception.ThrowUtils;
import com.gsq.backend.model.dto.project.AddProjectRequest;
import com.gsq.backend.model.dto.project.ProjectDetailDTO;
import com.gsq.backend.model.dto.project.ProjectInfoDTO;
import com.gsq.backend.model.dto.project.UpdateProjectRequest;
import com.gsq.backend.model.entity.Project;
import com.gsq.backend.model.vo.UserVO;
import com.gsq.backend.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.BlockingDeque;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/13 15:36
 * @description 项目管理controller
 */
@RestController
@RequestMapping("/project")
@Api(tags = {"项目管理控制类"})
@Slf4j
public class ProjectManageController {

    @Resource
    private ProjectService projectService;

    @ApiOperation("新建项目")
    @PostMapping("/add")
    public BaseResponse<Boolean> addProject(@RequestBody AddProjectRequest addProjectRequest) {
        Boolean save = projectService.addProject(addProjectRequest);
        ThrowUtils.throwIf(!save, new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库添加失败"));
        return ResultUtils.success(true);
    }

    @ApiOperation("更新项目信息")
    @PostMapping("/update")
    public BaseResponse<Boolean> updateProject(@RequestBody UpdateProjectRequest updateProjectRequest) {
        Boolean update = projectService.updateProject(updateProjectRequest);
        ThrowUtils.throwIf(!update, new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库更新失败"));
        return ResultUtils.success(true);
    }

    @ApiOperation("根据项目id删除项目")
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteProjectById(Long projectId) {
        return ResultUtils.success(projectService.deleteProjectById(projectId));
    }

    @ApiOperation("根据项目id获取项目详细信息")
    @GetMapping("/getProjectDetailById")
    public BaseResponse<ProjectDetailDTO> getProjectDetailById(Long projectId) {
        ProjectDetailDTO projectDetail = projectService.getProjectDetailById(projectId);
        return ResultUtils.success(projectDetail);
    }

    @ApiOperation("根据用户id获取用户所参与的所有项目基本信息")
    @GetMapping("/getSingleUserAllProjectInfoByUserId")
    public BaseResponse<List<ProjectInfoDTO>> getSingleUserAllProjectInfoByUserId(Long userId) {
        return ResultUtils.success(projectService.getSingleUserAllProjectInfoByUserId(userId));
    }

    @ApiOperation("获取当前登录用户所参与的所有项目基本信息")
    @GetMapping("/getLoginUserAllProjectInfoByUserId")
    public BaseResponse<List<ProjectInfoDTO>> getLoginUserAllProjectInfoByUserId(HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute(UserConstant.USER_LOGIN_STATE);
        return ResultUtils.success(projectService.getSingleUserAllProjectInfoByUserId(userVO.getUserId()));
    }

}
