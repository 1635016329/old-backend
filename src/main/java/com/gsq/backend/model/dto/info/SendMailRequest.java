package com.gsq.backend.model.dto.info;

import lombok.Data;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/21 10:06
 * @description 发送邮件请求
 */
@Data
public class SendMailRequest {

    /**
     * 接收人, 有多个用逗号分隔
     */
    private String to;

    /**
     * 文件主题
     */
    private String subject;

    /**
     * 文件内容
     */
    private String text;

    /**
     * 上传文件名，有多个用逗号分隔
     */
    private String filename;

}
