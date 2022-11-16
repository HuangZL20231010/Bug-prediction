package com.example.demo.service.impl;

import com.example.demo.utils.Global;
import com.example.demo.utils.MatrixOperation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class LogisticRegressionImplTest {

    private LogisticRegressionImpl system_lrm;
    private LogisticRegressionImpl user_lrm;
    private ArrayList<ArrayList<Double>>testFeature;
    private ArrayList<ArrayList<Double>>testlabel;
    @Before
    public void setUp() throws Exception {
        system_lrm=new LogisticRegressionImpl(true,61);
        user_lrm=new LogisticRegressionImpl(false,61);
        LogisticRegressionImpl logisticRegression=new LogisticRegressionImpl(true,61);
        ArrayList<ArrayList<Double>> origin_data=FileProcessImpl.read_csv("src/main/resources/static/EvaluateData_nolabel.csv",true);
        testFeature=MatrixOperation.converse(MatrixOperation.iloc(origin_data,0,0,origin_data.size()-1,origin_data.get(0).size()-1),true);
        ArrayList<ArrayList<Double>> evaluate_data=FileProcessImpl.read_csv("src/main/resources/static/EvaluateData.csv",true);
        testlabel=MatrixOperation.iloc(evaluate_data,0,evaluate_data.get(0).size()-1,evaluate_data.size()-1,evaluate_data.get(0).size()-1);
    }

    @After
    public void tearDown() throws Exception {
    }



    @Test
    public void train() {
        //测试用例1：
        user_lrm.train(testFeature,testlabel,20,5,0.001);
        System.out.println("train Test1 passed");

        //测试用例2：
        user_lrm.train(testFeature,testlabel,20,-5,0.001);
        System.out.println("train Test2 passed");

        //测试用例3：
        user_lrm.train(testFeature,null,20,5,0.001);
        System.out.println("train Test3 passed");
        //测试用例4：
        user_lrm.train(null,testlabel,20,5,0.001);
        System.out.println("train Test4 passed");
    }

    @Test
    public void sigmoid() {


        //测试用例2：
        ArrayList<ArrayList<Double>>test2=new ArrayList<ArrayList<Double>>();
        test2.add(new ArrayList<Double>());
        test2.add(new ArrayList<Double>());
        test2.get(0).add(1.0);
        test2.get(0).add(2.0);
        test2.get(0).add(3.0);
        test2.get(1).add(4.0);
        test2.get(1).add(5.0);
        test2.get(1).add(6.0);
        ArrayList<ArrayList<Double>>expected2=new ArrayList<ArrayList<Double>>();
        expected2.add(new ArrayList<Double>());
        expected2.add(new ArrayList<Double>());
        expected2.get(0).add(0.7310585786300049);
        expected2.get(0).add(0.8807970779778823);
        expected2.get(0).add(0.9525741268224331);
        expected2.get(1).add(0.9820137900379085);
        expected2.get(1).add(0.9933071490757153);
        expected2.get(1).add(0.9975273768433653);
        ArrayList<ArrayList<Double>>result2=system_lrm.sigmoid(test2);
        Assert.assertEquals(expected2,result2);
        System.out.println("sigmoid Test1 passed");

        //测试用例1：
        ArrayList<ArrayList<Double>>test1=null;
        ArrayList<ArrayList<Double>>expected1=null;
        ArrayList<ArrayList<Double>>result1=system_lrm.sigmoid(test1);
        if(result1==null){
            System.out.println("sigmoid Test1 passed");
        }
    }

    @Test
    public void forward() {
        //测试用例2：
        ArrayList<ArrayList<Double>>test2=new ArrayList<ArrayList<Double>>();
        test2.add(new ArrayList<Double>());
        test2.add(new ArrayList<Double>());
        test2.get(0).add(1.0);
        test2.get(0).add(2.0);
        test2.get(0).add(3.0);
        test2.get(1).add(4.0);
        test2.get(1).add(5.0);
        test2.get(1).add(6.0);
        ArrayList<ArrayList<Double>>expected2=new ArrayList<ArrayList<Double>>();
        expected2= MatrixOperation.add(MatrixOperation.matrixMatmul(test2,user_lrm.weights),user_lrm.getBias());
        expected2=user_lrm.sigmoid(expected2);
        ArrayList<ArrayList<Double>>result2=user_lrm.forward(test2);
        Assert.assertEquals(expected2,result2);
        System.out.println("sigmoid Test1 passed");

        //测试用例1：
        ArrayList<ArrayList<Double>>test1=null;
        ArrayList<ArrayList<Double>>expected1=null;
        ArrayList<ArrayList<Double>>result1=user_lrm.forward(test1);
        if(result1==null){
            System.out.println("sigmoid Test1 passed");
        }
    }

    @Test
    public void evaluate() {
        //测试用例1
        Double result1=system_lrm.evaluate(testlabel,testlabel);
        Assert.assertEquals(1d,result1,0.00000001);
        System.out.println("evaluate Test1 passed");

        //测试用例3
        ArrayList<ArrayList<Double>> test=new ArrayList<ArrayList<Double>>();
        Double result3=system_lrm.evaluate(test,testlabel);
        if(result3==null){
            System.out.println("evaluate Test3 passed");
        }

        //测试用例2
        Double result2=system_lrm.evaluate(null,testlabel);
        if(result2==null){
            System.out.println("evaluate Test2 passed");
        }


    }

    @Test
    public void cross_entropy() {
        //测试用例1
        ArrayList<ArrayList<Double>> test1=new ArrayList<>();
        test1.add(new ArrayList<>());
        test1.get(0).add(0.96);
        test1.add(new ArrayList<>());
        test1.get(1).add(0.78);
        test1.add(new ArrayList<>());
        test1.get(2).add(0.64);
        test1.add(new ArrayList<>());
        test1.get(3).add(0.101);
        ArrayList<ArrayList<Double>> test2=new ArrayList<>();
        test2.add(new ArrayList<>());
        test2.get(0).add(1.0);
        test2.add(new ArrayList<>());
        test2.get(1).add(0.0);
        test2.add(new ArrayList<>());
        test2.get(2).add(1.0);
        test2.add(new ArrayList<>());
        test2.get(3).add(1.0);
        Double result1=system_lrm.cross_entropy(test1,test2);
        Assert.assertEquals(1.073467897979832,result1,0.00000001);
        System.out.println("cross_entropy Test1 passed");


        //测试用例3
        ArrayList<ArrayList<Double>> test=new ArrayList<ArrayList<Double>>();
        Double result3=system_lrm.cross_entropy(test,testlabel);
        if(result3==null){
            System.out.println("cross_entropy Test3 passed");
        }
        //测试用例2
        Double result2=system_lrm.cross_entropy(null,testlabel);
        if(result2==null){
            System.out.println("cross_entropy Test2 passed");
        }


    }

    @Test
    public void predict() {
        //测试用例1：
        ArrayList<ArrayList<Double>>predict_result=system_lrm.predict(testFeature);
        if(predict_result.size()==testFeature.size()){
            System.out.println("predict Test1 passed");
        }

        //测试用例2：
        ArrayList<ArrayList<Double>>test1=new ArrayList<>();
        ArrayList<ArrayList<Double>>predict_result2=system_lrm.predict(test1);
        if(predict_result2.size()==testFeature.size()){
            System.out.println("predict Test2 passed");
        }

        //测试用例3：
        ArrayList<ArrayList<Double>>test2=null;
        ArrayList<ArrayList<Double>>predict_result3=system_lrm.predict(test2);
        if(predict_result3.size()==testFeature.size()){
            System.out.println("predict Test2 passed");
        }
    }


}