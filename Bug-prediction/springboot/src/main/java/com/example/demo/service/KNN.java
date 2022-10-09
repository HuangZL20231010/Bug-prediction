package com.example.demo.service;

import java.util.ArrayList;

public interface KNN {
    // 计算两个向量之间的距离
    double calDistance(ArrayList<Double> data1, ArrayList<Double> data2);
    // 返回类别,1代表没缺陷，0代表有缺陷，其他代表bug的种类
    int knn(ArrayList<Double> newdata, int k,int weight);
    double precise_weight(int w); // 带权重预测
    double precise(); // 无权重预测
    int knn(ArrayList<Double> newdata, int k);
}
