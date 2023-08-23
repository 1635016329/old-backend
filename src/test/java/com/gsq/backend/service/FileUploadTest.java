package com.gsq.backend.service;

import com.gsq.backend.common.FileUtils;
import com.gsq.backend.config.FileConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/19 11:34
 * @description 文件上传和下载测试
 */
@Slf4j
@SpringBootTest
class FileUploadTest {

    @Resource
    private FileConfigProperties fileConfigProperties;

//    @Test
//    void fileUploadTest() {
//        String downloadUrl = "https://ts1.cn.mm.bing.net/th/id/R-C.66d7b796377883a92aad65b283ef1f84?rik=sQ%2fKoYAcr%2bOwsw&riu=http%3a%2f%2fwww.quazero.com%2fuploads%2fallimg%2f140305%2f1-140305131415.jpg&ehk=Hxl%2fQ9pbEiuuybrGWTEPJOhvrFK9C3vyCcWicooXfNE%3d&risl=&pid=ImgRaw&r=0";
//        String filePath = fileConfigProperties.getDownloadPath();
//        String fileName = "testUpload.png";
//        boolean download = FileUtils.downloadToServer(downloadUrl, filePath, fileName);
//        Assertions.assertTrue(download);
//    }

}
