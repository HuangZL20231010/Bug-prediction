package com.example.demo.service.impl;

import java.util.Arrays;
import com.example.demo.utils.*;

/*
 * @(#)SVM.java
 * The class of SVM.
 * Author: Yu-Xuan Zhang
 * Email: inki.yinji@qq.com
 * Created: October 5, 2019.
 * Last modified: December 18, 2019.
 */
public class SVM extends Classify {
    // lin核函数
    public static final int KERNEL_LIN = 1;

    // rbf核函数
    public static final int KERNEL_RBF = 0;

    public static int numTrainingData;  // 训练数据集大小

    public int numTestData;

    /**
     * The index of training data.
     */
    public static int[] trainingDataIndex;

    /**
     * The index of test data.
     */
    public int[] testDataIndex;

    /**
     * The upper border.
     */
    private double C;

    /**
     * The fault tolerance rate.
     */
    private double toler;

    /**
     * The type of kernel function.
     */
    private int kernelType;

    /**
     * The size of kernel.
     */
    private double kernel;

    /**
     * The array of K.
     */
    private double[][] K;

    /**
     * The alpha.
     */
    private double[] alphas;

    /**
     * The eCache.
     */
    private double[][] eCache;

    /**
     * The b.
     */
    private double b;

    /**
     * The max-number of iteration.
     */
    private int maxIter;

    /**
     *************
     * The constructor of first SVM.
     *
     * @param paraData  The data.
     * @param paralabel The label of data.
     * @param paraC          The upper bound.
     * @param paraToler      The tolerance.
     * @param paraKernelType The type of kernel function.
     * @param paraKernel     The size of kernel.
     *************
     */
    public SVM(double[][] paraData, double[] paralabel, double paraC, double paraToler, int paraMaxIter,
               int paraKernelType, double paraKernel) {
        super(paraData, paralabel);
        C = paraC;
        toler = paraToler;
        maxIter = paraMaxIter;
        kernelType = paraKernelType;
        kernel = paraKernel;
        SimpleTool.tracingStarting("Hello SVM(double[][], double[], double, double, int, int, double).");
    }// The first constructor

    /**
     *************
     * The constructor of second SVM.
     *
     * @param paraData  The data.
     * @param paralabel The label of data.
     * @param paraC          The upper bound.
     * @param paraToler      The tolerance.
     * @param paraKernelType The type of kernel function.
     *************
     */
    public SVM(double[][] paraData, double[] paralabel, double paraC, double paraToler, int paraMaxIter,
               int paraKernelType) {
        super(paraData, paralabel);
        C = paraC;
        toler = paraToler;
        kernelType = paraKernelType;
        SimpleTool.tracingStarting("Hello SVM(double[][], double[], double, double, int, int).");
    }// The second constructor

    /**
     *************
     * Initialize
     *************
     */
    private void initialize() {
        b = 0;
        K = new double[numM][];
//		for (int i = 0; i < K.length; i++) {
//			Arrays.fill(K[i], 0);
//		}//Of for i
        double[][] tempK = new double[numM][];
        switch (kernelType) {
            case KERNEL_LIN :
                for (int i = 0; i < numM; i++) {
                    tempK[i] = SimpleTool.kernelLinear(data, data[i]);
                } // Of for i
                break;
            case KERNEL_RBF:
                for (int i = 0; i < numM; i++) {
                    tempK[i] = SimpleTool.kernelRBF(data, data[i], kernel);
                } // Of for i
                break;
            default:
                System.out.println("Error--> That Kernel is not recognized!");
                break;
        }
        K = SimpleTool.arrayTranspose(tempK);
        alphas = new double[numM];
        eCache = new double[2][numM];
    }// initialize

    /**
     *************
     * Set index of training data.
     *
     * @param paraTrainingDataIndex The index of training data set.
     *************
     */
    public void setTrainingData(int[] paraTrainingDataIndex, int[] paraTestDataIndex) {
        trainingDataIndex = paraTrainingDataIndex;
        numTrainingData = trainingDataIndex.length;
        testDataIndex = paraTestDataIndex;
        numTestData = testDataIndex.length;
        initialize();
    }// Of setTrainingData

