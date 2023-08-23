package com.gsq.backend.job;

import com.gsq.backend.config.FileConfigProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/20 9:26
 * @description 每24小时清理一次过期文件
 */
@Component
public class TempFileCleaner {

    @Resource
    private FileConfigProperties fileConfigProperties;

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // 每24小时执行一次任务24 * 60 * 60 * 1000
    public void cleanTempFile() {
        // 执行你的定时任务逻辑
        File tempDir = new File(fileConfigProperties.getUploadPath());
        File[] files = tempDir.listFiles();
        long now = new Date().getTime();

        for (File file : files) {
            long lastModified = file.lastModified();
            if (now - lastModified > 24 * 60 * 60 * 1000) {
                file.delete(); // 删除过期的临时文件
            }
        }
    }
}