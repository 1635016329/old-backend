package com.gsq.backend.model.dto.user;

import lombok.Data;

import javax.validation.constraints.Email;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/14 13:47
 * @description 用户登录请求model
 */
@Data
public class UserLoginRequest {

    @Email(message = "请填写正确的邮箱地址")
    private String email;

    private String password;
}
