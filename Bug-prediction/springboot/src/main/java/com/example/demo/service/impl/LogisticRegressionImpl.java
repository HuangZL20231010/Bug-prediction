package com.example.demo.service.impl;

import com.example.demo.utils.Global;
import com.example.demo.utils.MatrixOperation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;


public class LogisticRegressionImpl {

    private int batchSize;//批量数
    private int epochNum;//轮次数
    private Double lr;//学习率
    private Boolean isSystem;//判断是否是采用系统自带的模型
    public ArrayList<ArrayList<Double>>weights;//weights为了便于矩阵乘法，这里的weights实际上是一个二维列向量，其shape为（featuresnum，1）
    private Double bias;

    public LogisticRegressionImpl(Boolean isSystem,int featureNum){
        this.isSystem=isSystem;
        init_parameters(isSystem,featureNum);
    }

    //权重初始化，如果选用的是系统模型则赋予默认参数，如果是自定义训练则随机权重
    public void init_parameters(Boolean isSystem,int featureNum){
        if(isSystem&&featureNum!=61){
            System.out.println("featureNum is wrong!");
            return;
        }
        if(isSystem){
            ArrayList<ArrayList<Double>>parameters=FileProcessImpl.read_csv(Global.resourcesPath + "LogisticParams.csv",false);
            weights=MatrixOperation.iloc(parameters,0,0,parameters.size()-2,0);
            bias=MatrixOperation.iloc(parameters,parameters.size()-1,0,parameters.size()-1,0).get(0).get(0);
        }else{
            weights=new ArrayList<>();
            //weights随机生成方差为0.01，均值为0 的正态分布
            Random random=new Random();
            for(int i=0;i<featureNum;i++){
                ArrayList<Double>temp=new ArrayList<>();
                temp.add(Math.sqrt(0.01)*random.nextGaussian()+0);
                weights.add(temp);
            }
            bias=0.0;
        }
    }

    //总体训练函数(这是针对用户自定义的)
    public void train(ArrayList<ArrayList<Double>>train_features,ArrayList<ArrayList<Double>>train_labels,int epochNum,int batchSize,Double lr){
        if(isSystem==true){
            System.out.println("SystemModel can not train");
            return;
        }else{
            this.batchSize=batchSize;
            this.epochNum=epochNum;
            this.lr=lr;
            int num_examples=train_features.size();//样本总数
            for(int i=0;i<epochNum;i++){


                for(int j=0;j<num_examples;j+=batchSize){
                    int index=(int)(Math.random()*num_examples);//随机选择批量的起点
                    ArrayList<ArrayList<Double>>features;
                    ArrayList<ArrayList<Double>>labels;
                    if(index+batchSize>num_examples){
                        features=MatrixOperation.iloc(train_features,index,0,num_examples-1,train_features.get(0).size()-1);
                        labels=MatrixOperation.iloc(train_labels,index,0,num_examples-1,train_labels.get(0).size()-1);
                    }else{
                        features=MatrixOperation.iloc(train_features,index,0,index+batchSize-1,train_features.get(0).size()-1);
                        labels=MatrixOperation.iloc(train_labels,index,0,index+batchSize-1,train_labels.get(0).size()-1);
                    }

                    ArrayList<ArrayList<Double>> y_hat =forward(features);
                    Double loss=cross_entropy(y_hat,labels);
                    Double acc=evaluate(y_hat,labels);
                    //梯度下降对应如下公式
                    //dw=(features.T@(y_hat-labels))/len(features)
                    //db=np.sum(y_hat - labels) / len(features)
                    ArrayList<ArrayList<Double>> features_T=MatrixOperation.trans(features);
                    ArrayList<ArrayList<Double>> y_hat_minus_y=MatrixOperation.minus(y_hat,labels);
                    ArrayList<ArrayList<Double>> dw=MatrixOperation.multi(MatrixOperation.matrixMatmul(features_T,y_hat_minus_y),1.0/train_features.size());
                    Double db=MatrixOperation.sum(y_hat_minus_y)/num_examples;
                    //更新梯度，对应如下公式
                    //self.weight=self.weight-lr*dw
                    //self.bias=self.bias-lr*db
                    this.weights=MatrixOperation.minus(this.weights,MatrixOperation.multi(dw,this.lr));
                    this.bias=this.bias-db;

                }
                //该轮全部训练完，计算在训练集上总的误差
                ArrayList<ArrayList<Double>> y_hat =forward(train_features);
                Double loss=cross_entropy(y_hat,train_labels);
                Double acc=evaluate(y_hat,train_labels);
                if(i%50==0){
                    System.out.println("第"+i+"轮训练，损失函数值为："+loss+"，准确率为:"+acc);
                }

            }
        }
    }

    //sigmoid函数
    public ArrayList<ArrayList<Double>> sigmoid(ArrayList<ArrayList<Double>>y_hat){
        ArrayList<ArrayList<Double>>result=new ArrayList<>(y_hat);
        for(int i=0;i<y_hat.size();i++){
            //事实上这里y_hat是个二维的列向量，每一行只有一个元素
            for(int j=0;j<y_hat.get(i).size();j++){
                result.get(i).set(j,1/(1+Math.pow(Math.E, -y_hat.get(i).get(j))));
            }
        }
        return result;
    }

    //逻辑回归运算
    public ArrayList<ArrayList<Double>> forward(ArrayList<ArrayList<Double>>features){
        //普通的线性回归
        ArrayList<ArrayList<Double>>temp= MatrixOperation.add(MatrixOperation.matrixMatmul(features,this.weights),this.bias);
        //套上sigmoid头
        ArrayList<ArrayList<Double>>result=sigmoid(temp);
        return result;
    }

    //评估准确率,y_hat为预测值，y为真实值
    public Double evaluate(ArrayList<ArrayList<Double>>y_hat,ArrayList<ArrayList<Double>>y){
        if(y_hat.size()==0||y_hat.size()!=y.size()){
            return null;
        }
        Double sum=0.0;
        for(int i=0;i<y_hat.size();i++){
            //取阈值为0.5，如果预测结果大于0.5则为正标签1，否则为负标签0
            if((y_hat.get(i).get(0)>=0.5&&y.get(i).get(0)==1.0)||(y_hat.get(i).get(0)<0.5&&y.get(i).get(0)==0.0)){
                sum++;
            }
        }

        return sum/y_hat.size();
    }

    //交叉熵损失函数，这里采用的是简单01二分类的交叉熵损失函数，且返回的是这一个批量的均值
    public Double cross_entropy(ArrayList<ArrayList<Double>>y_hat,ArrayList<ArrayList<Double>>y){
        Double sum=0.0;
        for(int i=0;i<y_hat.size();i++){
            double ty=y.get(i).get(0);
            double ty_hat=y_hat.get(i).get(0);
            sum+=-1*(ty*Math.log(ty_hat)+(1-ty)*Math.log(1-ty_hat));
        }
        return sum/y_hat.size();
    }

    //预测，这里主要针对系统模型
    public ArrayList<ArrayList<Double>> predict(ArrayList<ArrayList<Double>>features){
        //计算概率
        ArrayList<ArrayList<Double>> result=forward(features);
        //将预测值（概率）转换为标签
        for(int i=0;i<result.size();i++){
            if(result.get(i).get(0)>=0.5){
                result.get(i).set(0,1.0);
            }else{
                result.get(i).set(0,0.0);
            }
        }
        return result;
    }

    public Double getBias() {
        return bias;
    }
}
