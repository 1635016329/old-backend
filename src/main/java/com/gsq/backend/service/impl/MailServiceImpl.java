package com.gsq.backend.service.impl;

import com.gsq.backend.common.ErrorCode;
import com.gsq.backend.config.FileConfigProperties;
import com.gsq.backend.exception.BusinessException;
import com.gsq.backend.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Arrays;
import java.util.Date;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/21 9:20
 * @description 邮件业务接口实现类
 */
@Service
@Slf4j
public class MailServiceImpl implements MailService {

    /**
     * 注入邮件工具类
     */
    @Resource
    private JavaMailSenderImpl javaMailSender;

    @Resource
    private FileConfigProperties fileConfigProperties;

    @Value("${spring.mail.username}")
    private String sendMailer;

    @Override
    public Boolean sendTextMail(String to, String subject, String text) {
        try {
            //true 代表支持复杂的类型
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
            //邮件发信人
            mimeMessageHelper.setFrom(sendMailer);
            //邮件收信人  1或多个
            mimeMessageHelper.setTo(to.split(","));
            //邮件主题
            mimeMessageHelper.setSubject(subject);
            //邮件内容
            mimeMessageHelper.setText(text);
            //邮件发送时间
            mimeMessageHelper.setSentDate(new Date());

            //发送邮件
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            log.info("发送邮件成功：" + sendMailer + "->" + Arrays.toString(to.split(",")));

        } catch (MessagingException e) {
            e.printStackTrace();
            log.error("发送邮件失败：" + e.getMessage());
            return false;
        }
        return true;

    }

    @Override
    public Boolean sendHtmlMail(String to, String subject, String text, String filename) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //邮件发件人
            helper.setFrom(sendMailer);
            //邮件收件人 1或多个
            helper.setTo(to.split(","));
            //邮件主题
            helper.setSubject(subject);
            //邮件内容
            helper.setText(text, true);
            //邮件发送时间
            helper.setSentDate(new Date());
            String filePath = fileConfigProperties.getUploadPath() + filename;
            if (StringUtils.hasText(filePath)) {
                FileSystemResource file = new FileSystemResource(new File(filePath));
                String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
                helper.addAttachment(fileName, file);
            }
            javaMailSender.send(message);
            log.info("发送邮件成功:{}->{}", sendMailer, Arrays.toString(to.split(",")));
        } catch (MessagingException e) {
            log.error("发送邮件时发生异常！", e);
            return false;
        }
        return true;
    }
}
