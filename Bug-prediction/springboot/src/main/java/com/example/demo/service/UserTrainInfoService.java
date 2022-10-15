package com.example.demo.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public interface UserTrainInfoService {
    /* 传入csv文件,将该文件存储到本地,返回是否成功 */
    boolean storeFile(MultipartFile file);

    // 传入目的文件绝对路径和二维数组,将该二维数组写入文件中
    String writeTwoArrayFile(String destinationPath, ArrayList<ArrayList<Double>> result);

    /* 系统预测部分,采用Logistic回归模型,传入csv文件的绝对路径和文件名,返回训练后的csv文件绝对路径 */
    String systemPredictionLogistic(String filePath, String fileName);

    /* 系统预测部分,采用KNN模型,传入csv文件的绝对路径和文件名,返回训练后的csv文件的绝对路径 */
    String systemPredictionKNN(String filePath, String fileName);

    // 用户自定义训练部分,采用Logistic模型,传入csv文件的绝对路径和文件名,再传入三个超参和用户名,返回训练后的csv文件的绝对路径
    String userDefinedPredictionLogistic(String filePath, String fileName, Integer epochNum, Integer batchSize,
                                         Double learningrate, String userName);

    // 用户自定义训练部分,采用KNN模型,传入csv文件的绝对路径和文件名,再传入用户名,返回训练后的csv文件的绝对路径
    String userDefinedPredictionKNN(String filePath, String fileName, String username);
}
