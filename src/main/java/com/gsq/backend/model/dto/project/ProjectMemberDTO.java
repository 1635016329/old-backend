package com.gsq.backend.model.dto.project;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/17 10:13
 * @description 项目关联成员DTO
 */
@Data
public class ProjectMemberDTO implements Serializable {
    /**
     * 项目成员id
     */
    private Long memberId;

    /**
     * 项目成员职责(组长、成员、外聘专家)
     */
    private String job;

    private static final long serialVersionUID = 1L;
}
