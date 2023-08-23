package com.gsq.backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/19 11:15
 * @description 文件上传与下载属性配置
 */
@Data
@Component
@ConfigurationProperties(value = "file")
public class FileConfigProperties {

    /**
     * 上传路径(临时文件夹)
     */
    private String uploadPath = "";

    /**
     * 上传路径(临时文件夹)
     */
    private String pdfPath = "";

    /**
     * 下载路径
     */
    private String downloadPath = "";

    /**
     * 文件类型
     */
    private String[] fileTypeArray;

    /**
     * 文件大小
     */
    private int maxFileSize;

    /**
     * baseUrl 说明书上传前缀
     */
    private String baseUrl;

    /**
     * mailFilePath
     */
    private String mailFilePath;
}