    /**
     ************
     * Select j not equal i.
     *
     * @param paraI The truth index i
     * @return The j not equal i.
     ************
     */
    private static int selcetJRandom(int paraI) {  // 返回与参数不同的随机变量
        int resultJ = paraI;
        while (resultJ == paraI) {
            resultJ = SimpleTool.random.nextInt(numTrainingData);
            resultJ = trainingDataIndex[resultJ];
        } // Of while
        return resultJ;
    }// Of selectJRandom

    /**
     ************
     * Numerical adjustment.
     *
     * @param paraAlphaJ  The alpha of index j.
     * @param paraHighest 上边界
     * @param paraLowest  下边界
     * @return The j not equal i.
     ************
     */
    private double clipAlpha(double paraAlphaJ, double paraHighest, double paraLowest) {
        if (paraAlphaJ > paraHighest) {
            paraAlphaJ = paraHighest;
        } // Of if

        if (paraLowest > paraAlphaJ) {
            paraAlphaJ = paraLowest;
        } // Of if

        return paraAlphaJ;
    }// Of clipAlpha

    /**
     ************
     * Compute Error of index i.
     *
     * @param paraI The index of i.
     * @return The Error of index i.
     ************
     */
    private double computeErrorI(int paraI) {
        double[] tempI = SimpleTool.arrayColumnValue(K, paraI);
        double tempErrorI = SimpleTool.arrayMultiplyAndAdd(SimpleTool.arrayMultiply(alphas, label), tempI) + b;
        return tempErrorI - label[paraI];
    }// Of computeE

    /**
     ************
     * Select second j.
     *
     * @param paraI  The index of i.
     * @param
     * @return The Error.
     ************
     */
    public double[] selectJ(int paraI, double paraErrorI) {
        int tempMaxI = -1;
        double tempErrorJ = 0;
        double tempMaxDiffError = 0;
        eCache[0][paraI] = 1;
        eCache[1][paraI] = paraErrorI;
        int[] tempValidECacheList = SimpleTool.computeNonZeroIndex(eCache[0]);
        int tempValidECacheListLen = tempValidECacheList.length;
        // The error of i.
        double tempErrorI = 0;
        // The diffience of two error.
        double tempDiffErrorI = 0;
        double[] resultMaxErrorEAndErrorj = new double[2];
        if (tempValidECacheListLen > 1) {
            for (int i = 0; i < tempValidECacheListLen; i++) {
                // Skip equal to paraI.
                if (tempValidECacheList[i] == paraI) {
                    continue;
                } // Of if
                tempErrorI = computeErrorI(tempValidECacheList[i]);
                tempDiffErrorI = Math.abs(paraErrorI - tempErrorI);
                if (tempDiffErrorI >= tempMaxDiffError) {
                    tempMaxI = tempValidECacheList[i];
                    tempMaxDiffError = tempDiffErrorI;
                    tempErrorJ = tempErrorI;
                } // Of if
            } // Of for i
        } else {
            tempMaxI = selcetJRandom(paraI);
            tempErrorJ = computeErrorI(tempMaxI);
        } // Of if
        resultMaxErrorEAndErrorj[0] = tempMaxI;
        resultMaxErrorEAndErrorj[1] = tempErrorJ;
        return resultMaxErrorEAndErrorj;
    }// Of selectJ

    /**
     ************
     * Update the error.
     *
     *
     ************
     */
    private void updateError(int paraI) {
        eCache[0][paraI] = 1;
        eCache[1][paraI] = computeErrorI(paraI);
    }// Of updateError

