package com.gsq.backend.model.dto.user;

import lombok.Data;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/24 9:36
 * @description 人员档案
 */
@Data
public class UserFile {
    private Integer id;
    private String realName;
    private String email;
    private String org;
}
