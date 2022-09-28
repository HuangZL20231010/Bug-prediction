package com.example.demo.utils;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MatrixOperation {

    //矩阵乘法（叉乘）
    public static Double[][] matrixMatmul(Double[][]A,Double[][]B){

        int m=A.length;
        if(m==0){
            return null;
        }
        int n=A[0].length;
        int p=B.length;
        if(p==0){
            return null;
        }
        int q=B[0].length;
        if(n!=p){
            return null;
        }else{
            Double[][]result=new Double[m][q];
            for(int i=0;i<m;i++){
                for(int j=0;j<q;j++){
                    double num=0;
                    for(int k=0;k<n;k++){
                        num+=A[i][k]*B[k][j];
                    }
                    result[i][j]=num;
                }

            }
            return result;
        }
    }

    //截取矩阵的一部分,(p,q),(m,n)为截取部分的左上角和右下角坐标
    public static Double[][] iloc(Double[][]A,int p,int q,int m,int n){

        int M= A.length;
        if(M==0){
            return null;
        }
        int N=A[0].length;
        if(p<0||q<0||m<0||n<0||m>=M||n>=N||(p>=m&&q<n)){
            return null;
        }
        else{
            if(p>=m&&q>=n){
                int temp=p;
                p=m;
                m=temp;
                temp=q;
                q=n;
                n=temp;
            }
            Double[][]result=new Double[m-p+1][n-q+1];
            for(int i=p;i<=m;i++){
                for(int j=q;j<=n;j++){
                    result[i-p][j-q]=A[i][j];
                }
            }
            return result;
        }
    }

    //求矩阵各元素之和
    public static Double sum(Double[][]A){
        Double result=new Double(0);
        for(int i=0;i<A.length;i++){
            for(int j=0;j<A[0].length;j++){
                result+=A[i][j];
            }
        }
        return result;
    }

    //求矩阵各元素平均值
    public static Double mean(Double[][]A){
        Double result=sum(A);
        if(A.length==0){
            return null;
        }
        int num=A.length*A[0].length;
        return result/num;
    }

    //求矩阵A的转置矩阵A.T
    public static Double[][] trans(Double[][]A){

        if(A.length==0){
            return null;
        }
        Double[][]result=new Double[A[0].length][A.length];
        for(int i=0;i<A.length;i++){
            for(int j=0;j<A[0].length;j++){
                result[j][i]=A[i][j];
            }
        }
        return result;
    }

    //求矩阵某一列各元素和
    public static Double sum_column(Double[][]A,int index){
        Double result=new Double(0);
        if(A.length==0){
            return null;
        }
        if(index>=A[0].length){
            return null;
        }
        for(int i=0;i<A.length;i++){
            result+=A[i][index];
        }
        return result;
    }

    //求矩阵某一列各元素平均值
    public static Double mean_column(Double[][]A,int index){
        if(A.length==0){
            return null;
        }
        Double result=new Double(0);
        if(index>=A[0].length){
            return null;
        }
        result=sum_column(A,index);
        return result/A.length;
    }

    //计算矩阵加法
    public static Double[][] add(Double[][]A,Double[][]B){

        int m=A.length;
        if(m==0){
            return null;
        }
        int n=A[0].length;
        int p=B.length;
        if(p==0){
            return null;
        }
        int q=B[0].length;
        if(m!=p||n!=q){
            return null;
        }else{
            Double[][] result=new Double[m][n];
            for(int i=0;i<m;i++){
                for(int j=0;j<n;j++){
                    result[i][j]=A[i][j]+B[i][j];
                }
            }
            return result;
        }
    }

    //计算矩阵减法，矩阵A-B
    public static Double[][] minus(Double[][]A,Double[][]B){

        int m=A.length;
        if(m==0){
            return null;
        }
        int n=A[0].length;
        int p=B.length;
        if(p==0){
            return null;
        }
        int q=B[0].length;
        if(m!=p||n!=q){
            return null;
        }else{
            Double[][] result=new Double[m][n];
            for(int i=0;i<m;i++){
                for(int j=0;j<n;j++){
                    result[i][j]=A[i][j]-B[i][j];
                }
            }
            return result;
        }
    }

    //计算矩阵内积，矩阵A*B
    public static Double[][] multi(Double[][]A,Double[][]B){

        int m=A.length;
        if(m==0){
            return null;
        }
        int n=A[0].length;
        int p=B.length;
        if(p==0){
            return null;
        }
        int q=B[0].length;
        if(m!=p||n!=q){
            return null;
        }else{
            Double[][] result=new Double[m][n];
            for(int i=0;i<m;i++){
                for(int j=0;j<n;j++){
                    result[i][j]=A[i][j]*B[i][j];
                }
            }
            return result;
        }
    }
}
