package com.gsq.backend.model.dto.train;

import lombok.Data;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/8/1 10:05
 * @description 审核培训申请请求
 */
@Data
public class AuditTrainApplicationRequest {
    /**
     * 审核的培训申请id
     */
    private Long trainApplicationId;

    /**
     * 审核结果
     */
    private Boolean result;

    /**
     * 不通过原因
     */
    private String rejectReason;
}
