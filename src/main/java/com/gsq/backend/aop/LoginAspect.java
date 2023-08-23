package com.gsq.backend.aop;

import com.gsq.backend.common.ErrorCode;
import com.gsq.backend.exception.BusinessException;
import com.gsq.backend.model.entity.User;
import com.gsq.backend.model.vo.UserVO;
import com.gsq.backend.service.UserService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.gsq.backend.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/14 16:40
 * @description 登录拦截
 */
@Aspect
@Component
public class LoginAspect {

    @Resource
    private UserService userService;

    @Before("@annotation(com.gsq.backend.annotation.LoginCheck)")
    public void loginCheck() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute(USER_LOGIN_STATE);
        if (user == null || userService.getById(user.getUserId()) == null) {
            //未登录
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

    }
}
