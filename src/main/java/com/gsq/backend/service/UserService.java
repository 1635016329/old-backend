package com.gsq.backend.service;

import com.gsq.backend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86178
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-07-13 14:33:15
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param user
     * @return 返回注册用户id
     */
    long userRegister(User user);
}
