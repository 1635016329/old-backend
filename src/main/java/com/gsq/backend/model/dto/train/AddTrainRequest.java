package com.gsq.backend.model.dto.train;

import lombok.Data;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/31 10:19
 * @description 添加培训请求
 */
@Data
public class AddTrainRequest {
    /**
     * 培训项目名称
     */
    private String trainName;

    /**
     * 培训介绍
     */
    private String trainIntroduction;

    /**
     * 培训内容电子文档
     */
    private String docUrl;

    /**
     * 培训预计时长
     */
    private String trainDuration;

}
