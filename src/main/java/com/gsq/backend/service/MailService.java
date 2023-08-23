package com.gsq.backend.service;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/21 9:18
 * @description 邮件业务接口
 */
public interface MailService {

    /**
     * 发送纯文本邮件
     * @param to
     * @param subject
     * @param text
     * @return
     */
    Boolean sendTextMail(String to, String subject, String text);

    /**
     * 发送html邮件，可携带附件
     * @param to 收件人邮箱数组
     * @param subject 邮件主题
     * @param text 邮件内容
     * @param filename 上传附件名
     * @return
     */
    Boolean sendHtmlMail(String to, String subject, String text, String filename);
}
