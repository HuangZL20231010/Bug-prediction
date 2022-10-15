package com.example.demo.utils;


/*
 * @(#)MutilInstance.java
 * The class of load data.
 * Author: Yu-Xuan Zhang
 * Email: inki.yinji@qq.com
 * Created: October 17, 2019.
 * Last modified: December 17, 2019.
 */
public class Classify {

    /**
     * The number of row.
     */
    public static int numM;

    /**
     * The number of column.
     */
    public static int numN;

    /**
     * The distance of instance.
     */
    public static double[][] data;

    /**
     * The label of bags.
     */
    public static double[] label;

    /**
     * The label of bags.
     */
    public static int[] index;

    /**
     ************
     * The first constructor.
     *
     * @param paraData The all data.
     * @param paraLabel The label of all data.
     ************
     */
    public Classify(double[][] paraData, double[] paraLabel) {
        data = paraData;
        label = paraLabel;
        numM = data.length;
        numN = data[0].length;
        index = SimpleTool.arrayIndexAuto(numM);
    }// The constructor

    public static void main(String[] args) {
        System.out.println("");
    }// The main

}// Of class Classify
