package com.gsq.backend.aop;

import com.gsq.backend.annotation.AuthCheck;
import com.gsq.backend.common.ErrorCode;
import com.gsq.backend.exception.BusinessException;
import com.gsq.backend.model.enums.UserRoleEnum;
import com.gsq.backend.model.vo.UserVO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.gsq.backend.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/14 10:15
 * @description 权限认证
 */
@Aspect
@Component
public class AuthAspect {

    /**
     * 执行拦截
     *
     * @param joinPoint
     * @param authCheck
     * @return
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        Integer mustRole = authCheck.mustRole();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute(USER_LOGIN_STATE);
        if (user == null) {
            //未登录
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 必须有该权限才通过
        if (mustRole != null) {
            UserRoleEnum mustUserRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
            if (mustUserRoleEnum == null) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
            Integer userRole = user.getUserRole();
            // 必须有管理员权限
            if (UserRoleEnum.ADMIN.equals(mustUserRoleEnum)) {
                if (userRole != null && !mustRole.equals(userRole)) {
                    throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
                }
            }
        }
        // 通过权限校验，放行
        return joinPoint.proceed();
    }
}
