package com.gsq.backend.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/17 9:34
 * @description 项目人员职责枚举
 */
public enum ProjectUserJobEnum {
    GROUP_LEADER("组长", 0),
    GROUP_MEMBER("成员", 1),
    EXTERNAL_EXPERT("外聘专家", 2);

    private final String text;

    private final int value;

    ProjectUserJobEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static ProjectUserJobEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (ProjectUserJobEnum anEnum : ProjectUserJobEnum.values()) {
            if (anEnum.value == value) {
                return anEnum;
            }
        }
        return null;
    }

    /**
     * 根据 text 获取对应 value
     * @param text
     * @return
     */
    public static Integer getValueByText(String text) {
        if (text == null || text.length() < 1) {
            return null;
        }
        for (ProjectUserJobEnum anEnum : ProjectUserJobEnum.values()) {
            if (Objects.equals(anEnum.text, text)) {
                return anEnum.getValue();
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
