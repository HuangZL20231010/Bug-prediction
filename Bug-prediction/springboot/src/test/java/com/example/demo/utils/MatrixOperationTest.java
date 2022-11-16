package com.example.demo.utils;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MatrixOperationTest {

    Double[][] convertToDouble(ArrayList<ArrayList<Double>> a){
        int m=a.size();
        if(m==0){
            return null;
        }
        int n=a.get(0).size();
        Double[][]result=new Double[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                result[i][j]=a.get(i).get(j);
            }
        }
        return result;
    }

    ArrayList<ArrayList<Double>> convertToList(Double [][] a){
        ArrayList<ArrayList<Double>>result=new ArrayList<ArrayList<Double>>();
        for(int i=0;i<a.length;i++){
            result.add(new ArrayList<>());
        }
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a[i].length;j++){
                result.get(i).add(a[i][j]);
            }
        }
        return result;
    }
    @BeforeEach
    void setUp() {
        System.out.println("MatrixOperation Test start!");
    }

    @AfterEach
    void tearDown() {
        System.out.println("MatrixOperation Test over!");
    }

    @Test
    void matrixMatmul() {
        //测试用例1：
        Double[][] testa1=new Double[][]{{1.0,2.0,3.0}};
        Double[][] testa2=new Double[][]{{1.0},{2.0},{3.0}};
        Double[][] result1 =MatrixOperation.matrixMatmul(testa1,testa2);
        Double[][] expected1=new Double[][]{{14.0}};
        Assert.assertArrayEquals(expected1,result1);
        System.out.println("matrixMatmul Test1 passed");
        //测试用例2：
        Double[][] testb1=new Double[][]{{}};
        Double[][] testb2=new Double[][]{{}};
        Double[][] result2 =MatrixOperation.matrixMatmul(testb1,testb2);
        Double[][] expected2=null;
        Assert.assertArrayEquals(expected2,result2);
        System.out.println("matrixMatmul Test2 passed");

        //测试用例4：
        Double[][] testd1=new Double[][]{{1.0,2.0,3.0}};
        Double[][] testd2=new Double[][]{{1.0},{2.0}};;
        Double[][] result4 =MatrixOperation.matrixMatmul(testd1,testd2);
        Double[][] expected4=null;
        Assert.assertArrayEquals(expected4,result4);
        System.out.println("matrixMatmul Test4 passed");

        //测试用例3：
        Double[][] testc1=null;
        Double[][] testc2=null;
        Double[][] result3 =MatrixOperation.matrixMatmul(testc1,testc2);
        Double[][] expected3=null;
        Assert.assertArrayEquals(expected3,result3);
        System.out.println("Test3 passed");


    }

    @Test
    void testMatrixMatmul() {
        //测试用例1：
        Double[][] testa1=new Double[][]{{1.0,2.0,3.0}};
        Double[][] testa2=new Double[][]{{1.0},{2.0},{3.0}};
        ArrayList<ArrayList<Double>>testla1=convertToList(testa1);
        ArrayList<ArrayList<Double>>testla2=convertToList(testa2);
        ArrayList<ArrayList<Double>> resultl1 =MatrixOperation.matrixMatmul(testla1,testla2);
        Double[][] expected1=new Double[][]{{14.0}};
        ArrayList<ArrayList<Double>> expectedl1 =convertToList(expected1);
        Assert.assertEquals(expectedl1,resultl1);
        System.out.println("testmatrixMatmul Test1 passed");
        //测试用例2：
        Double[][] testb1=new Double[][]{{}};
        Double[][] testb2=new Double[][]{{}};
        ArrayList<ArrayList<Double>>testlb1=convertToList(testb1);
        ArrayList<ArrayList<Double>>testlb2=convertToList(testb2);
        ArrayList<ArrayList<Double>> resultl2 =MatrixOperation.matrixMatmul(testlb1,testlb2);
        Double[][] expected2=new Double[][]{{14.0}};
        ArrayList<ArrayList<Double>> expectedl2 =convertToList(expected2);
        Assert.assertEquals(expectedl2,resultl2);
        System.out.println("testmatrixMatmul Test2 passed");

    }

    @Test
    void iloc() {
        //测试用例1
        Double[][] test1=new Double[][]{{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
        Double[][] result1=MatrixOperation.iloc(test1,0,0,2,2);
        Double[][] expected1=new Double[][]{{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
        Assert.assertArrayEquals(expected1,result1);
        System.out.println("iloc Test1 passed");

        //测试用例2
        Double[][] test2=new Double[][]{{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
        Double[][] result2=MatrixOperation.iloc(test2,0,0,3,3);
        Double[][] expected2=null;
        Assert.assertArrayEquals(expected2,result2);
        System.out.println("iloc Test2 passed");

        //测试用例3
        Double[][] test3=new Double[][]{{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
        Double[][] result3=MatrixOperation.iloc(test3,-1,-1,2,2);
        Double[][] expected3=null;
        Assert.assertArrayEquals(expected3,result3);
        System.out.println("iloc Test3 passed");

        //测试用例4
        Double[][] test4=new Double[][]{{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
        Double[][] result4=MatrixOperation.iloc(test4,2,2,0,0);
        Double[][] expected4=null;
        Assert.assertArrayEquals(expected4,result4);
        System.out.println("iloc Test4 passed");
    }

    @Test
    void testIloc() {
    }

    @Test
    void sum() {
        //测试用例1
        Double[][] test1=new Double[][]{{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
        Double expected1=45d;
        Double result1=MatrixOperation.sum(test1);
        Assert.assertEquals(expected1,result1,0.0000001);
        System.out.println("sum Test1 passed");

        //测试用例2
        Double[][] test2=new Double[][]{{}};
        Double expected2=0d;
        Double result2=MatrixOperation.sum(test2);
        Assert.assertEquals(expected2,result2,0.0000001);
        System.out.println("sum Test2 passed");

        //测试用例3
        Double[][] test3=null;
        Double expected3=0d;
        Double result3=MatrixOperation.sum(test3);
        Assert.assertEquals(expected3,result3,0.0000001);
        System.out.println("sum Test3 passed");
    }

    @Test
    void testSum() {
    }

    @Test
    void mean() {
        //测试用例1
        Double[][] test1=new Double[][]{{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
        Double expected1=5d;
        Double result1=MatrixOperation.mean(test1);
        Assert.assertEquals(expected1,result1,0.0000001);
        System.out.println("mean Test1 passed");

        //测试用例2
        Double[][] test2=new Double[][]{{}};
        Double expected2=0d;
        Double result2=MatrixOperation.mean(test2);
        Assert.assertEquals(expected2,result2,0.0000001);
        System.out.println("mean Test2 passed");

        //测试用例3
        Double[][] test3=null;
        Double expected3=0d;
        Double result3=MatrixOperation.mean(test3);
        Assert.assertEquals(expected3,result3,0.0000001);
        System.out.println("mean Test3 passed");
    }

    @Test
    void testMean() {

    }

    @Test
    void trans() {
        //测试用例1：
        Double[][] test1=new Double[][]{{1.0,2.0,3.0}};
        Double[][] result1=MatrixOperation.trans(test1);
        Double[][] expected1=new Double[][]{{1.0},{2.0},{3.0}};
        Assert.assertArrayEquals(expected1,result1);
        System.out.println("trans Test1 passed");


        //测试用例3：
        Double[][] test3=null;
        Double[][] result3=MatrixOperation.trans(test3);
        Double[][] expected3=null;
        Assert.assertArrayEquals(expected3,result3);
        System.out.println("trans Test3 passed");

        //测试用例2：
        Double[][] test2=new Double[][]{{}};
        Double[][] result2=MatrixOperation.trans(test2);
        Double[][] expected2=null;
        Assert.assertArrayEquals(expected2,result2);
        System.out.println("trans Test2 passed");
    }

    @Test
    void testTrans() {
    }

    @Test
    void sum_column() {
        //测试用例1
        Double[][] test1=new Double[][]{{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
        Double expected1=12d;
        Double result1=MatrixOperation.sum_column(test1,0);
        Assert.assertEquals(expected1,result1,0.0000001);
        System.out.println("sum_column Test1 passed");

        //测试用例2
        Double[][] test2=new Double[][]{{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
        Double expected2=null;
        Double result2=MatrixOperation.sum_column(test2,4);
        if(result2==null){
            System.out.println("sum_column Test2 passed");
        }

        //测试用例3
        Double[][] test3=new Double[][]{{}};
        Double expected3=null;
        Double result3=MatrixOperation.sum_column(test3,4);
        if(result3==null){
            System.out.println("sum_column Test3 passed");
        }
    }

    @Test
    void testSum_column() {
    }

    @Test
    void mean_column() {
        //测试用例1
        Double[][] test1=new Double[][]{{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
        Double expected1=4d;
        Double result1=MatrixOperation.mean_column(test1,0);
        Assert.assertEquals(expected1,result1,0.0000001);
        System.out.println("mean_column Test1 passed");

        //测试用例2
        Double[][] test2=new Double[][]{{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
        Double expected2=null;
        Double result2=MatrixOperation.mean_column(test2,4);
        if(result2==null){
            System.out.println("mean_column Test2 passed");
        }

        //测试用例3
        Double[][] test3=new Double[][]{{}};
        Double expected3=null;
        Double result3=MatrixOperation.sum_column(test3,4);
        if(result3==null){
            System.out.println("mean_column Test3 passed");
        }
    }

    @Test
    void testMean_column() {
    }

    @Test
    void add() {
        //测试用例1：
        Double[][] testa1=new Double[][]{{1.0,2.0,3.0}};
        Double[][] testa2=new Double[][]{{1.0,2.0,3.0}};
        Double[][] result1 =MatrixOperation.add(testa1,testa2);
        Double[][] expected1=new Double[][]{{2.0,4.0,6.0}};
        Assert.assertArrayEquals(expected1,result1);
        System.out.println("add Test1 passed");

        //测试用例2：
        Double[][] testb1=new Double[][]{{}};
        Double[][] testb2=new Double[][]{{}};
        Double[][] result2 =MatrixOperation.add(testb1,testb2);
        Double[][] expected2=null;
        if(result2==null){
            System.out.println("add Test2 passed");
        }


        //测试用例4：
        Double[][] testd1=new Double[][]{{1.0,2.0,3.0}};
        Double[][] testd2=new Double[][]{{1.0},{2.0}};;
        Double[][] result4 =MatrixOperation.add(testd1,testd2);
        Double[][] expected4=null;
        if(result4==null){
            System.out.println("add Test4 passed");
        }

        //测试用例3：
        Double[][] testc1=null;
        Double[][] testc2=null;
        Double[][] result3 =MatrixOperation.add(testc1,testc2);
        Double[][] expected3=null;
        Assert.assertArrayEquals(expected3,result3);
        if(result3==null){
            System.out.println("add Test3 passed");
        }
    }

    @Test
    void testAdd() {
    }

    @Test
    void testAdd1() {
    }

    @Test
    void minus() {
        //测试用例1：
        Double[][] testa1=new Double[][]{{1.0,2.0,3.0}};
        Double[][] testa2=new Double[][]{{1.0,2.0,3.0}};
        Double[][] result1 =MatrixOperation.minus(testa1,testa2);
        Double[][] expected1=new Double[][]{{0.0,0.0,0.0}};
        Assert.assertArrayEquals(expected1,result1);
        System.out.println("minus Test1 passed");

        //测试用例2：
        Double[][] testb1=new Double[][]{{}};
        Double[][] testb2=new Double[][]{{}};
        Double[][] result2 =MatrixOperation.minus(testb1,testb2);
        Double[][] expected2=null;
        if(result2==null){
            System.out.println("minus Test2 passed");
        }


        //测试用例4：
        Double[][] testd1=new Double[][]{{1.0,2.0,3.0}};
        Double[][] testd2=new Double[][]{{1.0},{2.0}};;
        Double[][] result4 =MatrixOperation.minus(testd1,testd2);
        Double[][] expected4=null;
        if(result4==null){
            System.out.println("minus Test4 passed");
        }

        //测试用例3：
        Double[][] testc1=null;
        Double[][] testc2=null;
        Double[][] result3 =MatrixOperation.minus(testc1,testc2);
        Double[][] expected3=null;
        Assert.assertArrayEquals(expected3,result3);
        if(result3==null){
            System.out.println("minus Test3 passed");
        }
    }

    @Test
    void testMinus() {
    }

    @Test
    void multi() {
        //测试用例1：
        ArrayList<ArrayList<Double>> test1=new ArrayList<>();
        test1.add(new ArrayList<>());
        Double[] tmp=new Double[]{1.0,2.0,3.0};
        test1.get(0).addAll(Arrays.asList(tmp));
        ArrayList<ArrayList<Double>> result1=MatrixOperation.multi(test1,2.0);
        ArrayList<ArrayList<Double>> expected1=new ArrayList<>();
        expected1.add(new ArrayList<>());
        Double[] tmp2=new Double[]{2.0,4.0,6.0};
        expected1.get(0).addAll(Arrays.asList(tmp2));
        Assert.assertEquals(expected1,result1);
        System.out.println("multi Test1 passed");

        //测试用例3：
        ArrayList<ArrayList<Double>> test3=null;
        ArrayList<ArrayList<Double>> result3=MatrixOperation.multi(test3,2.0);
        ArrayList<ArrayList<Double>> expected3=null;
        if(result3==null){
            System.out.println("multi Test3 passed");
        }


        //测试用例2：
        ArrayList<ArrayList<Double>> test2=new ArrayList<>();
        test1.add(new ArrayList<>());
        ArrayList<ArrayList<Double>> result2=MatrixOperation.multi(test2,2.0);
        ArrayList<ArrayList<Double>> expected2=new ArrayList<>();
        expected2.add(new ArrayList<>());
        Assert.assertEquals(expected2,result2);
        System.out.println("multi Test2 passed");


    }

    @Test
    void matrixMulti() {
        //测试用例1：
        Double[][] testa1=new Double[][]{{1.0,2.0,3.0}};
        Double[][] testa2=new Double[][]{{1.0,2.0,3.0}};
        Double[][] result1 =MatrixOperation.matrixMulti(testa1,testa2);
        Double[][] expected1=new Double[][]{{1.0,4.0,9.0}};
        Assert.assertArrayEquals(expected1,result1);
        System.out.println("matrixMulti Test1 passed");

        //测试用例2：
        Double[][] testb1=new Double[][]{{}};
        Double[][] testb2=new Double[][]{{}};
        Double[][] result2 =MatrixOperation.matrixMulti(testb1,testb2);
        Double[][] expected2=null;
        if(result2==null){
            System.out.println("matrixMulti Test2 passed");
        }


        //测试用例4：
        Double[][] testd1=new Double[][]{{1.0,2.0,3.0}};
        Double[][] testd2=new Double[][]{{1.0},{2.0}};;
        Double[][] result4 =MatrixOperation.matrixMulti(testd1,testd2);
        Double[][] expected4=null;
        if(result4==null){
            System.out.println("matrixMulti Test4 passed");
        }

        //测试用例3：
        Double[][] testc1=null;
        Double[][] testc2=null;
        Double[][] result3 =MatrixOperation.matrixMulti(testc1,testc2);
        Double[][] expected3=null;
        Assert.assertArrayEquals(expected3,result3);
        if(result3==null){
            System.out.println("matrixMulti Test3 passed");
        }
    }

    @Test
    void testMatrixMulti() {
    }

    @Test
    void maxNum() {
        //测试用例1
        Double[][] tmp1=new Double[][]{{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
        ArrayList<ArrayList<Double>> test1=convertToList(tmp1);
        Double expected1=7d;
        Double result1=MatrixOperation.maxNum(test1,0);
        Assert.assertEquals(expected1,result1,0.0000001);
        System.out.println("maxNum Test1 passed");

        //测试用例2
        Double[][] tmp2=new Double[][]{{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
        ArrayList<ArrayList<Double>> test2=convertToList(tmp2);
        Double expected2=null;
        Double result2=MatrixOperation.maxNum(test2,4);
        if(result2==null){
            System.out.println("maxNum Test2 passed");
        }

        //测试用例3
        Double[][] tmp3=new Double[][]{{}};
        ArrayList<ArrayList<Double>> test3=convertToList(tmp3);
        Double expected3=null;
        Double result3=MatrixOperation.maxNum(test3,4);
        if(result3==null){
            System.out.println("maxNum Test3 passed");
        }
    }

    @Test
    void minNum() {
        //测试用例1
        Double[][] tmp1=new Double[][]{{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
        ArrayList<ArrayList<Double>> test1=convertToList(tmp1);
        Double expected1=1d;
        Double result1=MatrixOperation.minNum(test1,0);
        Assert.assertEquals(expected1,result1,0.0000001);
        System.out.println("minNum Test1 passed");

        //测试用例2
        Double[][] tmp2=new Double[][]{{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
        ArrayList<ArrayList<Double>> test2=convertToList(tmp2);
        Double expected2=null;
        Double result2=MatrixOperation.minNum(test2,4);
        if(result2==null){
            System.out.println("minNum Test2 passed");
        }

        //测试用例3
        Double[][] tmp3=new Double[][]{{}};
        ArrayList<ArrayList<Double>> test3=convertToList(tmp3);
        Double expected3=null;
        Double result3=MatrixOperation.minNum(test3,4);
        if(result3==null){
            System.out.println("minNum Test3 passed");
        }
    }

    @Test
    void conversetoDouble() {
        //测试用例1：
        Double[][] tmp1=new Double[][]{{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
        ArrayList<ArrayList<Double>> test1=convertToList(tmp1);
        double[][] result1=MatrixOperation.conversetoDouble(test1,true);
        double[][] expected1={{0.0,0.0,0.0},{0.5,0.5,0.5},{1.0,1.0,1.0}};
        Assert.assertArrayEquals(result1,expected1);
        System.out.println("conversetoDouble Test1 passed");

        //测试用例2：
        Double[][] tmp2=new Double[][]{{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
        ArrayList<ArrayList<Double>> test2=convertToList(tmp1);
        double[][] result2=MatrixOperation.conversetoDouble(test2,false);
        double[][] expected2={{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
        Assert.assertArrayEquals(result2,expected2);
        System.out.println("conversetoDouble Test2 passed");
        //测试用例3：
        Double[][] tmp3=new Double[][]{};
        ArrayList<ArrayList<Double>> test3=convertToList(tmp3);
        double[][] result3=MatrixOperation.conversetoDouble(test3,false);
        double[][] expected3=null;
        if(result3==null){
            System.out.println("conversetoDouble Test3 passed");
        }


        //测试用例4：
        Double[][] tmp4=null;
        ArrayList<ArrayList<Double>> test4=convertToList(tmp3);
        double[][] result4=MatrixOperation.conversetoDouble(test4,false);
        double[][] expected4=null;
        if(result4==null){
            System.out.println("conversetoDouble Test4 passed");
        }
    }

    @Test
    void converse() {
        //测试用例1：
        Double[][] tmp1=new Double[][]{{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
        ArrayList<ArrayList<Double>> test1=convertToList(tmp1);
        ArrayList<ArrayList<Double>> result1=MatrixOperation.converse(test1,true);
        Double[][] expectedtmp1={{0.0,0.0,0.0},{0.5,0.5,0.5},{1.0,1.0,1.0}};
        ArrayList<ArrayList<Double>> expected1=convertToList(expectedtmp1);
        Assert.assertEquals(result1,expected1);
        System.out.println("converse Test1 passed");

        //测试用例2：
        Double[][] tmp2=new Double[][]{{1.0,2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
        ArrayList<ArrayList<Double>> test2=convertToList(tmp2);
        ArrayList<ArrayList<Double>> result2=MatrixOperation.converse(test2,true);
        Double[][] expectedtmp2={{0.0,0.0,0.0},{0.5,0.5,0.5},{1.0,1.0,1.0}};
        ArrayList<ArrayList<Double>> expected2=convertToList(expectedtmp2);
        Assert.assertEquals(result2,expected2);
        System.out.println("converse Test2 passed");
        //测试用例3：
        Double[][] tmp3=new Double[][]{};
        ArrayList<ArrayList<Double>> test3=convertToList(tmp3);
        ArrayList<ArrayList<Double>> result3=MatrixOperation.converse(test3,true);
        ArrayList<ArrayList<Double>> expected3=null;
        if(result3==null){
            System.out.println("conversetoDouble Test3 passed");
        }


        //测试用例4：
        ArrayList<ArrayList<Double>> test4=null;
        ArrayList<ArrayList<Double>> result4=MatrixOperation.converse(test4,true);
        ArrayList<ArrayList<Double>> expected4=null;
        if(result4==null){
            System.out.println("conversetoDouble Test3 passed");
        }
    }
}