    /**
     ************
     * SMO's inner loop.
     *
     * @param paraI The index of i.
     * @return Does alpha change?
     ************
     */
    private int innerL(int paraI) {
        double tempErrorI = computeErrorI(paraI);

        int tempJ = 0;
        double tempErrorJ = 0;
        double[] tempJAndErrorJ = new double[2];

        double tempLowest = 0;
        double tempHighest = 0;
        double tempEta = 0;
        double tempAlphaI = 0;
        double tempAlphaJ = 0;
        double tempB1 = 0;
        double tempB2 = 0;

        if (((label[paraI] * tempErrorI < -toler) && (alphas[paraI] < C))
                || ((label[paraI] * tempErrorI > toler) && (alphas[paraI] > 0))) {
            tempJAndErrorJ = selectJ(paraI, tempErrorI);
            tempJ = (int) tempJAndErrorJ[0];
            tempErrorJ = tempJAndErrorJ[1];
            tempAlphaI = alphas[paraI];
            tempAlphaJ = alphas[tempJ];

            if (label[paraI] != label[tempJ]) {
                tempLowest = Math.max(0, alphas[tempJ] - alphas[paraI]);
                tempHighest = Math.min(C, C + alphas[tempJ] - alphas[paraI]);
            } else {
                tempLowest = Math.max(0, alphas[tempJ] + alphas[paraI] - C);
                tempHighest = Math.min(C, alphas[tempJ] + alphas[paraI]);
            } // Of if

            if (tempLowest == tempHighest) {
                SimpleTool.tracingProgress("L==H");
                return 0;
            } // Of if

            tempEta = 2 * K[paraI][tempJ] - K[paraI][paraI] - K[tempJ][tempJ];
            if (tempEta >= 0) {
                SimpleTool.tracingProgress("eta >= 0");
                return 0;
            } // Of if

            alphas[tempJ] -= label[tempJ] * (tempErrorI - tempErrorJ) / tempEta;
            alphas[tempJ] = clipAlpha(alphas[tempJ], tempHighest, tempLowest);
            updateError(tempJ);
            if (Math.abs(alphas[tempJ] - tempAlphaJ) < 1e-5) {
                SimpleTool.tracingProgress("J not moving enough");
                return 0;
            } // Of if

            alphas[paraI] += label[tempJ] * label[paraI] * (tempAlphaJ - alphas[tempJ]);
            updateError(paraI);
            tempB1 = b - tempErrorI - label[paraI] * (alphas[paraI] - tempAlphaI) * K[paraI][paraI]
                    - label[tempJ] * (alphas[tempJ] - tempAlphaJ) * K[paraI][tempJ];
            tempB2 = b - tempErrorJ - label[paraI] * (alphas[paraI] - tempAlphaI) * K[paraI][paraI]
                    - label[tempJ] * (alphas[tempJ] - tempAlphaJ) * K[tempJ][tempJ];

            if (0 < alphas[paraI] && C > alphas[paraI]) {
                b = tempB1;
            } else if (1e-5 < alphas[tempJ] && C > alphas[tempJ]) {
                b = tempB2;
            } else {
                b = (tempB1 + tempB2) / 2.0;
            } // Of if
            return 1;
        } else {
            return 0;
        } // Of if
    }

    /**
     ************
     * The complete SMO algorithm outer loop.
     *
     * @return The b and alphas.
     ************
     */
    public double[][] SMOP() {
        int tempIter = 0;
        // Whether to traverse the entire data set?
        boolean tempEntireSet = true;
        int tempAlphaPairsChanged = 0;
        while ((tempIter < maxIter) && ((tempAlphaPairsChanged > 0) || tempEntireSet)) {
            tempAlphaPairsChanged = 0;
            if (tempEntireSet) {
                for (int i = 0; i < numTrainingData; i++) {
                    tempAlphaPairsChanged += innerL(trainingDataIndex[i]);
                    SimpleTool.tracingProgress(
                            "Full set, iter: " + tempIter + ", i: " + i + "pairs changed " + tempAlphaPairsChanged);
                } // Of for i
            } else {
                int[] tempNonBound = SimpleTool.computeFixedIntervalIndex(alphas, 0, C);
                int tempNonBoundLen = tempNonBound.length;
                for (int i = 0; i < tempNonBoundLen; i++) {
                    tempAlphaPairsChanged += innerL(tempNonBound[i]);
                    SimpleTool.tracingProgress(
                            "Non-bound, iter: " + tempIter + "i: " + i + ", pairs changed " + tempAlphaPairsChanged);
                } // Of for i
            } // Of if

            tempIter++;
            if (tempEntireSet) {
                tempEntireSet = false;
            } else if (tempAlphaPairsChanged == 0) {
                tempEntireSet = true;
            } // Of of
            SimpleTool.tracingProgress("Iteration number: " + tempIter);
        } // Of while
        double[][] returnBAndAlphas = new double[2][];
        returnBAndAlphas[0] = new double[1];
        returnBAndAlphas[0][0] = b;

        returnBAndAlphas[1] = new double[numM];
        for (int i = 0; i < numM; i++) {
            returnBAndAlphas[1][i] = alphas[i];
        } // Of for i

        SimpleTool.tracingProgress("The b is: " + b);
        SimpleTool.tracingProgress("The alphas is: " + Arrays.toString(alphas));
        return returnBAndAlphas;
    }// Of SMOP

