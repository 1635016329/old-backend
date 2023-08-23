package com.gsq.backend.model.enums;

import io.swagger.models.auth.In;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/8/1 10:13
 * @description 培训申请状态枚举
 */
public enum AuditStateEnum {

    NOT_AUDIT("未审核", 0),
    PASS_AUDIT("审核通过", 1),
    REJECT_AUDIT("审核未通过", 2)
    ;
    private final String text;

    private final Integer value;

    AuditStateEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public Integer getValue() {
        return value;
    }
}
