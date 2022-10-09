package com.example.demo.service.impl;

import com.example.demo.service.KNN;
import org.springframework.data.util.Pair;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class KNNSer implements KNN {

    ArrayList<Pair<Double, Double>> dataset; // 新点到各点的距离,和各点的类别
    ArrayList<ArrayList<Double>> data_train; // 数据集;
    ArrayList<ArrayList<Double>> data_test;
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
        ArrayList<ArrayList<Double>> data = FileProcessImpl.read_csv(file,true);
        int clean = 0;
        int buggy = 0;
        length = data.get(0).size();
        data_test = new ArrayList<>();
        data_train = new ArrayList<>();
        dataset = new ArrayList<>();
        for(int i=0;i<data.size();i++){
            if(i%5==0) data_test.add(data.get(i));
            if(i%5!=0) data_train.add(data.get(i));
            if(data.get(i).get(length-1)==1.0) buggy++;
            if(data.get(i).get(length-1)==0.0) clean++;
        }
        k=6;
        weight = (double)clean/buggy;
        System.out.println("weight:"+weight);
        length = data_train.get(0).size();
        size = data_train.size();
        System.out.println("初始化已完成 训练集："+size+"*"+length+"  测试集："+data_test.size()+"*"+length);
        System.out.println("KNN初始化完成");
    }

    @Override
    public void init(String file, int ku, double weightu) {
        ArrayList<ArrayList<Double>> data = FileProcessImpl.read_csv(file,true);
        data_test = new ArrayList<>();
        data_train = new ArrayList<>();
        dataset = new ArrayList<>();
        for(int i=0;i<data.size();i++){
            if(i%5==0) data_test.add(data.get(i));
            if(i%5!=0) data_train.add(data.get(i));
        }
        k=ku;
        weight = weightu;
        length = data_train.get(0).size();
        size = data_train.size();
        System.out.println("初始化已完成 训练集："+size+"*"+length+"  测试集："+data_test.size()+"*"+length);
        System.out.println("KNN初始化完成");
    }

    @Override
    public double calDistance(ArrayList<Double> data1, ArrayList<Double> data2) {
        double result = 0;
        for (int i=0;i<data1.size()-1;i++){ // 除最后一列的标签外的权重
            result+=Math.pow((data1.get(i) - data2.get(i)), 2);
        }
        return Math.sqrt(result);
    }

    @Override
    public int knn(ArrayList<Double> newdata, int k) {
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
            if(tem==1.0) buggy+= weight;
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

    public static void main(String[] args){
        KNNSer knnSer = new KNNSer();
        knnSer.init("C:\\Users\\FUBOFENG\\Desktop\\实训1\\Bug-prediction\\springboot\\src\\main\\resources\\static\\JDT.csv");
        System.out.println("准确率："+knnSer.precise());
//        knnSer.knn(knnSer.data_test.get(187),101);
    }
}
