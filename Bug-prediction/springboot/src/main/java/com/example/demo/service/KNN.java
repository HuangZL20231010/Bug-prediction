package com.example.demo.service;

import java.util.ArrayList;

public interface KNN {
    // 计算两个向量之间的距离
    double calDistance(ArrayList<Double> data1, ArrayList<Double> data2);
    // 返回类别,1代表没缺陷，0代表有缺陷，其他代表bug的种类
    double precise(); // 无权重预测
    int knn(ArrayList<Double> newdata, int k);
    void init(String file); //默认构造，k=5，weight = 1
    void init(String file,int ku,int weightu);
}
