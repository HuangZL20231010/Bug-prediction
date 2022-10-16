package com.example.demo.service.impl;

import com.example.demo.service.KNN;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Service
public class KNNSer implements KNN {

    ArrayList<Pair<Double, Double>> dataset; // 新点到各点的距离,和各点的类别
    ArrayList<ArrayList<Double>> data_train; // 数据集;
    ArrayList<ArrayList<Double>> data_test;  // 测试集
    int length;    // 维度
    int size;      //数据集大小
    int k;         //knn
    double weight;    //负例的权重
    Comparator<Pair<Double, Double>> gc =new Comparator<Pair<Double, Double>>() {
        @Override
        public int compare(Pair<Double, Double> s1, Pair<Double, Double> s2) {
            if(s1.getFirst()<s2.getFirst()) return -1;
            return 1;
        }
    };
    @Override
    public void init(String file){
        data_train = new ArrayList<>();
        data_train = FileProcessImpl.read_csv(file,true);
        int clean = 0;
        int buggy = 0;
        length = data_train.get(0).size();
        data_test = new ArrayList<>();
        dataset = new ArrayList<>();
        for(int i=0;i<data_train.size();i++){
//            if(i%5==0) data_test.add(data.get(i));
//            if(i%5!=0) data_train.add(data.get(i));
            if(data_train.get(i).get(length-1)==1.0) buggy++;
            if(data_train.get(i).get(length-1)==0.0) clean++;
        }
        data_train = dataProcess(data_train);
        System.out.println("数据归一化成功");
        k=45;
        weight = 2.0*(double)clean/buggy;
//        weight = 100.0;
        System.out.println("weight:"+weight);
        length = data_train.get(0).size();
        size = data_train.size();
        System.out.println("初始化已完成 训练集："+size+"*"+length);
        System.out.println("KNN初始化完成");
    }



    @Override
    public ArrayList<ArrayList<Double>> dataProcess(ArrayList<ArrayList<Double>> data) { // 归一化
        int len = data.get(0).size(); // 数据维度
        int size = data.size();       // 数据量
        ArrayList<Double> maxmin = new ArrayList<>();
        for(int i=0;i<len;i++){
            double min = data.get(0).get(i);
            double max = min;
            for(int j=0;j<size;j++){
                double tem = data.get(j).get(i);
                if(tem>max) max = tem;
                if(tem<min) min = tem;
            }
            maxmin.add(min);
            maxmin.add(max);
        }
        // 取得了每个维度的数据的最大值和最小值
        // 第j个维度的最小值是2j-2 最大值是2j-1
        for(int i=0;i<size;i++){  // 第i个数据
            ArrayList<Double> temdata = data.get(i);
            for(int j=0;j<len;j++){ //第j个维度
                double temm = maxmin.get(2*j+1) - maxmin.get(2*j); //第j个维度的最大值和最小值之差
                if(temm ==0){  //最大值等于最小值
                    temdata.set(j,0.0);
                }
                else {
                    temdata.set(j,(temdata.get(j)-maxmin.get(2*j))/temm);
                }
            }
            data.set(i,temdata);
        }
        return data;
    }

    @Override
    public double calDistance(ArrayList<Double> data1, ArrayList<Double> data2) {
        double result = 0;
        if(data2.size() == data1.size()){
            for (int i=0;i<data1.size()-1;i++){ // 测试
                result+=Math.pow((data1.get(i) - data2.get(i)), 2);
            }
        }
        else {
            for (int i=0;i<data2.size() && i<data1.size();i++){ //
                result+=Math.pow((data1.get(i) - data2.get(i)), 2);
            }
        }
        return Math.sqrt(result);
    }

    @Override
    public int knn(ArrayList<Double> newdata, int k) { //newdata为带标签的测试集
//        System.out.println("------------");
        if(k>=data_test.size()) return 0;
        double clean=0;
        double buggy=0;
        for(int i=0;i<size;i++){
            double res = calDistance(data_train.get(i),newdata);
            Pair<Double, Double> pair = Pair.of(res,data_train.get(i).get(length-1));
//            System.out.println("第"+i+"个训练集的种类:"+data_train.get(i).get(length-1));
            dataset.add(pair);
        } // 求出新的点和所有点的距离
        Collections.sort(dataset, gc); // 按距离从小到大排序
        for(int i=0;i<k && k<data_test.size();i++){
            double tem = dataset.get(i).getSecond();
//            System.out.println("第"+i+"个dataset的种类:"+tem);
            if(tem==0.0) clean++;
            if(tem==1.0) {
//                System.out.println("第"+i+"个点"+"事buggy数据");
                buggy+= weight;
            }
        }
//        System.out.print("clean:"+clean+"   buggy:"+buggy);
        if(clean>buggy) return 1;
        else if(clean<buggy) return 0;
        return knn(newdata,k+1);
    }
    @Override
    public double precise() {
        int len = data_test.size(); //测试集数量
        double clean_true = 0; //正例
        double tp = 0;       // 预测正确的正例
        double buggy_true = 0;
        double fp = 0;
        for(int i=0;i<len;i++){
            int j =knn(data_test.get(i),k);
            if(data_test.get(i).get(length-1) == 0.0){ // 真实数据为clean
                clean_true++;
                if(j==1) tp++; //系统划分为clean
            }
            if(data_test.get(i).get(length-1) == 1.0){ // 真实数据为buggy
                buggy_true++;
                if(j==0) fp++; //系统划分为buggy
            }
        }
        System.out.println("finally:   TP:"+tp+" ; 正例:"+clean_true);
        System.out.println("finally:   FP:"+fp+" ; 反例:"+buggy_true);
        return tp/clean_true;
    }

    public void print(ArrayList<ArrayList<Double>> data){
        for(int i=0;i<data.size();i++){
            System.out.println(data.get(i).toString());
        }
    }

    // 传入用户上传的csv文件的绝对路径，返回训练后的二维数组
    public ArrayList<ArrayList<Double>> systemPrediction(String filePath) {
        ArrayList<ArrayList<Double>> data = FileProcessImpl.read_csv(filePath,true);
        if(data.get(0).size()!=length-1) return null; // 用户输入数据集维度不等于默认数据集的维度-1
        ArrayList<ArrayList<Double>> result = new ArrayList<>();
        for(int i=0;i<data.size();i++){
            ArrayList<Double> tem = new ArrayList<>();
            tem.add((double)knn(data.get(i),k));
            result.add(tem);
        }

        return result;
    }


//    public static void main(String[] args){
//        KNNSer knnSer = new KNNSer();
//        knnSer.init("C:\\Users\\FUBOFENG\\Desktop\\实训1\\Bug-prediction\\springboot\\src\\main\\resources\\static\\JDT.csv");
//        System.out.println("准确率："+knnSer.precise());
////        knnSer.knn(knnSer.data_test.get(187),101);
////        knnSer.print(knnSer.data_test);
////        knnSer.print(knnSer.dataProcess(knnSer.data_test));
//    }
}
