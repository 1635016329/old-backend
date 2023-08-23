package com.gsq.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gsq.backend.common.BaseResponse;
import com.gsq.backend.common.ErrorCode;
import com.gsq.backend.common.FileUtils;
import com.gsq.backend.common.ResultUtils;
import com.gsq.backend.config.FileConfigProperties;
import com.gsq.backend.exception.BusinessException;
import com.gsq.backend.exception.ThrowUtils;
import com.gsq.backend.model.dto.info.SendMailRequest;
import com.gsq.backend.model.entity.Notice;
import com.gsq.backend.model.entity.SendMail;
import com.gsq.backend.service.MailService;
import com.gsq.backend.service.NoticeService;
import com.gsq.backend.service.SendMailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/21 9:14
 * @description 信息管理控制器
 */
@RestController
@RequestMapping("/info")
@Api(tags = {"信息管理控制器"})
@Slf4j
public class InfoManageController {

    @Resource
    private MailService mailService;

    @Resource
    private SendMailService sendMailService;

    @Resource
    private NoticeService noticeService;

    @Resource
    private FileConfigProperties fileConfigProperties;

    @ApiOperation("发送纯文本邮件")
    @PostMapping("/sendTextMail")
    public BaseResponse<Boolean> sendTextMail(@RequestBody SendMailRequest sendMailRequest) {
        Boolean send = mailService.sendTextMail(sendMailRequest.getTo(), sendMailRequest.getSubject(), sendMailRequest.getText());
        ThrowUtils.throwIf(!send, new BusinessException(ErrorCode.SYSTEM_ERROR, "邮件发送失败"));
        return ResultUtils.success(true);
    }

    @ApiOperation("发送html邮件, 可带附件")
    @PostMapping("/sendHtmlMail")
    public BaseResponse<Boolean> sendHtmlMail(@RequestBody SendMailRequest sendMailRequest) {
        for (String filename : sendMailRequest.getFilename().split(",")) {
            Boolean send = mailService.sendHtmlMail(sendMailRequest.getTo(), sendMailRequest.getSubject(), sendMailRequest.getText(), filename);
            ThrowUtils.throwIf(!send, new BusinessException(ErrorCode.SYSTEM_ERROR, "邮件发送失败"));
            //发送邮件成功后，将附件转移到mailFile文件夹下，再添加发送记录
            try {
                Path sourcePath = Paths.get(fileConfigProperties.getUploadPath() + filename);
                boolean exists = Files.exists(sourcePath);
                ThrowUtils.throwIf(!exists, new BusinessException(ErrorCode.NOT_FOUND_ERROR));
                Path targetPath = Paths.get(fileConfigProperties.getMailFilePath() + filename);
                Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                log.info("文件转移成功");
            } catch (Exception e) {
                e.printStackTrace();
                throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "上传文件找不到，请重新上传");
            }
        }
        SendMail sendMail = SendMail.parseSendMail(sendMailRequest);
        boolean save = sendMailService.save(sendMail);
        ThrowUtils.throwIf(!save, new BusinessException(ErrorCode.SYSTEM_ERROR, "邮件记录添加失败"));
        return ResultUtils.success(true);
    }

    @ApiOperation("获取所有邮件发送记录")
    @PostMapping("/getSendMailList")
    public BaseResponse<List<SendMail>> getSendMailList() {
        QueryWrapper<SendMail> sendMailQueryWrapper = new QueryWrapper<>();
        sendMailQueryWrapper.orderByDesc("send_time");
        List<SendMail> sendMailList = sendMailService.list(sendMailQueryWrapper);
        return ResultUtils.success(sendMailList);
    }

    @ApiOperation("发布公告")
    @PostMapping("/publishNotice")
    public BaseResponse<Boolean> publishNotice(@RequestBody Notice notice) {
        boolean save = noticeService.save(notice);
        ThrowUtils.throwIf(!save, new BusinessException(ErrorCode.SYSTEM_ERROR, "邮件记录添加失败"));
        return ResultUtils.success(true);
    }

    @ApiOperation("获取所有发布的公告列表")
    @PostMapping("/getNoticeList")
    public BaseResponse<List<Notice>> getNoticeList() {
        QueryWrapper<Notice> noticeQueryWrapper = new QueryWrapper<>();
        noticeQueryWrapper.orderByDesc("publish_date");
        List<Notice> noticeList = noticeService.list(noticeQueryWrapper);
        return ResultUtils.success(noticeList);
    }

    @ApiOperation("邮件上传附件")
    @PostMapping("/uploadMailFile")
    public BaseResponse<String> uploadMailFile(@RequestParam("file") MultipartFile multipartFile) {
        String filename = multipartFile.getOriginalFilename();
        if (multipartFile.isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean upload = FileUtils.uploadToServer(multipartFile, fileConfigProperties.getUploadPath(), filename);
        ThrowUtils.throwIf(!upload, new BusinessException(ErrorCode.SYSTEM_ERROR, "邮件附件上传失败"));
        return ResultUtils.success(filename);
    }



}
