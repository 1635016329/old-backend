package com.gsq.backend.constant;

import com.gsq.backend.model.vo.UserVO;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/14 14:15
 * @description 用户模块常量
 */
public interface UserConstant {

    /**
     * 保存登录用户信息
     */
    ThreadLocal<UserVO> USER_LOGIN = new ThreadLocal<>();

    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "user_login";

    /**
     * 初始密码
     */
    String USER_INIT_PWD = "12345678";

    //  region 权限

    /**
     * 默认角色(普通用户)
     */
    int DEFAULT_ROLE = 0;

    /**
     * 管理员角色
     */
    int ADMIN_ROLE = 1;

    /**
     * 项目组组长
     */
    int GROUP_LEADER = 2;



}
