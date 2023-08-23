package com.gsq.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.gsq.backend.model.dto.info.SendMailRequest;
import lombok.Data;

/**
 * 发送邮件记录表
 * @TableName send_mail
 */
@TableName(value ="send_mail")
@Data
public class SendMail implements Serializable {
    /**
     * 发送邮件记录id
     */
    @TableId(type = IdType.AUTO)
    private Long sendMailId;

    /**
     * 接收人邮箱，以,分隔
     */
    private String receiver;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 附件名，可以为空，如果有多个附件，请用逗号分隔
     */
    private String filename;

    public static SendMail parseSendMail(SendMailRequest sendMailRequest) {
        SendMail sendMail = new SendMail();
        sendMail.setReceiver(sendMailRequest.getTo());
        sendMail.setSubject(sendMailRequest.getSubject());
        sendMail.setContent(sendMailRequest.getText());
        sendMail.setFilename(sendMailRequest.getFilename());
        return sendMail;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}