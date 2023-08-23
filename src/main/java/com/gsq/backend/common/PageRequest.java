package com.gsq.backend.common;

import com.gsq.backend.constant.CommonConstant;
import lombok.Data;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/13 15:42
 * @description 分页请求
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private long current = 1;

    /**
     * 页面大小
     */
    private long pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;
}