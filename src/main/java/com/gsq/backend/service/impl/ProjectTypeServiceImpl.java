package com.gsq.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsq.backend.model.entity.ProjectType;
import com.gsq.backend.service.ProjectTypeService;
import com.gsq.backend.mapper.ProjectTypeMapper;
import org.springframework.stereotype.Service;

/**
* @author 86178
* @description 针对表【project_type(项目分类表)】的数据库操作Service实现
* @createDate 2023-07-13 14:33:15
*/
@Service
public class ProjectTypeServiceImpl extends ServiceImpl<ProjectTypeMapper, ProjectType>
    implements ProjectTypeService {

}




