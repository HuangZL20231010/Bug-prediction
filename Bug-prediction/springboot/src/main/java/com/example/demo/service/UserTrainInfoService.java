package com.example.demo.service;

import org.springframework.web.multipart.MultipartFile;

public interface UserTrainInfoService {
    /* 传入csv文件,将该文件存储到本地,返回是否成功 */
    boolean storeFile(MultipartFile file);

    /* 系统预测部分,传入csv文件的绝对路径,训练后的csv文件绝对路径 */
    String systemPrediction(String filePath, String fileName);
}
