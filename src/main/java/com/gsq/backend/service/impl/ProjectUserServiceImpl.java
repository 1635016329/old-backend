package com.gsq.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsq.backend.model.entity.ProjectUser;
import com.gsq.backend.service.ProjectUserService;
import com.gsq.backend.mapper.ProjectUserMapper;
import org.springframework.stereotype.Service;

/**
* @author 86178
* @description 针对表【project_user(项目用户联系表)】的数据库操作Service实现
* @createDate 2023-07-13 14:33:15
*/
@Service
public class ProjectUserServiceImpl extends ServiceImpl<ProjectUserMapper, ProjectUser>
    implements ProjectUserService {

}




