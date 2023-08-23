package com.gsq.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsq.backend.common.ErrorCode;
import com.gsq.backend.exception.BusinessException;
import com.gsq.backend.model.entity.User;
import com.gsq.backend.service.UserService;
import com.gsq.backend.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 86178
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2023-07-13 14:33:15
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Override
    public long userRegister(User user) {
        String email = user.getEmail();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("email", email);
        User exist = getOne(userQueryWrapper);
        if (exist != null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "该邮箱已被注册");
        }
        user.setUserPassword("123456");
        boolean save = save(user);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库错误, 注册失败");
        }
        return user.getUserId();
    }
}




