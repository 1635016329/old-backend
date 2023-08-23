package com.gsq.backend.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/18 10:28
 * @description 项目成员VO
 */
@Data
public class ProjectUserVO implements Serializable {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 邮箱，账号为邮箱地址
     */
    private String email;

    /**
     * 所属组织
     */
    private String org;

    /**
     * 项目成员职责
     */
    private String job;


    private static final long serialVersionUID = 1L;
}
