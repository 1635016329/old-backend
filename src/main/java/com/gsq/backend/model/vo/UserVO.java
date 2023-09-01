package com.gsq.backend.model.vo;

import com.gsq.backend.model.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/14 10:26
 * @description 脱敏用户
 */
@Data
public class UserVO implements Serializable {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 用户角色, 0 普通用户 1 管理员
     */
    private Integer userRole;

    /**
     * 邮箱，账号为邮箱地址
     */
    private String email;

    /**
     * 所属组织
     */
    private String org;

    /**
     * user to userVO, 脱敏
     * @param user
     * @return
     */
    public static UserVO parseUserVO(User user) {
        return Optional.ofNullable(user)
                .map(u -> {
                    UserVO userVO = new UserVO();
                    userVO.setUserId(u.getUserId());
                    userVO.setRealName(u.getUsername());
                    userVO.setUserRole(u.getUserRole());
                    userVO.setEmail(u.getEmail());
                    userVO.setOrg(u.getOrg());
                    return userVO;
                })
                .orElse(null);
    }

    private static final long serialVersionUID = 1L;
}
