package com.gsq.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gsq.backend.annotation.LoginCheck;
import com.gsq.backend.common.BaseResponse;
import com.gsq.backend.common.ErrorCode;
import com.gsq.backend.common.ResultUtils;
import com.gsq.backend.exception.BusinessException;
import com.gsq.backend.exception.ThrowUtils;
import com.gsq.backend.model.dto.user.UserLoginRequest;
import com.gsq.backend.model.entity.User;
import com.gsq.backend.model.vo.UserVO;
import com.gsq.backend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.gsq.backend.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/13 15:58
 * @description 用户controller
 */
@RestController
@RequestMapping("/user")
@Api(tags = {"用户管理控制类"})
@Slf4j
@Validated
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation("根据用户id获取用户信息")
    @GetMapping("/getUserById")
    @LoginCheck
    public BaseResponse<UserVO> getProjectById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        UserVO userVO = UserVO.parseUserVO(user);
        return ResultUtils.success(userVO);
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public BaseResponse<UserVO> userLogin(@Validated @RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        String email = userLoginRequest.getEmail();
        String password = userLoginRequest.getPassword();

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("email", email)
                .eq("user_password", password);
        User user = userService.getOne(userQueryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        UserVO userVO = UserVO.parseUserVO(user);
        request.getSession().setAttribute(USER_LOGIN_STATE, userVO);
        return ResultUtils.success(userVO);
    }

    @ApiOperation("用户注销")
    @PostMapping("/logout")
    @LoginCheck
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(USER_LOGIN_STATE);
        return ResultUtils.success(true);
    }

    @ApiOperation("获取当前登录用户信息")
    @PostMapping("/getLoginUserInfo")
    @LoginCheck
    public BaseResponse<Object> getLoginUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return ResultUtils.success(session.getAttribute(USER_LOGIN_STATE));
    }

    @ApiOperation("更新当前登录用户信息")
    @PostMapping("/updateLoginUserInfo")
    @LoginCheck
    public BaseResponse<Boolean> updateLoginUserInfo(@RequestBody UserVO userVO, HttpServletRequest request) {

        HttpSession session = request.getSession();
        UserVO userLogin = (UserVO) session.getAttribute(USER_LOGIN_STATE);
        User user = new User();
        user.setUserId(userLogin.getUserId());
        user.setRealName(userVO.getRealName());
        user.setEmail(userVO.getEmail());
        user.setOrg(userVO.getOrg());

        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("user_id", userLogin.getUserId())
                .set("real_name", userVO.getRealName())
                .set("email", userVO.getEmail())
                .set("org", userVO.getOrg());
        boolean update = userService.update(userUpdateWrapper);
        ThrowUtils.throwIf(!update, new BusinessException(ErrorCode.SYSTEM_ERROR, "更新个人信息失败"));
        return ResultUtils.success(true);
    }
}
