package com.gsq.backend.controller;

import com.gsq.backend.common.BaseResponse;
import com.gsq.backend.common.FileUtils;
import com.gsq.backend.common.ResultUtils;
import com.gsq.backend.config.FileConfigProperties;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/19 10:59
 * @description 文件管理控制器
 */
@RestController
@RequestMapping("/file")
@Api(tags = {"文件控制器"})
@Slf4j
public class FileController {

    @Autowired
    private FileConfigProperties fileConfigProperties;

    @PostMapping(value = "/uploadToServer")
    public BaseResponse<Boolean> uploadToServer(@RequestParam("file") MultipartFile multiFile) {
        boolean upload = FileUtils.uploadToServer(multiFile, fileConfigProperties.getUploadPath(), multiFile.getOriginalFilename());
        return ResultUtils.success(upload);
    }

    @GetMapping("/downloadToClient")
    public void getImage(HttpServletResponse response) throws IOException {
        String filePath = "D://myFile/upload/1.pdf";
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            response.setContentType("image/jpeg/png/pdf");
            response.setHeader("Content-Disposition", "inline; filename=1.pdf");
            Resource resource = new UrlResource(path.toUri());
            byte[] data = Files.readAllBytes(path);
            response.getOutputStream().write(data);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
