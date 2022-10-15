package com.example.demo.service;

import java.util.ArrayList;

public interface KNN {
    // 计算两个向量之间的距离
    double calDistance(ArrayList<Double> data1, ArrayList<Double> data2);
    // 返回类别,1代表没缺陷，0代表有缺陷，其他代表bug的种类
    double precise(); //
    int knn(ArrayList<Double> newdata, int k);
    void init(String file); //默认构造，k=5，weight =
    ArrayList<ArrayList<Double>> dataProcess(ArrayList<ArrayList<Double>> data);
    // 传入用户上传的csv文件的绝对路径，返回训练后的二维数组
    public ArrayList<ArrayList<Double>> systemPrediction(String filePath);

    // 用户自定义预测

}