    /**
     ************
     * Compute the Weights.
     ************
     */
//    public double[] computeWeights() {
//        double[] returnW = new double[numN];
//        for (int i = 0; i < numN; i++) {
//            returnW = SimpleTool.arrayAdd(returnW, SimpleTool.arrayMultiply(alphas[i] * label[i], data[i]));
//        } // Of for i
//        return returnW;
//    }// Of computeWeights

    /**
     ************
     * Predictint label.
     *
     * @return The predicted label.
     ************
     */
    public double[] predictedLabel() {
        double[][] resultBAndAlphas = SMOP();
        double b = resultBAndAlphas[0][0];
        double[] alphas = resultBAndAlphas[1];
        int[] nonZeroAlphas = SimpleTool.computeNonZeroIndex(alphas);
        int tempNonZeroAlphasLength = nonZeroAlphas.length;

        double[][] tempDataSupported = new double[tempNonZeroAlphasLength][numN];
        double[] tempDataLabelSupported = new double[tempNonZeroAlphasLength];
        double[] tempAlphaSupported = new double[tempNonZeroAlphasLength];
        double tempPredicted;

        for (int j = 0; j < tempNonZeroAlphasLength; j++) {
            for (int j1 = 0; j1 < numN; j1++) {
                tempDataSupported[j][j1] = data[nonZeroAlphas[j]][j1];
            } // Of j2
            tempDataLabelSupported[j] = label[nonZeroAlphas[j]];
            tempAlphaSupported[j] = alphas[nonZeroAlphas[j]];
        } // Of for j

        double[] returnPredictedLabel = new double[numTestData];
        for (int i = 0; i < numTestData; i++) {
            double[] kernelEval = SimpleTool.kernelRBF(tempDataSupported, data[(int) testDataIndex[i]], kernel);
            tempPredicted = SimpleTool.arrayMultiplyAndAdd(kernelEval,
                    SimpleTool.arrayMultiply(tempDataLabelSupported, tempAlphaSupported)) + b;
            returnPredictedLabel[i] = tempPredicted;
        } // Of for i

        return returnPredictedLabel;
    }// Of predictedLabel

    /**
     ************
     * The test of SVM.
     ************
     */
    public static void testSvm() {
        double[][] tempData = SimpleTool.testData1();
        double[] tempLabel = SimpleTool.testLabel1();
        double tempC = 1.5;
        double tempKernel = .9;
        double tempToler = 1e-4;
        int tempMaxIter = 100;
        int tempKernelType = KERNEL_RBF;
        SVM svm = new SVM(tempData, tempLabel, tempC, tempToler, tempMaxIter, tempKernelType, tempKernel);
        int[] tempIndex = SimpleTool.arrayIndexAuto(numM);
        int[] tempTrainingDataIndex = new int[numM - 1];
        int[] tempTestingDataIndex = new int[1];
        double tempPredicted;
        double error = 0;
        for (int i = 0; i < tempLabel.length; i++) {
            tempTestingDataIndex[0] = i;
            tempTrainingDataIndex = SimpleTool.deleteElement(tempIndex, i);
            svm.setTrainingData(tempTrainingDataIndex, tempTestingDataIndex);
            tempPredicted = svm.predictedLabel()[0];

            if (SimpleTool.sign(tempPredicted) != tempLabel[i]) {
                SimpleTool.tracingPredictedError("The index-" + i + " is error: (the predicted:" + tempPredicted
                        + ", the real: " + tempLabel[i] + ")");
                error++;
            } // Of if
        } // Of for i
        SimpleTool.tracingFinalResult("The error rate is: " + error / numM);
    }// Of testSvm

    /**
     ************
     * The main
     ************
     */
    public static void main(String[] args) {
        testSvm();
    } // Of main
}// Of class SVM


