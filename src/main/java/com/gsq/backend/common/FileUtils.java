package com.gsq.backend.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/19 11:17
 * @description 文件上传下载工具类
 */
@Slf4j
public class FileUtils {

    /**
     * 文件上传
     *
     * @param multiFile      文件
     * @param uploadPath     服务器上要存储文件的路径
     * @param uploadFileName 服务器上要存储的文件的名称
     * @return
     */
    public static boolean uploadToServer(MultipartFile multiFile, String uploadPath, String uploadFileName) {
        //构建文件对象
        File file = new File(uploadPath);
        //文件目录不存在则递归创建目录
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
            if (!mkdirs) {
                log.error("创建文件夹异常");
                return false;
            }
        }
        try {
            //获取文件输入流
            InputStream inputStream = multiFile.getInputStream();
            //构建文件输出流
            FileOutputStream outputStream = new FileOutputStream(uploadPath + uploadFileName);
            int copy = FileCopyUtils.copy(inputStream, outputStream);
            log.info("上传成功,文件大小：{}", copy);
            return true;
        } catch (IOException e) {
            log.error("文件上传异常", e);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 下载文件到服务器
     *
     * @param downloadUrl      要下载的文件的地址
     * @param downloadPath     服务器上存储的文件路径
     * @param downloadFileName 服务器上存储的文件名称
     * @return
     */
    public static boolean downloadToServer(String downloadUrl, String downloadPath, String downloadFileName) {
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        boolean flag = false;
        try {
            URL url = new URL(downloadUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            bis = new BufferedInputStream(connection.getInputStream());
            File file = new File(downloadPath);
            if (!file.exists()) {
                boolean mkdirs = file.mkdirs();
                if (!mkdirs) {
                    log.error("创建文件目录失败");
                    return false;
                }
            }
            String filePathName = downloadPath + File.separator + downloadFileName;
            byte[] buf = new byte[1024];
            int size;
            fos = new FileOutputStream(filePathName);
            while ((size = bis.read(buf)) != -1) {
                fos.write(buf, 0, size);
            }
            flag = true;
            log.info("文件下载成功,文件路径[" + filePathName + "]");
            flag = true;
        } catch (Exception e) {
            log.error("下载文件异常", e);
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                log.error("关流异常", e);
                e.printStackTrace();
            }
        }
        return flag;
    }
}
