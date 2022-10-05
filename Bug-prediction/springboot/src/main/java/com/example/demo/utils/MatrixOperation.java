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

    //矩阵乘法（叉乘）重载
    public static ArrayList<ArrayList<Double>> matrixMatmul(ArrayList<ArrayList<Double>>A,ArrayList<ArrayList<Double>>B){

        int m=A.size();
        if(m==0){
            return null;
        }
        int n=A.get(0).size();
        int p=B.size();
        if(p==0){
            return null;
        }
        int q=B.get(0).size();
        if(n!=p){
            return null;
        }else{
            ArrayList<ArrayList<Double>>result=new ArrayList<>();
            for(int i=0;i<m;i++){
                ArrayList<Double>temp=new ArrayList<>();
                for(int j=0;j<q;j++){
                    double num=0;
                    for(int k=0;k<n;k++){
                        num+=A.get(i).get(k)*B.get(k).get(j);
                    }
                    temp.add(num);
                }
                result.add(temp);
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

    //截取矩阵的一部分,(p,q),(m,n)为截取部分的左上角和右下角坐标（重载）
    public static ArrayList<ArrayList<Double>> iloc(ArrayList<ArrayList<Double>>A,int p,int q,int m,int n){
        int M= A.size();
        if(M==0){
            return null;
        }
        int N=A.get(0).size();
        if(p<0||q<0||m<0||n<0||m>=M||n>=N||(p>m&&q<n)){
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
            ArrayList<ArrayList<Double>>result=new ArrayList<>();
            for(int i=p;i<=m;i++){
                ArrayList<Double>temp=new ArrayList<>();
                for(int j=q;j<=n;j++){
                    temp.add(A.get(i).get(j));
                }
                result.add(temp);
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

    //求矩阵各元素之和(重载)
    public static Double sum(ArrayList<ArrayList<Double>>A){
        Double result=new Double(0);
        for(int i=0;i<A.size();i++){
            for(int j=0;j<A.get(0).size();j++){
                result+=A.get(i).get(j);
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

    //求矩阵各元素平均值（重载）
    public static Double mean(ArrayList<ArrayList<Double>>A){
        Double result=sum(A);
        if(A.size()==0){
            return null;
        }
        int num=A.size()*A.get(0).size();
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

    //求矩阵A的转置矩阵A.T(重载)
    public static ArrayList<ArrayList<Double>> trans(ArrayList<ArrayList<Double>>A){

        if(A.size()==0){
            return null;
        }
        ArrayList<ArrayList<Double>> result=new ArrayList<>();
        for(int j=0;j<A.get(0).size();j++){
            result.add(new ArrayList<>());
        }
        for(int i=0;i<A.size();i++){
            for(int j=0;j<A.get(0).size();j++){
                result.get(j).add(A.get(i).get(j));
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

    //求矩阵某一列各元素和(重载)
    public static Double sum_column(ArrayList<ArrayList<Double>>A,int index){
        Double result=new Double(0);
        if(A.size()==0){
            return null;
        }
        if(index>=A.get(0).size()){
            return null;
        }
        for(int i=0;i<A.size();i++){
            result+=A.get(i).get(index);
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

    //求矩阵某一列各元素平均值（重载）
    public static Double mean_column(ArrayList<ArrayList<Double>>A,int index){
        if(A.size()==0){
            return null;
        }
        Double result=new Double(0);
        if(index>=A.get(0).size()){
            return null;
        }
        result=sum_column(A,index);
        return result/A.size();
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

    //计算矩阵加法(重载1)
    public static ArrayList<ArrayList<Double>> add(ArrayList<ArrayList<Double>>A,ArrayList<ArrayList<Double>>B){

        int m=A.size();
        if(m==0){
            return null;
        }
        int n=A.get(0).size();
        int p=B.size();
        if(p==0){
            return null;
        }
        int q=B.get(0).size();
        if(m!=p||n!=q){
            return null;
        }else{
            ArrayList<ArrayList<Double>>result=new ArrayList<>();
            for(int i=0;i<m;i++){
                ArrayList<Double>temp=new ArrayList<>();
                for(int j=0;j<n;j++){
                    temp.add(A.get(i).get(j)+B.get(i).get(j));
                }
                result.add(temp);
            }
            return result;
        }
    }

    //计算矩阵加法（重载2）
    public static ArrayList<ArrayList<Double>> add(ArrayList<ArrayList<Double>>A,Double num){

        int m=A.size();
        if(m==0){
            return null;
        }
        ArrayList<ArrayList<Double>>result=new ArrayList<>(A);
        for(int i=0;i<A.size();i++){
            for(int j=0;j<A.get(i).size();j++){
                result.get(i).set(j,A.get(i).get(j)+num);
            }
        }
        return result;
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

    //计算矩阵减法，矩阵A-B
    public static ArrayList<ArrayList<Double>> minus(ArrayList<ArrayList<Double>>A,ArrayList<ArrayList<Double>>B){

        int m=A.size();
        if(m==0){
            return null;
        }
        int n=A.get(0).size();
        int p=B.size();
        if(p==0){
            return null;
        }
        int q=B.get(0).size();
        if(m!=p||n!=q){
            return null;
        }else{
            ArrayList<ArrayList<Double>>result=new ArrayList<>();
            for(int i=0;i<m;i++){
                ArrayList<Double>temp=new ArrayList<>();
                for(int j=0;j<n;j++){
                    temp.add(A.get(i).get(j)-B.get(i).get(j));
                }
                result.add(temp);
            }
            return result;
        }
    }

    //计算矩阵乘以标量num，即让矩阵中每个元素乘以num
    public static ArrayList<ArrayList<Double>> multi(ArrayList<ArrayList<Double>>A,Double num){
        if(A.size()==0){
            return null;
        }else{
            ArrayList<ArrayList<Double>>result=new ArrayList<>();
            for(int i=0;i<A.size();i++){
                ArrayList<Double>temp=new ArrayList<>();
                for(int j=0;j<A.get(i).size();j++){
                    temp.add(A.get(i).get(j)*num);
                }
                result.add(temp);
            }
            return result;
        }
    }

    //计算矩阵内积，矩阵A*B
    public static Double[][] matrixMulti(Double[][]A,Double[][]B){

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

    //计算矩阵内积，矩阵A*B
    public static ArrayList<ArrayList<Double>> matrixMulti(ArrayList<ArrayList<Double>>A,ArrayList<ArrayList<Double>>B){

        int m=A.size();
        if(m==0){
            return null;
        }
        int n=A.get(0).size();
        int p=B.size();
        if(p==0){
            return null;
        }
        int q=B.get(0).size();
        if(m!=p||n!=q){
            return null;
        }else{
            ArrayList<ArrayList<Double>>result=new ArrayList<>();
            for(int i=0;i<m;i++){
                ArrayList<Double>temp=new ArrayList<>();
                for(int j=0;j<n;j++){
                    temp.add(A.get(i).get(j)*B.get(i).get(j));
                }
                result.add(temp);
            }
            return result;
        }
    }

    //求矩阵某一列的最大值
    public static Double maxNum(ArrayList<ArrayList<Double>>matrix,int index){
        if(matrix.size()==0){
            return null;
        }
        Double result=new Double(Double.MIN_VALUE);
        for(int i=0;i<matrix.size();i++){
            if(result<matrix.get(i).get(index)){
                result=matrix.get(i).get(index);
            }
        }
        return result;
    }

    //求矩阵某一列的最小值
    public static Double minNum(ArrayList<ArrayList<Double>>matrix,int index){
        if(matrix.size()==0){
            return null;
        }
        Double result=new Double(Double.MAX_VALUE);
        for(int i=0;i<matrix.size();i++){
            if(result>matrix.get(i).get(index)){
                result=matrix.get(i).get(index);
            }
        }
        return result;
    }


    //数据处理，原始数据转换成Double[][]矩阵，dataProcess如果为真则将数据归一化
    public static double[][] conversetoDouble(ArrayList<ArrayList<Double>>origin_matrix,Boolean dataProcess){
        int m=origin_matrix.size();
        if(m==0){
            return null;
        }
        int n=origin_matrix.get(0).size();
        double[][]result=new double[m][n];
        if(dataProcess==false){
            for(int i=0;i<origin_matrix.size();i++){
                for(int j=0;j<origin_matrix.get(0).size();j++){
                    result[i][j]=origin_matrix.get(i).get(j);
                }
            }

        }else{
            for(int j=0;j<origin_matrix.get(0).size();j++){
                Double maxn=maxNum(origin_matrix,j);
                Double minn=minNum(origin_matrix,j);
                if(maxn!=null&&minn!=null){
                    for(int i=0;i<origin_matrix.size();i++){
                        result[i][j]=(origin_matrix.get(i).get(j)-minn)/(maxn-minn);

                    }
                }
            }
        }
        return result;
    }

    //数据处理,dataProcess如果为真则将数据归一化
    public static ArrayList<ArrayList<Double>> converse(ArrayList<ArrayList<Double>>origin_matrix,Boolean dataProcess){
        int m=origin_matrix.size();
        if(m==0){
            return null;
        }
        int n=origin_matrix.get(0).size();
        ArrayList<ArrayList<Double>>result=new ArrayList<>(origin_matrix);
        if(dataProcess==false){


        }else{
            for(int j=0;j<origin_matrix.get(0).size();j++){
                Double maxn=maxNum(origin_matrix,j);
                Double minn=minNum(origin_matrix,j);
                if(maxn!=null&&minn!=null){
                    for(int i=0;i<origin_matrix.size();i++){
                        result.get(i).set(j,(origin_matrix.get(i).get(j)-minn)/(maxn-minn));
                    }
                }
            }
        }
        return result;
    }
}
