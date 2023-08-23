package com.gsq.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsq.backend.common.ErrorCode;
import com.gsq.backend.exception.BusinessException;
import com.gsq.backend.exception.ThrowUtils;
import com.gsq.backend.mapper.*;
import com.gsq.backend.model.dto.project.*;
import com.gsq.backend.model.entity.*;
import com.gsq.backend.model.enums.ProjectUserJobEnum;
import com.gsq.backend.model.vo.DeviceVO;
import com.gsq.backend.model.vo.ProjectUserVO;
import com.gsq.backend.service.LabService;
import com.gsq.backend.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
* @author 86178
* @description 针对表【project(项目管理表)】的数据库操作Service实现
* @createDate 2023-07-13 14:33:15
*/
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project>
    implements ProjectService {

    @Resource
    private ProjectTypeMapper projectTypeMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ProjectMapper projectMapper;

    @Resource
    private ProjectUserMapper projectUserMapper;

    @Resource
    private ProjectDeviceMapper projectDeviceMapper;

    @Resource
    private DeviceMapper deviceMapper;

    @Resource
    private LabMapper labMapper;

    @Resource
    private DeviceTypeMapper deviceTypeMapper;

    @Override
    @Transactional
    public Boolean addProject(AddProjectRequest addProjectRequest) {
        //查看项目名是否已经存在
        ThrowUtils.throwIf(isExistProjectName(addProjectRequest.getProjectName()), new BusinessException(ErrorCode.SYSTEM_ERROR, "该项目名已存在"));
        //获取分类id
        Long projectTypeId = getProjectTypeByTypeName(addProjectRequest.getProjectType());
        //添加项目
        Long projectId = insertProject(addProjectRequest.getProjectName(), projectTypeId, addProjectRequest.getTarget());
        //添加项目成员
        addProjectMember(addProjectRequest.getProjectMemberList(), projectId);

        //检查设备是否存在
        List<Long> deviceIdList = addProjectRequest.getDeviceIdList();
        isExistDeviceByIds(deviceIdList);
        //添加设备
        return addDeviceForProject(deviceIdList, projectId);
    }

    @Override
    @Transactional
    public Boolean updateProject(UpdateProjectRequest updateProjectRequest) {
        //获取分类id
        Long projectTypeId = getProjectTypeByTypeName(updateProjectRequest.getProjectType());
        //更新项目信息
        Project project = new Project();
        project.setProjectId(updateProjectRequest.getProjectId());
        project.setProjectName(updateProjectRequest.getProjectName());
        project.setProjectTypeId(projectTypeId);
        project.setTarget(updateProjectRequest.getTarget());
        int update = projectMapper.updateById(project);
        if (update < 1) {
            return false;
        }
        //更新项目成员
        // 1. 根据项目id删除所有项目成员
        // 2. 根据ProjectMemberList添加
        // 删除
        Long projectId = project.getProjectId();
        QueryWrapper<ProjectUser> projectUserQueryWrapper = new QueryWrapper<>();
        projectUserQueryWrapper.eq("project_id", projectId);
        projectUserMapper.delete(projectUserQueryWrapper);
        // 添加
        addProjectMember(updateProjectRequest.getProjectMemberList(), projectId);
        //更新设备信息
        //1 查找项目相关设备信息
        QueryWrapper<ProjectDevice> projectDeviceQueryWrapper = new QueryWrapper<>();
        projectDeviceQueryWrapper.eq("project_id", projectId);
        ProjectDevice projectDevice = projectDeviceMapper.selectOne(projectDeviceQueryWrapper);
        //2 更新设备信息
        //检查设备是否存在
        List<Long> deviceIdList = updateProjectRequest.getDeviceIdList();
        isExistDeviceByIds(deviceIdList);
        //将id列表转化为以逗号分隔的字符串
        StringJoiner joiner = new StringJoiner(",");
        for (Long id : deviceIdList) {
            joiner.add(String.valueOf(id));
        }
        String idsString = joiner.toString();
        projectDevice.setDeviceIds(idsString);
        int updateProjectDevice = projectDeviceMapper.updateById(projectDevice);
        return updateProjectDevice >= 1;
    }

    @Override
    public ProjectDetailDTO getProjectDetailById(Long projectId) {
        //1 根据项目id获取项目信息
        Project project = projectMapper.selectById(projectId);
        ThrowUtils.throwIf(project == null, new BusinessException(ErrorCode.NOT_FOUND_ERROR));
        //2 根据项目分类id获取项目分类名
        ProjectType projectType = projectTypeMapper.selectById(project.getProjectTypeId());
        //3 根据项目id获取项目使用设备列表
        List<DeviceVO> deviceVOList = getDeviceVOListByProjectId(projectId);
        //4 根据项目id获取项目成员列表
        List<ProjectUserVO> projectUserVOList = getProjectUserVOListByProjectId(projectId);

        ProjectDetailDTO projectDetailDTO = new ProjectDetailDTO();
        projectDetailDTO.setProjectId(projectId);
        projectDetailDTO.setProjectName(project.getProjectName());
        projectDetailDTO.setProjectTypeName(projectType.getTypeName());
        projectDetailDTO.setTarget(project.getTarget());
        projectDetailDTO.setDeviceList(deviceVOList);
        projectDetailDTO.setProjectMemberList(projectUserVOList);

        return projectDetailDTO;
    }

    @Override
    public List<ProjectInfoDTO> getSingleUserAllProjectInfoByUserId(Long userId) {
        //1 根据用户id从项目成员表中找到所有该用户参与的项目
        //2 根据项目id找到所有项目基本信息()
        //  (1) 根据项目分类id找到项目分类名
        QueryWrapper<ProjectUser> projectUserQueryWrapper = new QueryWrapper<>();
        projectUserQueryWrapper.eq("user_id", userId);
        List<ProjectUser> projectUsers = projectUserMapper.selectList(projectUserQueryWrapper);
        if (projectUsers.isEmpty()) {
            return null;
        }
        ArrayList<ProjectInfoDTO> projectInfoDTOList = new ArrayList<>();
        for (ProjectUser projectUser : projectUsers) {
            Project project = projectMapper.selectById(projectUser.getProjectId());
            if (project == null) {
                continue;
            }
            //根据分类id获取分类名
            QueryWrapper<ProjectType> projectTypeQueryWrapper = new QueryWrapper<>();
            projectTypeQueryWrapper.eq("project_type_id", project.getProjectTypeId());
            ProjectType projectType = projectTypeMapper.selectOne(projectTypeQueryWrapper);
            ProjectInfoDTO projectInfoDTO = new ProjectInfoDTO();
            projectInfoDTO.setProjectId(project.getProjectId());
            projectInfoDTO.setProjectName(project.getProjectName());
            projectInfoDTO.setProjectTypeName(projectType.getTypeName());
            projectInfoDTO.setTarget(project.getTarget());
            projectInfoDTOList.add(projectInfoDTO);
        }
        return projectInfoDTOList;
    }

    @Override
    @Transactional
    public Boolean deleteProjectById(Long projectId) {
        //1 根据项目id删除项目
        //2 根据项目id删除项目关联成员记录
        //3 根据项目id删除项目关联设备记录
        int delProject = projectMapper.deleteById(projectId);
        QueryWrapper<ProjectUser> projectUserQueryWrapper = new QueryWrapper<>();
        projectUserQueryWrapper.eq("project_id", projectId);
        int deleteProjectUser = projectUserMapper.delete(projectUserQueryWrapper);
        QueryWrapper<ProjectDevice> projectDeviceQueryWrapper = new QueryWrapper<>();
        projectDeviceQueryWrapper.eq("project_id", projectId);
        int deleteProjectDevice = projectDeviceMapper.delete(projectDeviceQueryWrapper);
        if (delProject < 1 || deleteProjectUser < 1 || deleteProjectDevice < 1) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除项目失败");
        }
        return true;
    }

    /**
     * 根据项目id获取项目成员VO列表
     * @param projectId
     * @return
     */
    private List<ProjectUserVO> getProjectUserVOListByProjectId(Long projectId) {
        QueryWrapper<ProjectUser> projectUserQueryWrapper = new QueryWrapper<>();
        projectUserQueryWrapper.eq("project_id", projectId);
        List<ProjectUser> projectUsers = projectUserMapper.selectList(projectUserQueryWrapper);
        if (projectUsers.isEmpty()) {
            return null;
        }
        ArrayList<ProjectUserVO> projectUserVOList = new ArrayList<>();
        for (ProjectUser projectUser : projectUsers) {
            User user = userMapper.selectById(projectUser.getUserId());
            ProjectUserVO projectUserVO = new ProjectUserVO();
            projectUserVO.setUserId(user.getUserId());
            projectUserVO.setRealName(user.getRealName());
            projectUserVO.setEmail(user.getEmail());
            projectUserVO.setOrg(user.getOrg());
            projectUserVO.setJob(ProjectUserJobEnum.getEnumByValue(projectUser.getUserJob()).getText());
            projectUserVOList.add(projectUserVO);
        }
        return projectUserVOList;
    }

    /**
     * 根据项目id获取设备VO列表
     * @param projectId
     * @return
     */
    private List<DeviceVO> getDeviceVOListByProjectId(Long projectId) {
        QueryWrapper<ProjectDevice> projectDeviceQueryWrapper = new QueryWrapper<>();
        projectDeviceQueryWrapper.eq("project_id", projectId);
        ProjectDevice projectDevice = projectDeviceMapper.selectOne(projectDeviceQueryWrapper);
        if (projectDevice == null) {
            return null;
        }
        String[] tempArr = projectDevice.getDeviceIds().split(",");
        ArrayList<Integer> deviceIds = new ArrayList<>();
        for (String idStr : tempArr) {
            deviceIds.add(Integer.parseInt(idStr));
        }
        ArrayList<DeviceVO> deviceVOList = new ArrayList<>();
        //遍历id查找相关设备数据
        for (Integer deviceId : deviceIds) {
            Device device = deviceMapper.selectById(deviceId);
            ThrowUtils.throwIf(device == null, new BusinessException(ErrorCode.NOT_FOUND_ERROR));
            Lab lab = labMapper.selectById(device.getLabId());
            ThrowUtils.throwIf(lab == null, new BusinessException(ErrorCode.NOT_FOUND_ERROR));
            DeviceType deviceType = deviceTypeMapper.selectById(device.getDeviceTypeId());
            ThrowUtils.throwIf(deviceType == null, new BusinessException(ErrorCode.NOT_FOUND_ERROR));
            deviceVOList.add(DeviceVO.parseDeviceVO(device, deviceType, lab));
        }
        return deviceVOList;
    }

    /**
     * 校验项目名是否存在
     * @param projectName
     * @return
     */
    private Boolean isExistProjectName(String projectName) {
        QueryWrapper<Project> projectQueryWrapper = new QueryWrapper<>();
        projectQueryWrapper.eq("project_name", projectName);
        Long count = projectMapper.selectCount(projectQueryWrapper);
        return count >= 1;
    }

    /**
     * 根据项目分类名获取项目分类id
     * @param typeName
     * @return
     */
    private Long getProjectTypeByTypeName(String typeName) {
        QueryWrapper<ProjectType> projectTypeQueryWrapper = new QueryWrapper<>();
        projectTypeQueryWrapper.eq("type_name", typeName);
        ProjectType projectType = projectTypeMapper.selectOne(projectTypeQueryWrapper);
        if (projectType == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "该项目分类不存在");
        }
        return projectType.getProjectTypeId();
    }

    /**
     * 添加项目
     * @param projectName 项目名
     * @param projectTypeId 项目分类id
     * @param target 项目目的
     * @return 项目id
     */
    private Long insertProject(String projectName, Long projectTypeId, String target) {
        Project project = new Project();
        project.setProjectName(projectName);
        project.setProjectTypeId(projectTypeId);
        project.setTarget(target);
        int insert = projectMapper.insert(project);
        ThrowUtils.throwIf(insert < 1, new BusinessException(ErrorCode.SYSTEM_ERROR, "项目添加失败"));
        return project.getProjectId();
    }

    /**
     * 根据项目成员DTO列表添加项目成员
     * @param projectMemberList 项目成员列表
     * @param projectId 项目id
     * @return
     */
    private Boolean addProjectMember(List<ProjectMemberDTO> projectMemberList, Long projectId) {
        for (ProjectMemberDTO projectMemberDTO : projectMemberList) {
            //验证该项目成员是否存在
            Long memberId = projectMemberDTO.getMemberId();
            User user = userMapper.selectById(memberId);
            if (user == null) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "id为" + memberId + "的成员不存在或被删除，请联系管理员");
            }
            ProjectUser projectUser = new ProjectUser();
            projectUser.setProjectId(projectId);
            projectUser.setUserId(projectMemberDTO.getMemberId());
            projectUser.setUserJob(ProjectUserJobEnum.getValueByText(projectMemberDTO.getJob()));
            int insert = projectUserMapper.insert(projectUser);
            if (insert < 1) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "项目成员添加失败");
            }
        }
        return true;
    }

    /**
     * 根据ids检查多个设备是否存在
     * @param deviceIdList
     * @return
     */
    private Boolean isExistDeviceByIds(List<Long> deviceIdList) {
        for (Long deviceId : deviceIdList) {
            Device device = deviceMapper.selectById(deviceId);
            if (device == null) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "id为" + deviceId + "的设备不存在或被删除，请联系管理员");
            }
        }
        return true;
    }

    private Boolean addDeviceForProject(List<Long> deviceIdList, Long projectId) {
        //将id列表转化为以逗号分隔的字符串
        StringJoiner joiner = new StringJoiner(",");
        for (Long id : deviceIdList) {
            joiner.add(String.valueOf(id));
        }
        String idsString = joiner.toString();
        ProjectDevice projectDevice = new ProjectDevice();
        projectDevice.setProjectId(projectId);
        projectDevice.setDeviceIds(idsString);
        int insert = projectDeviceMapper.insert(projectDevice);
        if (insert < 1) {
            return false;
        }
        return true;
    }
}




