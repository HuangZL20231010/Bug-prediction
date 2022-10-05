package com.example.demo.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.example.demo.service.impl.FileProcessImpl;
import weka.core.Instances;

/**
 * @(#)SimpleTool.java The class of SimpleTool.
 * @author: Yu-Xuan Zhang
 * @Email: inki.yinji@qq.com
 * @Created: July 22, 2019.
 * @Last_modified: December 18, 2019.
 */
public class SimpleTool {

	/**
	 * A global random object.
	 */
	public static final Random random = new Random();

	/**
	 * The variable of progress tracing.
	 */
	public static boolean PROGRESS_TRACING = false;

	/**
	 * The variable of predicted error progress tracing.
	 */
	public static boolean PREDICTED_ERROR_TRACING = true;

	/**
	 * The variable of middle result tracing.
	 */
	public static boolean MIDDLE_RESULT_TRACING = false;

	/**
	 * The variable of final result tracing.
	 */
	public static boolean FINAL_RESULT_TRACING = true;

	/**
	 * The variable of starting tracing.
	 */
	public static boolean STARTING_TRACING = false;

	/**
	 * The variable of function introduction.
	 */
	private static final boolean FUNCTION_INTRODUCTION = true;

	/**
	 ************
	 * The simpleTool function summary.
	 ************
	 */
	public static void simpleToolIntroduction() {
		int tempI = 1;
		if (FUNCTION_INTRODUCTION) {
			System.out.println("The summary function is:\n"

					+ "Function" + (tempI++) + ": autoNorm;\n"

					+ "Function" + (tempI++) + ": arrayMultiply;\n"

					+ "Function" + (tempI++) + ": arrayMultiplyAndAdd;\n"

					+ "Function" + (tempI++) + ": arrayMinus;\n"

					+ "Function" + (tempI++) + ": arrayAdd;\n"

					+ "Function" + (tempI++) + ": arrayTranspose;\n"

					+ "Function" + (tempI++) + ": arrayMaximumValue;\n"

					+ "Function" + (tempI++) + ": arrayMinimumValue;\n"

					+ "Function" + (tempI++) + ": arrayMaximumValueIndex;\n"

					+ "Function" + (tempI++) + ": arrayMinimumValueIndex;\n"

					+ "Function" + (tempI++) + ": arrayColumnValue;\n"

					+ "Function" + (tempI++) + ": arrayRowValue;\n"

					+ "Function" + (tempI++) + ": arrayRowValueNot;\n"

					+ "Function" + (tempI++) + ": arrayIndexAuto;\n"

					+ "Function" + (tempI++) + ": arrayAppendElement;\n"

					+ "Function" + (tempI++) + ": arrayCopy;\n"

					+ "Function" + (tempI++) + ": arrayReverse;\n"

					+ "Function" + (tempI++) + ": arrayCorrespondingElementIndex;\n"

					+ "Function" + (tempI++) + ": arrayMerge;\n"

					+ "Function" + (tempI++) + ": arrayMean;\n"

					+ "Function" + (tempI++) + ": computeStandardDeviation;\n"

					+ "Function" + (tempI++) + ": computeNonZeroIndex;\n"

					+ "Function" + (tempI++) + ": computeFixtedIntervalIndex;\n"

					+ "Function" + (tempI++) + ": deleteElement;\n"

					+ "Function" + (tempI++) + ": fileReadArff;\n"

					+ "Function" + (tempI++) + ": fileRead;\n"

					+ "Function" + (tempI++) + ": generateRandomSequence;\n"

					+ "Function" + (tempI++) + ": GB2312ToUNICODE\n"

					+ "Function" + (tempI++) + ": isDoubleMatricesEqual;\n"

					+ "Function" + (tempI++) + ": isEmptyStr;\n"

					+ "Function" + (tempI++) + ": kernelRBF;\n"

					+ "Function" + (tempI++) + ": kernelLinear;\n"

					+ "Function" + (tempI++) + ": kernelGaussian;\n"

					+ "Function" + (tempI++) + ": logBase;\n"

					+ "Function" + (tempI++) + ": matrixCov;\n"

					+ "Function" + (tempI++) + ": matrixPca;\n"

					+ "Function" + (tempI++) + ": matrixDeterminant;\n"

					+ "Function" + (tempI++) + ": matrixGramSchimidt;\n"

					+ "Function" + (tempI++) + ": matrixInverse;\n"

					+ "Function" + (tempI++) + ": matrixQrDecomposition;\n"

					+ "Function" + (tempI++) + ": matrixMultiply;\n"

					+ "Function" + (tempI++) + ": matrixEigValue;\n"

					+ "Function" + (tempI++) + ": matrixEigVector;\n"

					+ "Function" + (tempI++) + ": matirxSubtract;\n"

					+ "Function" + (tempI++) + ": matrixNonZeroValueNumber;\n"

					+ "Function" + (tempI++) + ": matrixEye;\n"

					+ "Function" + (tempI++) + ": quickSortAscent;\n"

					+ "Function" + (tempI++) + ": quickSortDescend;\n"

					+ "Function" + (tempI++) + ": sign;\n"

					+ "Function" + (tempI++) + ": tracingProgress;\n"

					+ "Function" + (tempI++) + ": tracingMiddleResult;\n"

					+ "Function" + (tempI++) + ": tracingFinalResult;\n"

					+ "Function" + (tempI++) + ": tracingPredictedError;\n"

					+ "Function" + (tempI++) + ": tracingStaring;\n"

					+ "Function" + (tempI++) + ": testData1;\n"

					+ "Function" + (tempI++) + ": testLabel1;\n"

					+ "Function" + (tempI++) + ": testData2;\n"

					+ "Function" + (tempI++) + ": testLabel2\n");

		} // Of if
		if (tempI == 101) {
			System.out.println("С�����ɣ��������ͣ�");
		} else if (tempI - 1 == 1001) {
			System.out.println("���ڲ�����Ŀ�ʼ��");
		} // Of if
	}// Of simpleToolIntroduction

	/**
	 ************
	 * Normalized data, the result: the max-element <= 1, the min-element >= 0.
	 * 
	 * @param paraData The data(double[][]).
	 * @return The normalized data(double[][]).
	 ************
	 */
	public static double[][] autoNorm(double[][] paraData) {
		int tempM = paraData.length;
		int tempN = paraData[0].length;
		// Column minimum
		double[] tempMinValues = new double[tempN];
		// Column maximum
		double[] tempMaxValues = new double[tempN];
		double[] tempRanges = new double[tempN];
		double[][] returnNormalizedData = new double[tempM][tempN];

		Arrays.fill(tempMinValues, Double.MAX_VALUE);
		Arrays.fill(tempMaxValues, Double.MIN_VALUE);
		for (int j = 0; j < tempN; j++) {
			for (int i = 0; i < tempM; i++) {
				if (tempMinValues[j] > paraData[i][j]) {
					tempMinValues[j] = paraData[i][j];
				} // Of if

				if (tempMaxValues[j] < paraData[i][j]) {
					tempMaxValues[j] = paraData[i][j];
				} // Of if
			} // Of for i
		} // Of for j

		for (int i = 0; i < tempN; i++) {
			tempRanges[i] = tempMaxValues[i] - tempMinValues[i];
		} // Of for i

		for (int i = 0; i < tempM; i++) {
			for (int j = 0; j < tempN; j++) {
				returnNormalizedData[i][j] = (paraData[i][j] - tempMinValues[j]) / tempRanges[j];
			} // Of for j
		} // Of for i

		return returnNormalizedData;
	}// Of autoNorm

	/**
	 ************
	 * Array corresponding elements multiply.
	 * 
	 * @param paraFirstArray  The first array(double[]).
	 * @param paraSecondArray The second array(double[]).
	 * @return The result(double[]).
	 ************
	 */
	public static double[] arrayMultiply(double[] paraFirstArray, double[] paraSecondArray) {
		int tempM = paraFirstArray.length;
		double[] resultMultipliedArray = new double[tempM];

		for (int i = 0; i < tempM; i++) {
			resultMultipliedArray[i] = paraFirstArray[i] * paraSecondArray[i];
		} // Of for i

		return resultMultipliedArray;
	}// Of arrayMultiply

	/**
	 ************
	 * Array each elements multiply para-value.
	 * 
	 * @param paraNum   The number(double).
	 * @param paraArray The array(double[]).
	 * @return The result(double[]).
	 ************
	 */
	public static double[] arrayMultiply(double paraNum, double[] paraArray) {
		int tempM = paraArray.length;
		double[] resultMultipliedArray = new double[tempM];

		for (int i = 0; i < tempM; i++) {
			resultMultipliedArray[i] = paraNum * paraArray[i];
		} // Of for i

		return resultMultipliedArray;
	}// Of arrayMultiply

	/**
	 ************
	 * Array corresponding elements subtraction.
	 * 
	 * @param paraFirstArray  The first array(double[]).
	 * @param paraSecondArray The second array(double[]).
	 * @return The result(double[]).
	 ************
	 */
	public static double[] arrayMinus(double[] paraFirstArray, double[] paraSecondArray) {
		int tempM = paraFirstArray.length;
		double[] resultMinusedArray = new double[tempM];

		for (int i = 0; i < tempM; i++) {
			resultMinusedArray[i] = paraFirstArray[i] - paraSecondArray[i];
		} // Of for i

		return resultMinusedArray;
	}// Of arrayMinus

	/**
	 ************
	 * Array corresponding elements subtraction.
	 * 
	 * @param paraFirstArray  The first array(double[][]).
	 * @param paraSecondArray The second array(double[]).
	 * @return The result(double[][]).
	 ************
	 */
	public static double[][] arrayMinus(double[][] paraFirstArray, double[] paraSecondArray) {
		int tempM = paraFirstArray.length;
		double[][] resultArray = new double[tempM][];
		for (int i = 0; i < tempM; i++) {
			resultArray[i] = arrayMinus(paraFirstArray[i], paraSecondArray);
		} // Of for i

		return resultArray;
	}// Of arrayMinus

	/**
	 ************
	 * Array corresponding elements multiply and add all elements.���
	 * 
	 * @param paraArray The given array(double[]).
	 * @return The result(double).
	 ************
	 */
	public static double arrayMultiplyAndAdd(double[] paraArray) {
		int tempM = paraArray.length;
		double resultMultipliedArray = 0;

		for (int i = 0; i < tempM; i++) {
			resultMultipliedArray += paraArray[i] * paraArray[i];
		} // Of for i

		return resultMultipliedArray;
	}// Of arrayMultiplyAndAdd

	/**
	 ************
	 * Array corresponding elements multiply and add all elements.���
	 * 
	 * @param paraFirstArray  The first array(double[]).
	 * @param paraSecondArray The second array(double[]).
	 * @return The result(double).
	 ************
	 */
	public static double arrayMultiplyAndAdd(double[] paraFirstArray, double[] paraSecondArray) {
		int tempM = paraFirstArray.length;
		double resultMultipliedArray = 0;

		for (int i = 0; i < tempM; i++) {
			resultMultipliedArray += paraFirstArray[i] * paraSecondArray[i];
		} // Of for i

		return resultMultipliedArray;
	}// Of arrayMultiplyAndAdd

	/**
	 ************
	 * Array corresponding elements subtraction.
	 * 
	 * @param paraFirstArray  The first array(int[]).
	 * @param paraSecondArray The second array(double[]).
	 * @return The result(int[]).
	 ************
	 */
	public static int[] arrayMinus(int[] paraFirstArray, double[] paraSecondArray) {
		int tempM = paraFirstArray.length;
		int[] resultMinusedArray = new int[tempM];

		for (int i = 0; i < tempM; i++) {
			resultMinusedArray[i] = paraFirstArray[i] - (int) paraSecondArray[i];
		} // Of for i

		return resultMinusedArray;
	}// Of arrayMinus

	/**
	 ************
	 * Array corresponding elements add.
	 * 
	 * @param paraFirstArray  The first array(double[]).
	 * @param paraSecondArray The second array(double[]).
	 * @return The result(double[]).
	 ************
	 */
	public static double[] arrayAdd(double[] paraFirstArray, double[] paraSecondArray) {
		int tempM = paraFirstArray.length;
		double[] resultAddedArray = new double[tempM];

		for (int i = 0; i < tempM; i++) {
			resultAddedArray[i] = paraFirstArray[i] + paraSecondArray[i];
		} // Of for i

		return resultAddedArray;
	}// Of arrayAdd

	/**
	 ************
	 * Get the sum of each element.
	 * 
	 * @param paraArray The array(int[]).
	 * @return The sum of each element(int).
	 ************
	 */
	public static int arrayAdd(int[] paraArray) {
		int tempM = paraArray.length;
		int resultAdd = 0;

		for (int i = 0; i < tempM; i++) {
			resultAdd += paraArray[i];
		} // Of for i
		return resultAdd;
	}// Of arrayAdd

	/**
	 ************
	 * Get the sum of each element.
	 * 
	 * @param paraArray The array(double[]).
	 * @return The sum of each element(double).
	 ************
	 */
	public static double arrayAdd(double[] paraArray) {
		int tempM = paraArray.length;
		double resultAdd = 0;

		for (int i = 0; i < tempM; i++) {
			resultAdd += paraArray[i];
		} // Of for i
		return resultAdd;
	}// Of arrayAdd

	/**
	 ************
	 * Array transpose.
	 * 
	 * @param paraArray The transposing array(double[][]).
	 * 
	 * @return returnArray The transposed array(double[][]).
	 ************
	 */
	public static double[][] arrayTranspose(double[][] paraArray) {
		int tempM = paraArray.length;
		int tempN = paraArray[0].length;
		double[][] returnArray = new double[tempN][tempM];
		for (int i = 0; i < tempM; i++) {
			for (int j = 0; j < tempN; j++) {
				returnArray[j][i] = paraArray[i][j];
			} // Of for j
		} // Of for i

		return returnArray;
	}// Of arrayTranspose

	/**
	 ************
	 * Compute the maximum value of array.
	 * 
	 * @param paraArray The array(double[]).
	 * 
	 * @return returnArray The maximum value of array(double).
	 ************
	 */
	public static double arrayMaximumValue(double[] paraArray) {
		double resultMaximumValue = Double.MIN_VALUE;
		for (int i = 0; i < paraArray.length; i++) {
			if (resultMaximumValue < paraArray[i]) {
				resultMaximumValue = paraArray[i];
			} // Of if
		} // Of for i

		return resultMaximumValue;
	}// Of arrayMaximumValue

	/**
	 ************
	 * Compute the maximum value of array.
	 * 
	 * @param paraArray The array(double[]).
	 * 
	 * @return returnArray The maximum value of array(double).
	 ************
	 */
	public static double arrayMinimumValue(double[] paraArray) {
		double resultMinimumValue = Double.MAX_VALUE;
		for (int i = 0; i < paraArray.length; i++) {
			if (resultMinimumValue > paraArray[i]) {
				resultMinimumValue = paraArray[i];
			} // Of if
		} // Of for i

		return resultMinimumValue;
	}// Of arrayMinimumValue

	/**
	 ************
	 * Compute the maximum value's index.
	 * 
	 * @param paraArray The array(double[]).
	 * 
	 * @return returnArray The maximum value's index(int).
	 ************
	 */
	public static int arrayMaximumValueIndex(double[] paraArray) {
		int resultIndex = 0;
		double tempMaximumValue = Double.MIN_VALUE;
		for (int i = 0; i < paraArray.length; i++) {
			if (tempMaximumValue < paraArray[i]) {
				tempMaximumValue = paraArray[i];
				resultIndex = i;
			} // Of if
		} // Of for i

		return resultIndex;
	}// Of arrayMaximumValueIndex

	/**
	 ************
	 * Compute the maximum value of value.
	 * 
	 * @param paraArray The array(double[]).
	 * 
	 * @return returnArray The maximum value of array(int).
	 ************
	 */
	public static int arrayMinimumValueIndex(double[] paraArray) {
		int resultIndex = 0;
		double resultMinimumValue = Double.MAX_VALUE;
		for (int i = 0; i < paraArray.length; i++) {
			if (resultMinimumValue > paraArray[i]) {
				resultMinimumValue = paraArray[i];
				resultIndex = i;
			} // Of if
		} // Of for i

		return resultIndex;
	}// Of arrayMinimumValueIndex

	/**
	 ************
	 * Get the elements of the corresponding column of the incoming array index I.
	 * 
	 * @param paraArray The import array(double[][]).
	 * @param paraI     The index i(int).
	 * @return The column elements corresponding index i(double[]).
	 ************
	 */
	public static double[] arrayColumnValue(double[][] paraArray, int paraI) {
		int tempN = paraArray.length;
		double[] returnColumnValue = new double[tempN];

		for (int i = 0; i < tempN; i++) {
			returnColumnValue[i] = paraArray[i][paraI];
		} // Of for i

		return returnColumnValue;
	}

	/**
	 ************
	 * Get the elements of the corresponding row of the incoming array.
	 * 
	 * @param paraArray The import array(double[][]).
	 * @param paraIndex The index i(int[]).
	 * @return The row elements corresponding incoming array(doubel[][]).
	 ************
	 */
	public static double[][] arrayRowValue(double[][] paraArray, int[] paraIndex) {
		int tempParaIndex = paraIndex.length;
		double[][] returnRowValue = new double[tempParaIndex][];

		for (int i = 0; i < returnRowValue.length; i++) {
			returnRowValue[i] = paraArray[paraIndex[i]];
		} // Of for i

		return returnRowValue;
	}// Of arrayRowValue

	/**
	 ************
	 * Get the elements of the corresponding row of the incoming array.
	 * 
	 * @param paraArray The import array(double[]).
	 * @param paraIndex The index i(int[]).
	 * @return The row elements corresponding incoming array(double[]).
	 ************
	 */
	public static double[] arrayRowValue(double[] paraArray, int[] paraIndex) {
		int tempParaIndex = paraIndex.length;
		double[] returnRowValue = new double[tempParaIndex];

		for (int i = 0; i < returnRowValue.length; i++) {
			returnRowValue[i] = paraArray[paraIndex[i]];
		} // Of for i

		return returnRowValue;
	}// Of arrayRowValue

	/**
	 ************
	 * Get the elements of between paraStart and paraEnd.
	 * 
	 * @param paraArray The import array(int[]).
	 * @param paraStart The start index(int).
	 * @param paraEnd   The end index(int).
	 * @return The row elements corresponding incoming array(int[]).
	 ************
	 */
	public static int[] arrayRowValue(int[] paraArray, int paraStart, int paraEnd) {
		int tempResultArrayLen = paraEnd - paraStart;
		int[] resultArray = new int[tempResultArrayLen];
		for (int i = paraStart; i < paraEnd; i++) {
			resultArray[i - paraStart] = paraArray[i];
		} // Of for i
		return resultArray;
	}// Of arrayRowValueNot

	/**
	 ************
	 * Get the elements of between paraStart and paraEnd.
	 * 
	 * @param paraArray The import array(char[]).
	 * @param paraStart The start index(int).
	 * @param paraEnd   The end index(int).
	 * @return The row elements corresponding incoming array(char[]).
	 ************
	 */
	public static char[] arrayRowValue(char[] paraArray, int paraStart, int paraEnd) {
		int tempResultArrayLen = paraEnd - paraStart;
		char[] resultArray = new char[tempResultArrayLen];
		for (int i = paraStart; i < paraEnd; i++) {
			resultArray[i - paraStart] = paraArray[i];
		} // Of for i
		return resultArray;
	}// Of arrayRowValueNot

	/**
	 ************
	 * Get the elements of between paraStart and paraEnd.
	 * 
	 * @param paraArray The import array(double[]).
	 * @param paraStart The start index(int).
	 * @param paraEnd   The end index(int).
	 * @return The row elements corresponding incoming array(double[]).
	 ************
	 */
	public static double[] arrayRowValue(double[] paraArray, int paraStart, int paraEnd) {
		int tempResultArrayLen = paraEnd - paraStart;
		double[] resultArray = new double[tempResultArrayLen];
		for (int i = paraStart; i < paraEnd; i++) {
			resultArray[i - paraStart] = paraArray[i];
		} // Of for i
		return resultArray;
	}// Of arrayRowValueNot

	/**
	 ************
	 * Get the elements of not between paraStart and paraEnd.
	 * 
	 * @param paraArray The import array(int[]).
	 * @param paraStart The start index(int).
	 * @param paraEnd   The end index(int).
	 * @return The row elements corresponding incoming array(int[]).
	 ************
	 */
	public static int[] arrayRowValueNot(int[] paraArray, int paraStart, int paraEnd) {
		int tempResultArrayLen = paraArray.length - (paraEnd - paraStart);
		int[] resultArray = new int[tempResultArrayLen];
		for (int i = 0; i < paraStart; i++) {
			resultArray[i] = paraArray[i];
		} // Of for i

		for (int i = paraStart; i < tempResultArrayLen; i++) {
			resultArray[i] = paraArray[i + (paraEnd - paraStart)];
		} // Of for i
		return resultArray;
	}// Of arrayRowValueNot

	/**
	 ************
	 * Get the index of array.
	 * 
	 * @param paraLen The length of array(int).
	 * @return The index of array(int[]).
	 ************
	 */
	public static int[] arrayIndexAuto(int paraLen) {
		int[] returnArray = new int[paraLen];
		for (int i = 0; i < paraLen; i++) {
			returnArray[i] = i;
		} /// Of for i
		return returnArray;
	}// Of arrayIndexAuto

	/**
	 ************
	 * Get the index between paraStart and paraEnd of array.
	 * 
	 * @param paraStrat The start index(int).
	 * @param paraEnd   The end index(int).
	 * @return The index of array(int[]).
	 ************
	 */
	public static int[] arrayIndexAuto(int paraStrat, int paraEnd) {
		int[] returnArray = new int[paraEnd - paraStrat];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = i + paraStrat;
		} // Of for i
		return returnArray;
	}// Of arrayIndexAuto

	public static int[] arraySort(double[] arr) {
		double temp;
		int index;
		int k = arr.length;
		int[] Index = new int[k];
		for (int i = 0; i < k; i++) {
			Index[i] = i;
		}

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length - i - 1; j++) {
				if (arr[j] < arr[j + 1]) {
					temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;

					index = Index[j];
					Index[j] = Index[j + 1];
					Index[j + 1] = index;
				}
			}
		}
		return Index;
	}

	/**
	 ************
	 * Array append element.
	 * 
	 * @param paraArray The array(int[]).
	 * @param paraValue The appended element(int).
	 * @return The array(int[]).
	 ************
	 */
	public static int[] arrayAppendElement(int[] paraArray, int paraValue) {
		int tempM = paraArray.length;
		int[] returnArray = new int[tempM + 1];
		for (int i = 0; i < tempM; i++) {
			returnArray[i] = paraArray[i];
		} // Of for i
		returnArray[tempM] = paraValue;
		return returnArray;
	}// Of appendElement

	/**
	 ************
	 * Array append element.
	 * 
	 * @param paraArray The array(double[]).
	 * @param paraValue The appended element(double).
	 * @return The array(double[]).
	 ************
	 */
	public static double[] arrayAppendElement(double[] paraArray, double paraValue) {
		int tempM = paraArray.length;
		double[] returnArray = new double[tempM + 1];
		for (int i = 0; i < tempM; i++) {
			returnArray[i] = paraArray[i];
		}
		returnArray[tempM] = paraValue;
		return returnArray;
	}// Of appendElement

	/**
	 ************
	 * Copy array.
	 * 
	 * @param paraArray The array(double[][]).
	 * @return The copied array(double[][]).
	 ************
	 */
	public static double[][] arrayCopy(double[][] paraArray) {
		int tempM = paraArray.length;
		double[][] resultArray = new double[tempM][];
		for (int i = 0; i < tempM; i++) {
			resultArray[i] = paraArray[i];
		} // Of for i
		return resultArray;
	}// Of arrayCopy

	/**
	 ************
	 * Copy array.
	 * 
	 * @param paraArray The array(double[]).
	 * @return The copied array(double[]).
	 ************
	 */
	public static double[] arrayCopy(double[] paraArray) {
		int tempM = paraArray.length;
		double[] returnCopyArray = new double[tempM];
		for (int i = 0; i < tempM; i++) {
			returnCopyArray[i] = paraArray[i];
		} // Of for i
		return returnCopyArray;
	}// Of arrayCopy

	/**
	 ************
	 * Copy array.
	 * 
	 * @param paraArray The array(int[]).
	 * @return The copied array(int[]).
	 ************
	 */
	public static int[] arrayCopy(int[] paraArray) {
		int tempM = paraArray.length;
		int[] returnCopyArray = new int[tempM];
		for (int i = 0; i < tempM; i++) {
			returnCopyArray[i] = paraArray[i];
		} // Of for i
		return returnCopyArray;
	}// Of arrayCopy

	/**
	 ************
	 * Reverse array.
	 * 
	 * @param paraArray The array(double[]).
	 * @return The reversed array(double[]).
	 ************
	 */
	public static double[] arrayReverse(double[] paraArray) {
		int tempM = paraArray.length;
		double[] returnReverseArray = new double[tempM];
		for (int i = 0; i < tempM; i++) {
			returnReverseArray[tempM - i - 1] = paraArray[i];
		} // Of for i
		return returnReverseArray;
	}// Of arrayCopy

	/**
	 ************
	 * Reverse array.
	 * 
	 * @param paraArray The array(int[]).
	 * @return The reversed array(int[]).
	 ************
	 */
	public static int[] arrayReverse(int[] paraArray) {
		int tempM = paraArray.length;
		int[] returnReverseArray = new int[tempM];
		for (int i = 0; i < tempM; i++) {
			returnReverseArray[tempM - i - 1] = paraArray[i];
		} // Of for i
		return returnReverseArray;
	}// Of arrayCopy

	/**
	 ************
	 * Reverse array.
	 * 
	 * @param paraArray The array(int[]).
	 ************
	 */
	private static void arrayReverseReturnVoid(int[] paraArray) {
		int tempM = paraArray.length;
		int tempValue;

		for (int i = 0; i < tempM / 2; i++) {
			tempValue = paraArray[i];
			paraArray[i] = paraArray[tempM - i - 1];
			paraArray[tempM - i - 1] = tempValue;
		} // Of for i
	}// Of arrayCopy

	/**
	 ************
	 * Reverse array.
	 * 
	 * @param paraArray The array(int[]).
	 ************
	 */
	private static void arrayReverseReturnVoid(double[] paraArray) {
		int tempM = paraArray.length;
		double tempValue;

		for (int i = 0; i < tempM / 2; i++) {
			tempValue = paraArray[i];
			paraArray[i] = paraArray[tempM - i - 1];
			paraArray[tempM - i - 1] = tempValue;
		} // Of for i
	}// Of arrayReverseReturnVoid

	/**
	 ************
	 * Get the index corresponding element.
	 * 
	 * @param paraArray The given array(int[]).
	 * @param paraValue The need finded value.
	 * @return The index corresponding element.
	 ************
	 */
	public static int arrayCorrespondingElementIndex(int[] paraArray, int paraValue) {
		int tempM = paraArray.length;

		for (int i = 0; i < tempM; i++) {
			if (paraArray[i] == paraValue) {
				return i;
			} // Of if
		} // Of for i
		return -1;
	}// Of arrayReverseReturnVoid

	/**
	 ************
	 * Get the index corresponding element.
	 * 
	 * @param paraArray The given array(int[]).
	 * @param paraIndex The need finded value.
	 * @return The index corresponding element(int[]).
	 ************
	 */
	public static int[] arrayCorrespondingElementIndex(int[] paraArray, int[] paraIndex) {
		int tempLen = paraIndex.length;
		int[] returnArray = new int[tempLen];

		for (int i = 0; i < tempLen; i++) {
			returnArray[i] = paraArray[paraIndex[i]];
		} // Of for i

		return returnArray;
	}// Of arrayCorrespondingElementIndex

	/**
	 ************
	 * Get the index corresponding element.
	 * 
	 * @param paraFirstArray  The first given array(int[]).
	 * @param paraSecondArray The second given array(int[]).
	 * @return The merged array(int[]).
	 ************
	 */
	public static int[] arrayMerge(int[] paraFirstArray, int[] paraSecondArray) {
		int tempFirstArrayLen = paraFirstArray.length;
		int tempSecondArrayLen = paraSecondArray.length;

		int[] resultArray = new int[tempFirstArrayLen + tempSecondArrayLen];

		for (int i = 0; i < tempFirstArrayLen; i++) {
			resultArray[i] = paraFirstArray[i];
		} // Of for i

		for (int i = tempFirstArrayLen; i < resultArray.length; i++) {
			resultArray[i] = paraSecondArray[i - tempFirstArrayLen];
		} // Of for i
		return resultArray;
	}// Of

	/**
	 ************
	 * Compute the mean of array.
	 * 
	 * @param paraDataBlock The given block(double[][]).
	 * @param paraAxis      Compute the mean of array's volume if paraAxis equal 0
	 *                      else line.(int).
	 * @return The mean of array volume or line(double[]).
	 ************
	 */
	public static double[] arrayMean(double[][] paraDataBlock, int paraAxis) {
		int tempM = paraDataBlock.length;
		int tempN = paraDataBlock[0].length;
		if (paraAxis == 0) {
			double[] resultMeanArray = new double[tempN];
			for (int i = 0; i < tempN; i++) {
				resultMeanArray[i] = arrayAdd(arrayColumnValue(paraDataBlock, i)) / tempM;
			} // Of for i
			return resultMeanArray;
		} else {
			double[] resultMeanArray = new double[tempM];
			for (int i = 0; i < tempM; i++) {
				resultMeanArray[i] = arrayAdd(paraDataBlock[i]) / tempN;
			} // Of for i
			return resultMeanArray;
		} // Of if
	}// Of arrayMean

	/**
	 ************
	 * Compute standard deviation.
	 * 
	 * @param paraList The list(double[]).
	 * @return The standard deviation(double).
	 ************
	 */
	public static double computeStandardDeviation(double[] paraList) {
		// ����s^2=[(x1-x)^2 +...(xn-x)^2]/n
		int tempM = paraList.length;

		double tempSum = 0;
		for (int i = 0; i < tempM; i++) {// ���
			tempSum += paraList[i];
		} // Of for i

		double tempAve = tempSum / tempM;// ��ƽ��ֵ
		double tempVar = 0;
		for (int i = 0; i < tempM; i++) {// �󷽲�
			tempVar += (paraList[i] - tempAve) * (paraList[i] - tempAve);
		} // Of for i
		return Math.sqrt(tempVar / tempM);
	}// Of StandardDeviation

	/**
	 ************
	 * Compute the non-zero index.
	 * 
	 * @param paraArray The array(double[]).
	 * @return The index of non-zero(int[]).
	 ************
	 */
	public static int[] computeNonZeroIndex(double[] paraArray) {
		int tempM = paraArray.length;

		int[] tempNonZeroIndex = new int[tempM];
		for (int i = 0; i < tempM; i++) {
			if (paraArray[i] == 0) {
				tempNonZeroIndex[i] = -1;
				continue;
			} // Of if
			tempNonZeroIndex[i] = i;
		} // Of for i

		int resultNonZeroIndexLen = 0;
		for (int i = 0; i < tempM; i++) {
			if (tempNonZeroIndex[i] != -1) {
				resultNonZeroIndexLen++;
			} // Of if
		} // Of for i

		int[] resultNonZeroIndex = new int[resultNonZeroIndexLen];
		int j = 0;
		for (int i = 0; i < tempM; i++) {
			if (tempNonZeroIndex[i] != -1) {
				resultNonZeroIndex[j++] = tempNonZeroIndex[i];
			} // Of if
		} // Of for i

		return resultNonZeroIndex;
	}// Of computeNonZeroIndex

	/**
	 ************
	 * Compute the fixed interval index.
	 * 
	 * @param paraArray   The array(double[]).
	 * @param paraLowest  The lowest bound(double).
	 * @param paraHighest The highest bound(double).
	 * @return The fixed interval index(int[]).
	 ************
	 */
	public static int[] computeFixedIntervalIndex(double[] paraArray, double paraLowest, double paraHighest) {
		if (paraLowest > paraHighest) {
			double tempValue = paraHighest;
			paraHighest = paraLowest;
			paraLowest = tempValue;
		} // Of if

		int tempM = paraArray.length;

		int[] tempIndex = new int[tempM];
		for (int i = 0; i < tempM; i++) {
			if (!(paraArray[i] > paraLowest && paraArray[i] < paraHighest)) {
				tempIndex[i] = -1;
				continue;
			} // Of if
			tempIndex[i] = i;
		} // Of for i

		int resultIndexLen = 0;
		for (int i = 0; i < tempM; i++) {
			if (tempIndex[i] != -1) {
				resultIndexLen++;
			} // Of if
		} // Of for i

		int[] returnIndex = new int[resultIndexLen];
		int j = 0;
		for (int i = 0; i < tempM; i++) {
			if (tempIndex[i] != -1) {
				returnIndex[j++] = tempIndex[i];
			} // Of if
		} // Of for i

		return returnIndex;
	}// Of computeFixedIntervalIndex

	/**
	 ************
	 * Delete the element in paraBlock.
	 * 
	 * @param paraBlock      The initial block(int[]).
	 * @param deletedElement The element to be delete(int).
	 * @return Block of deleted element(int[]).
	 ************
	 */
	public static int[] deleteElement(int[] paraBlock, int deletedElement) {
		int[] resultBlock = new int[paraBlock.length - 1];

		int tempIndex = 0;
		for (int i = 0; i < paraBlock.length; i++) {
			if (paraBlock[i] == deletedElement) {
				tempIndex = i;
			}
		} // Of for i

		for (int i = 0; i < tempIndex; i++) {
			resultBlock[i] = paraBlock[i];
		} // Of for i

		for (int i = tempIndex + 1; i < paraBlock.length; i++) {
			resultBlock[i - 1] = paraBlock[i];
		} // Of for i
		return resultBlock;
	}// Of deleteElement

	/**
	 ************
	 * Delete the element in paraBlock.
	 * 
	 * @param paraBlock      The initial block(double[]).
	 * @param deletedElement The element to be delete(int).
	 * @return Block of deleted element(double[]).
	 ************
	 */
	public static double[] deleteElement(double[] paraBlock, int deletedIndex) {
		double[] resultBlock = new double[paraBlock.length - 1];

		for (int i = 0; i < deletedIndex; i++) {
			resultBlock[i] = paraBlock[i];
		} // Of for i

		for (int i = deletedIndex + 1; i < paraBlock.length; i++) {
			resultBlock[i - 1] = paraBlock[i];
		} // Of for i
		return resultBlock;
	}// Of deleteElement

	/**
	 *************
	 * Only can load arff data.
	 * 
	 * @param paraFilename The arff filename.
	 * @return The data.
	 ************* 
	 */
	public static Instances fileReadArff(String paraFilename) {
		Instances tempData = null;
		try {
			FileReader fileReader = new FileReader(paraFilename);
			tempData = new Instances(fileReader);
			fileReader.close();
		} catch (Exception ee) {
			System.out.println("Cannot read the file: " + paraFilename + "\r\n" + ee);
			System.exit(0);
		} // Of try

		return tempData;
	}// Of fileReadArff

	/**
	 *************
	 * Read file.
	 * 
	 *
	 * @param paraSplit The division method(String).
	 * @return The string of file(double[])
	 *************
	 */
	public static String[][] fileRead(String paraFilename, String paraSplit) {

		int tempLen = 0;
		try (FileReader tempReader = new FileReader(paraFilename); 
				BufferedReader tempBr = new BufferedReader(tempReader) // ����һ�����������ļ�����ת�ɼ�����ܶ���������
		) {
			@SuppressWarnings("unused")
			String tempLine;
			while ((tempLine = tempBr.readLine()) != null) {
				tempLen++;
			}//Of while
		} catch (IOException e) {
			e.printStackTrace();
		} // Of try
		
		String[][] resultStrings = new String[tempLen][];
		try (FileReader tempReader = new FileReader(paraFilename); 
				BufferedReader tempBr = new BufferedReader(tempReader) // ����һ�����������ļ�����ת�ɼ�����ܶ���������
		) {
			String tempLine;
			int i = 0;
			while ((tempLine = tempBr.readLine()) != null) {
				tempLen++;
				resultStrings[i] = tempLine.split(paraSplit);
				i++;
			}//Of while
		} catch (IOException e) {
			e.printStackTrace();
		} // Of try

		return resultStrings;
	}// Of fileRead

	/**
	 *********************** 
	 * Generate a random sequence ranging from 0 to n-1.
	 * 
	 * @param paraNumber The given number(int).
	 * @return The random sequence.
	 *********************** 
	 */
	public static int[] generateRandomSequence(int paraNumber) {
		// Step 1. Initialize.
		int[] resultSequence = arrayIndexAuto(paraNumber);

		// Step 2. Randomize.
		for (int i = 0; i < paraNumber * 10; i++) {
			int tempFirstIndex = random.nextInt(paraNumber);
			int tempSecondIndex = random.nextInt(paraNumber);

			// Swap.
			int tempValue = resultSequence[tempFirstIndex];
			resultSequence[tempFirstIndex] = resultSequence[tempSecondIndex];
			resultSequence[tempSecondIndex] = tempValue;
		} // Of for i

		tracingMiddleResult("The result sequence is: " + Arrays.toString(resultSequence));
		return resultSequence;
	}// Of generateRandomSequence

	/**
	 *********************** 
	 * Generate a random sequence ranging derived paraBlock.
	 * 
	 * @param paraBlock The given block(int[]).
	 * @return The random sequence.
	 *********************** 
	 */
	public static int[] generateRandomSequence(int[] paraBlock) {
		int tempM = paraBlock.length;

		for (int i = 0; i < tempM * 10; i++) {
			int tempFirstIndex = random.nextInt(tempM);
			int tempSecondIndex = random.nextInt(tempM);

			// Swap.
			int tempValue = paraBlock[tempFirstIndex];
			paraBlock[tempFirstIndex] = paraBlock[tempSecondIndex];
			paraBlock[tempSecondIndex] = tempValue;
		} // Of for i

		tracingMiddleResult("The result sequence is: " + Arrays.toString(paraBlock));
		return paraBlock;
	}// Of generateRandomSequence

	/**
	 ********************************* 
	 * GB2312 to UNICODE. Use this one if Chinese characters is a mess.
	 * 
	 * @param paraString a GB2312 string
	 * @return a UNICODE string.
	 * @see #UNICODEToGB2312(String)
	 ********************************* 
	 */
	public static String GB2312ToUNICODE(String paraString) {
		char[] tempCharArray = paraString.toCharArray();
		int tempLength = tempCharArray.length;
		byte[] tempByteArray = new byte[tempLength];
		for (int i = 0; i < tempLength; i++) {
			tempByteArray[i] = (byte) tempCharArray[i];
		} // Of for.

		String returnString = new String(tempByteArray);
		return returnString;
	}// Of GB2312ToUNICODE.

	/**
	 ************
	 * Are double matrices equal?
	 * 
	 * @param paraMatrix1 The matrix of first(int[]).
	 * @param paraMatrix2 The matrix of second(int[]).
	 * @return True or false about matrices equal(boolean).
	 ************
	 */
	public static boolean isDoubleMatricesEqual(int[] paraMatrix1, int[] paraMatrix2) {
		if (paraMatrix1 == null) {
			return false;
		} // Of if

		if (paraMatrix2 == null) {
			return false;
		} // Of if

		if (paraMatrix1.length != paraMatrix2.length) {
			return false;
		} // Of if

		for (int i = 0; i < paraMatrix1.length; i++) {
			if (paraMatrix1[i] != paraMatrix2[i]) {
				return false;
			} // Of if
		} // Of for i

		return true;
	}// Of doubleMatricesEqual

	/**
	 *************************** 
	 * Judge whether or not the given string is null/empty.
	 * 
	 * @param paraString The given string.
	 * @return True if it is null or empty.
	 *************************** 
	 */
	public static boolean isEmptyStr(String paraString) {
		if (paraString == null)
			return true;
		if (paraString.equals(""))
			return true;
		return false;
	}// Of isEmptyStr

	/**
	 ************
	 * The kernel function of RBF.
	 * 
	 * @param paraFirstBlock  The first block(double[][]).
	 * @param paraSecondBlock The second block(double[]).
	 * @param paraKernel      The kernel(double).
	 * @return The RBF kernel(double[]).
	 ************
	 */
	public static double[] kernelRBF(double[][] paraFirstBlock, double[] paraSecondBlock, double paraKernel) {
		int tempM = paraFirstBlock.length;
		double[] resultKernelTrans = new double[tempM];
		double[] tempDeltaRow = new double[tempM];

		for (int i = 0; i < tempM; i++) {
			tempDeltaRow = arrayMinus(paraFirstBlock[i], paraSecondBlock);
			resultKernelTrans[i] = arrayMultiplyAndAdd(tempDeltaRow, tempDeltaRow);
			resultKernelTrans[i] = Math.exp(resultKernelTrans[i] / (-1 * paraKernel * paraKernel));
		} // Of for i

		return resultKernelTrans;
	}// Of kernelRBF

	/**
	 ************
	 * The kernel function.
	 * 
	 * @param paraFirstBlock  The first block(double[][]).
	 * @param paraSecondBlock The second block(double[]).
	 * @return The linear kernel(double[]).
	 ************
	 */
	public static double[] kernelLinear(double[][] paraFirstBlock, double[] paraSecondBlock) {

		int tempM = paraFirstBlock.length;
		double[] resultKernelTrans = new double[tempM];

		for (int i = 0; i < tempM; i++) {
			resultKernelTrans[i] = arrayMultiplyAndAdd(paraFirstBlock[i], paraSecondBlock);
		} // Of for i

		return resultKernelTrans;
	}// Of kernelFunction

	/**
	 ************
	 * The kernel function of gaussian.
	 * 
	 * @param paraArray  The given array(double[]).
	 * @param paraKernel The kernel(double).
	 * @return The gaussian kernel(double[]).
	 ************
	 */
	public static double kernelGaussian(double[] paraArray, double paraKernel) {
		return Math.exp(arrayMultiplyAndAdd(paraArray) / (-2 * paraKernel * paraKernel));
	}// Of kernelGaussian

	/**
	 ************
	 * Compute the log_value(base)?
	 * 
	 * @param value The computed number(double).
	 * @param base  The base number(int).
	 * @return The log(double).
	 ************
	 */
	static public double logBase(double value, int base) {
		return Math.log(value) / Math.log(base);
	}// Of log

	/**
	 ************
	 * Compute the cov of matrix based row.
	 * 
	 * @param paraMatrix The given matrix(double[][]).
	 * @param paraAxis   Row or line.
	 * 
	 * @return The cov of matrix(double[][]).
	 ************
	 */
	public static double[][] matrixCov(double[][] paraMatrix, int paraAxis) {
		double[] tempMeanVolumeData = arrayMean(paraMatrix, 0);
		double[][] tempMeanData = arrayMinus(paraMatrix, tempMeanVolumeData);
		int tempM = tempMeanData.length;
		int tempN = tempMeanData[0].length;

		if (paraAxis == 0) {
			double[][] resultMatrix = new double[tempN][tempN];
			for (int i = 0; i < tempN; i++) {
				for (int j = 0; j < tempN; j++) {
					resultMatrix[i][j] = arrayMultiplyAndAdd(arrayColumnValue(tempMeanData, i),
							arrayColumnValue(tempMeanData, j)) / (tempM - 1);
				} // Of for j
			} // Of for i
			return resultMatrix;
		} else {

			double[][] resultMatrix = new double[tempM][tempM];
			for (int i = 0; i < tempM; i++) {
				for (int j = 0; j < tempM; j++) {
					resultMatrix[i][j] = arrayMultiplyAndAdd(tempMeanData[i], tempMeanData[j]) / (tempN - 1);
				} // Of for j
			} // Of for i
			return resultMatrix;
		} // Of if
	}// Of matrixCov

	/**
	 *************
	 * The algorithm of matrixPca. Used to dimensionality reduction.
	 * 
	 * @param paraMatrix The given matrix.
	 * @return The pca of matrix.
	 ************* 
	 */
	public static double[][] matrixPca(double[][] paraMatrix) {
		int topNFeat = 3;
		return matrixPca(paraMatrix, topNFeat);
	}// Of matrixPca

	/**
	 *************
	 * The algorithm of matrixPca. Used to dimensionality reduction.
	 * 
	 * @param paraMatrix The given matrix.
	 * @return The pca of matrix.
	 ************* 
	 */
	public static double[][] matrixPca(double[][] paraMatrix, int paraTopNFeat) {
		double[][] tempmatrixCovMatrix = matrixCov(paraMatrix, 0);
		double[][] tempEigMatrix = matrixEigValue(tempmatrixCovMatrix, 100);
		double[] tempEigValue = new double[tempEigMatrix.length];
		for (int i = 0; i < tempEigValue.length; i++) {
			tempEigValue[i] = tempEigMatrix[i][i];
		} // Of for i
//		double[][] tempEigVector = matrixEigVector(tempmatrixCovMatrix, 100);

		return null;
	}// Of matrixPca

	/**
	 ************
	 * The determinant of matrix.����������ʽ
	 * 
	 * @param paraMatrix The given matrix(double[][]).
	 * 
	 * @return The answer of determinant(double).
	 ************
	 */
	public static double matrixDeterminant(double[][] paraMatrix) {
		int tempM = paraMatrix.length;
		int tempN = paraMatrix[0].length;

		if (tempM == 1) {
			return paraMatrix[0][0];
		} else if (tempM == 2) {
			return paraMatrix[0][0] * paraMatrix[1][1] - paraMatrix[1][0] * paraMatrix[0][1];
		} // Of if

		double resultValue = 0;
		int k;
		for (int i = 0; i < tempN; i++) {
			double[][] tempMatrix = new double[tempM - 1][tempN - 1];
			for (int j = 0; j < tempM - 1; j++) {
				k = 0;
				while (k < tempN - 1) {
					if (k < i) {
						tempMatrix[j][k] = paraMatrix[j + 1][k];
					} else {
						tempMatrix[j][k] = paraMatrix[j + 1][k + 1];
					} // Of if
					k++;
				} // Of while
			} // Of for j
			resultValue += (Math.pow(-1., i)) * paraMatrix[0][i] * matrixDeterminant(tempMatrix);
		} // Of for i

		return resultValue;
	}// Of matrixDeterminant

	/**
	 ************
	 * The gram schimidt of matrix.������������
	 * 
	 * @param paraMatrix The given matrix(double[][]).
	 * 
	 * @return The gram schimidt of matrix.
	 ************
	 */
	public static double[][] matrixGramSchimidt(double[][] paraMatrix) {
		if (paraMatrix == null) {
			return null;
		} // Of if

		double[][] tempTransposedMatrix = arrayTranspose(paraMatrix);
		int tempM = tempTransposedMatrix.length;
		int tempN = tempTransposedMatrix[0].length;

		double[][] resultMatrix = new double[tempM][tempN];
		double tempValue = 0;
		double tempFactor = 0;
		for (int i = 0; i < tempM; i++) {
			for (int j = 0; j < tempN; j++) {
				tempValue = tempTransposedMatrix[i][j];
				for (int k = 0; k < i; k++) {
					tempFactor = (1. * arrayMultiplyAndAdd(tempTransposedMatrix[i], resultMatrix[k]))
							/ arrayMultiplyAndAdd(resultMatrix[k], resultMatrix[k]);
					tempValue -= tempFactor * resultMatrix[k][j];
				} // Of for k
				resultMatrix[i][j] = tempValue;
			} // Of for j
		} // Of for i

		return arrayTranspose(resultMatrix);
	}// Of matrixGramSchimidt

	/**
	 ************
	 * The inverse of matrix.��������
	 * 
	 * @param paraMatrix The given matrix(double[][]).
	 * 
	 * @return The inverse of matrix(double[][]).
	 ************
	 */
	public static double[][] matrixInverse(double[][] paraMatrix) {
		if (paraMatrix == null) {
			return null;
		} // Of if

		// Make sure that this matrix is invertible.
		if (matrixDeterminant(paraMatrix) == 0) {
			System.out.println("Fetal error, the matrix can not be invertible.");
			System.exit(0);
		} // Of if

		int tempM = paraMatrix.length;
		int tempN = paraMatrix[0].length * 2;

		double[][] tempCopyMatrix = new double[tempM][tempN];

		for (int i = 0; i < tempM; i++) {
			for (int j = 0; j < tempN; j++) {
				if (j < tempM) {
					tempCopyMatrix[i][j] = paraMatrix[i][j];
				} else {
					if (j == i + tempM) {
						tempCopyMatrix[i][j] = 1;
					} // Of if
				} // Of if
			} // Of for j
		} // Of for i
		System.err.println(Arrays.deepToString(tempCopyMatrix));

		double tempTimes = 0;
		for (int i = 0; i < tempM; i++) {
			if (tempCopyMatrix[i][i] == 0) {
				int j;
				for (j = i + 1; j < tempM; j++) {
					if (tempCopyMatrix[j][i] != 0) {
						break;
					} // Of if
				} // Of for j

				if (j != i + 1) {
					for (int k = 0; k < tempN; k++) {
						double temppVlaue = tempCopyMatrix[i][k];
						tempCopyMatrix[i][k] = tempCopyMatrix[j][k];
						tempCopyMatrix[j][k] = temppVlaue;
					} // Of for k
				} // Of if
			} // Of if

			for (int j = i + 1; j < tempM; j++) {
				if (tempCopyMatrix[j][i] != 0) {
					tempTimes = (tempCopyMatrix[j][i]) / tempCopyMatrix[i][i];
					for (int k = i; k < tempN; k++) {
						tempCopyMatrix[j][k] /= tempTimes;
						tempCopyMatrix[j][k] -= tempCopyMatrix[i][k];
					} // Of for k
				} // Of if
			} // Of for j
		} // Of for i

		for (int i = 0; i < tempM; i++) {
			for (int j = i + 1; j < tempN / 2.; j++) {
				if (tempCopyMatrix[i][j] != 0) {
					tempTimes = tempCopyMatrix[i][j] / tempCopyMatrix[j][j];
					for (int k = j; k < tempN; k++) {
						tempCopyMatrix[i][k] -= tempTimes * tempCopyMatrix[j][k];
					} // Of for k
				} // Of if
			} // Of for j
		} // Of for i

		for (int i = 0; i < tempM; i++) {
			tempTimes = tempCopyMatrix[i][i];
			for (int j = 0; j < tempN; j++) {
				tempCopyMatrix[i][j] /= tempTimes;
			} // Of for j
		} // Of for i

		double[][] resultMatrix = new double[tempM][tempN / 2];
		for (int i = 0; i < tempM; i++) {
			for (int j = 0; j < tempN / 2; j++) {
				resultMatrix[i][j] = tempCopyMatrix[i][j + tempN / 2];
			} // Of for j
		} // Of for i

		return resultMatrix;
	}// Of matrixInverse

	/**
	 ************
	 * The QR-decomposition of matrix.����QR�ֽ�
	 * 
	 * @param paraMatrix  The given matrix(double[][]).
	 * @param paraMatrixQ The given matrix, the size echo paraMatrix(double[][]).
	 ************
	 */
	public static double[][] matrixQrDecomposition(double[][] paraMatrix) {
		double[][] tempOrthogonalMatrix = arrayTranspose(matrixGramSchimidt(paraMatrix));
		int tempM = tempOrthogonalMatrix.length;
		int tempN = tempOrthogonalMatrix[0].length;

		double[][] tempMatrixQ = new double[tempM][tempN];
		for (int i = 0; i < tempM; i++) {
			double tempMag = magnitude(tempOrthogonalMatrix[i]);
			for (int j = 0; j < tempN; j++) {
				tempMatrixQ[i][j] = tempOrthogonalMatrix[i][j] / tempMag;
			} // Of for j
		} // Of for i

		double[][] tempMatrixR = matrixMultiply(tempMatrixQ, paraMatrix);
		double[][] resultSummary = new double[tempM + tempN][tempM];
		for (int i = 0; i < tempN; i++) {
			for (int j = 0; j < tempM; j++) {
				resultSummary[i][j] = tempMatrixQ[j][i];
			} // Of for j
		} // Of for i

		for (int i = tempN; i < resultSummary.length; i++) {
			for (int j = 0; j < tempM; j++) {
				resultSummary[i][j] = tempMatrixR[i - tempN][j];
			} // Of for j
		} // Of for i

		return resultSummary;
	}// Of matrixQrDecomposition

	/**
	 ************
	 * The magnitude.
	 * 
	 * @param paraMatrix The given matrix(double[]).
	 * @return The answer.
	 ************
	 */
	private static double magnitude(double[] paraMatrix) {
		return Math.sqrt(arrayMultiplyAndAdd(paraMatrix, paraMatrix));
	}// Of magnitude

	/**
	 ************
	 * The matrix multiply.
	 * 
	 * @param paraFirstMatrix  The given first matrix(double[][]).
	 * @param paraSecondMatrix The given second matrix(double[][]).
	 * @return The answer(double[][]).
	 ************
	 */
	public static double[][] matrixMultiply(double[][] paraFirstMatrix, double[][] paraSecondMatrix) {
		if (paraFirstMatrix == null || paraSecondMatrix == null) {
			return null;
		} // Of if

		if (paraFirstMatrix[0].length != paraSecondMatrix.length) {
			System.out.println("Fetal error: The size of two inputed matrix is illegally in SimpleTool.matrixMultiply(double[][], double[][]).");
			System.exit(0);
		} // Of if

		int tempMa = paraFirstMatrix.length;
		int tempNa = paraFirstMatrix[0].length;
		int tempNb = paraSecondMatrix[0].length;

		double[][] resultMatrix = new double[tempMa][tempNb];
		for (int i = 0; i < tempMa; i++) {
			for (int j = 0; j < tempNb; j++) {
				double tempSum = 0;
				for (int k = 0; k < tempNa; k++) {
					tempSum += paraFirstMatrix[i][k] * paraSecondMatrix[k][j];
				} // Of for k
				resultMatrix[i][j] = tempSum;
			} // Of for j
		} // Of for i
		return resultMatrix;
	}// Of matrixMultiply

	/**
	 ************
	 * The eig of matrix.
	 * 
	 * @param paraMatrix The given matrix(double[][]).
	 * @param paraIter   The maximum iteration.
	 * @return The eig of matrix.
	 ************
	 */
	public static double[][] matrixEigValue(double[][] paraMatrix, int paraIter) {
		int tempM = paraMatrix.length;
		int tempN = paraMatrix[0].length;
		int[] tempIndexQ = arrayIndexAuto(tempM);
		int[] tempIndexR = arrayIndexAuto(tempM, tempM + tempN);
		for (int i = 0; i < paraIter; i++) {
			double[][] tempSummary = matrixQrDecomposition(paraMatrix);
			double[][] tempMatrixQ = arrayRowValue(tempSummary, tempIndexQ);
			double[][] tempMatrixR = arrayRowValue(tempSummary, tempIndexR);
			paraMatrix = matrixMultiply(tempMatrixR, tempMatrixQ);
		} // Of for i

		return paraMatrix;
	}// Of matrixEigValue

	/**
	 ************
	 * The eig vector of matrix.�������������
	 * 
	 * @param paraMatrix The given matrix(double[][]).
	 * @param paraIter   The maximum iteration.
	 * @return The eig vector of matrix.
	 ************
	 */
	public static double[][] matrixEigVector(double[][] paraMatrix, int paraIter) {
		int tempM = paraMatrix.length;
		int tempN = paraMatrix[0].length;
		double[][] tempMatrix = matrixEigValue(arrayCopy(paraMatrix), paraIter);
		for (int i = 0; i < tempM; i++) {
			for (int j = 0; j < tempN; j++) {
				if (i != j) {
					tempMatrix[i][j] = 0;
				} // Of if
			} // Of for j
		} // Of for i

		return matrixInverse(matrixSubtract(paraMatrix, tempMatrix));
	}// Of matrixEigVector

	/**
	 ************
	 * The subtract of matrix.��������
	 * 
	 * @param paraFirstMatrix The first given matrix(double[][]).
	 * @param paraFirstMatrix The second given matrix(double[][]).
	 * @return The subtract of two matrix.
	 ************
	 */
	public static double[][] matrixSubtract(double[][] paraFirstMatrix, double[][] paraSecondMatrix) {
		if (paraFirstMatrix == null || paraSecondMatrix == null) {
			return null;
		} // Of if

		int tempMFirst = paraFirstMatrix.length;
		int tempNFirst = paraFirstMatrix[0].length;
		int tempMSecond = paraSecondMatrix.length;
		int tempNSecond = paraSecondMatrix[0].length;

		if (tempMFirst != tempMSecond || tempNFirst != tempNSecond) {
			System.out.println("Fetal error, the inputed matrix is illegal in matrixSub(double[][], double[][])");
			System.exit(0);
		} // Of if

		double[][] resultMatrix = new double[tempMFirst][tempNFirst];
		for (int i = 0; i < tempMFirst; i++) {
			for (int j = 0; j < tempNFirst; j++) {
				resultMatrix[i][j] = paraFirstMatrix[i][j] - paraSecondMatrix[i][j];
			} // Of for j
		} // Of for i

		return resultMatrix;
	}// Of matrixSubtract

	/**
	 ************
	 * The subtract of matrix.��������
	 * 
	 * @param paraMatrix The given matrix(double[][]).
	 * @return The number of non-zero values in given matrix(int).
	 ************
	 */
	public static int matrixNonZeroValueNumber(double[][] paraMatrix) {
		int resultNumber = 0;
		for (int i = 0; i < paraMatrix.length; i++) {
			for (int j = 0; j < paraMatrix[0].length; j++) {
				if (paraMatrix[i][j] != 0) {
					resultNumber++;
				} // Of if
			} // Of for j
		} // Of for i

		return resultNumber;
	}// Of matrixNonZeroValueNumber

	/**
	 ************
	 * Get the eye matrix.
	 * 
	 * @param paraSize The size of eye matrix(int).
	 * @return The eye matrix(double[][]).
	 ************
	 */
	public static double[][] matrixEye(int paraSize) {
		double[][] resultEyeMatrix = new double[paraSize][paraSize];

		for (int i = 0; i < paraSize; i++) {
			resultEyeMatrix[i][i] = 1;
		} // Of if

		return resultEyeMatrix;
	}// Of matrixEye

	/**
	 ************
	 * Get the index of ascent sorted array and ascent sorted array use quick sort.
	 * 
	 * @param paraList  The given list.
	 * @param paraIndex The initial index of list.
	 * @param paraLeft  The index of left.
	 * @param paraRight The index of right.
	 ************
	 */
	public static void quickSortAscent(double[] paraList, int[] paraIndex, int paraLeft, int paraRight) {
		int[][] tempSummary = new int[2][];
		if (paraLeft < paraRight) {
			tempSummary = division(paraList, paraIndex, paraLeft, paraRight);
			int tempValue = tempSummary[0][0];
			paraIndex = tempSummary[1];
			quickSortAscent(paraList, paraIndex, paraLeft, tempValue - 1);
			quickSortAscent(paraList, paraIndex, tempValue + 1, paraRight);
		} // Of if

	}// Of quickSortAscent

	/**
	 ************
	 * Get the index of descend sorted array and descend sorted array use quick
	 * sort.
	 * 
	 * @param paraList  The given list.
	 * @param paraIndex The initial index of list.
	 * @param paraLeft  The index of left.
	 * @param paraRight The index of right.
	 ************
	 */
	public static void quickSortDescend(double[] paraList, int[] paraIndex, int paraLeft, int paraRight) {
		quickSortAscent(paraList, paraIndex, paraLeft, paraRight);
		arrayReverseReturnVoid(paraList);
		arrayReverseReturnVoid(paraIndex);
	}// Of quickSortAscent

	/**
	 ************
	 * The inner function of quick sort.
	 * 
	 * @param paraList  The given list.
	 * @param paraIndex The initial index of list.
	 * @param paraLeft  The index of left.
	 * @param paraRight The index of right.
	 ************
	 */
	private static int[][] division(double[] paraList, int[] paraIndex, int paraLeft, int paraRight) {
		double tempValue = paraList[paraLeft];
		int tempIndex = paraIndex[paraLeft];
		while (paraLeft < paraRight) {
			while (paraLeft < paraRight && paraList[paraRight] >= tempValue) {
				paraRight--;
			}
			paraList[paraLeft] = paraList[paraRight];
			paraIndex[paraLeft] = paraIndex[paraRight];
			while (paraLeft < paraRight && paraList[paraLeft] <= tempValue) {
				paraLeft++;
			}
			paraList[paraRight] = paraList[paraLeft];
			paraIndex[paraRight] = paraIndex[paraLeft];
		}
		paraList[paraLeft] = tempValue;
		paraIndex[paraLeft] = tempIndex;

		int[][] resultSummary = new int[2][];
		resultSummary[0] = new int[] { paraLeft };
		resultSummary[1] = paraIndex;
		return resultSummary;
	}// Of division

	/**
	 ************
	 * The sign function.
	 * 
	 * @param paraNumber The x of sign(x)(double).
	 * @return sign(x)(double);
	 ************
	 */
	public static double sign(double paraNumber) {
		if (paraNumber >= 0) {
			return 1.;
		} else {
			return -1.;
		} // Of if
	}// Of sign

	/**
	 ************
	 * The sign function.
	 * 
	 * @param paraNumber    The x of sign(x)(double).
	 * @param paraThreshold The threshold.
	 * @return sign(x)(double);
	 ************
	 */
	public static double sign(double paraNumber, double paraThreshold) {
		if (paraNumber >= paraThreshold) {
			return 1.;
		} else {
			return .0;
		} // Of if
	}// Of sign

	/**
	 ************
	 * Progress tracing.
	 * 
	 * @param paraString The printed string(String).
	 ************
	 */
	public static void tracingProgress(String paraString) {
		if (PROGRESS_TRACING) {
			System.out.println(paraString);
		} // Of if
	}// Of tracingProgress

	/**
	 ************
	 * Middle progress tracing.
	 * 
	 * @param paraString The printed string(String).
	 ************
	 */
	public static void tracingMiddleResult(String paraString) {
		if (MIDDLE_RESULT_TRACING) {
			System.out.println(paraString);
		} // Of if
	}// Of tracingMiddleResult

	/**
	 ************
	 * Final progress tracing.
	 * 
	 * @param paraString The printed string(String).
	 ************
	 */
	public static void tracingFinalResult(String paraString) {
		if (FINAL_RESULT_TRACING) {
			System.out.println(paraString);
		} // Of if
	}// Of tracingFinalResult

	/**
	 ************
	 * Predicted error progress tracing.
	 * 
	 * @param paraString The printed string(String).
	 ************
	 */
	public static void tracingPredictedError(String paraString) {
		if (PREDICTED_ERROR_TRACING) {
			System.out.println(paraString);
		} // Of if
	}// Of tracingPredictedError

	/**
	 ************
	 * Predicted error progress tracing.
	 * 
	 * @param paraString The printed string(String).
	 ************
	 */
	public static void tracingStarting(String paraString) {
		if (STARTING_TRACING) {
			System.out.println(paraString);
		} // Of if
	}// Of tracingStarting

	/**
	 ************
	 * The test data1.
	 * 
	 * @return The test data(double[][]).
	 ************
	 */
	public static double[][] testData1() {
		System.out.println(
				"Data from SimpleTool.testData1:\nThe simple binary data set(num-instances: 100; label: 1 or -1).\n");
		ArrayList<ArrayList<Double>> origin_data= FileProcessImpl.read_csv("C:\\Users\\FUBOFENG\\Desktop\\实训1\\Bug-prediction\\springboot\\src\\main\\resources\\static\\JDT.csv",true);
		ArrayList<ArrayList<Double>> all_features=MatrixOperation.converse(MatrixOperation.iloc(origin_data,0,0,origin_data.size()-1,origin_data.get(0).size()-2),true);
//		ArrayList<ArrayList<Double>> all_labels=MatrixOperation.iloc(origin_data,0,origin_data.get(0).size()-1,origin_data.size()-1,origin_data.get(0).size()-1);
		double[][] result= MatrixOperation.conversetoDouble(all_features,false);
		return result;
		//		double[][] returnData = new double[][] { { -0.214824, 0.662756 }, { -0.061569, -0.091875 },
//				{ 0.406933, 0.648055 }, { 0.22365, 0.130142 }, { 0.231317, 0.766906 }, { -0.7488, -0.531637 },
//				{ -0.557789, 0.375797 }, { 0.207123, -0.019463 }, { 0.286462, 0.71947 }, { 0.1953, -0.179039 },
//				{ -0.152696, -0.15303 }, { 0.384471, 0.653336 }, { -0.11728, -0.153217 }, { -0.238076, 0.000583 },
//				{ -0.413576, 0.145681 }, { 0.490767, -0.680029 }, { 0.199894, -0.199381 }, { -0.356048, 0.53796 },
//				{ -0.392868, -0.125261 }, { 0.353588, -0.070617 }, { 0.020984, 0.92572 }, { -0.475167, -0.346247 },
//				{ 0.074952, 0.042783 }, { 0.394164, -0.058217 }, { 0.663418, 0.436525 }, { 0.402158, 0.577744 },
//				{ -0.449349, -0.038074 }, { 0.61908, -0.088188 }, { 0.268066, -0.071621 }, { -0.015165, 0.359326 },
//				{ 0.539368, -0.374972 }, { -0.319153, 0.629673 }, { 0.694424, 0.64118 }, { 0.079522, 0.193198 },
//				{ 0.253289, -0.285861 }, { -0.035558, -0.010086 }, { -0.403483, 0.474466 }, { -0.034312, 0.995685 },
//				{ -0.590657, 0.438051 }, { -0.098871, -0.023953 }, { -0.250001, 0.141621 }, { -0.012998, 0.525985 },
//				{ 0.153738, 0.491531 }, { 0.388215, -0.656567 }, { 0.049008, 0.013499 }, { 0.068286, 0.392741 },
//				{ 0.7478, -0.06663 }, { 0.004621, -0.042932 }, { -0.7016, 0.190983 }, { 0.055413, -0.02438 },
//				{ 0.035398, -0.333682 }, { 0.211795, 0.024689 }, { -0.045677, 0.172907 }, { 0.595222, 0.20957 },
//				{ 0.229465, 0.250409 }, { -0.089293, 0.068198 }, { 0.3843, -0.17657 }, { 0.834912, -0.110321 },
//				{ -0.307768, 0.503038 }, { -0.777063, -0.348066 }, { 0.01739, 0.152441 }, { -0.293382, -0.139778 },
//				{ -0.203272, 0.286855 }, { 0.957812, -0.152444 }, { 0.004609, -0.070617 }, { -0.755431, 0.096711 },
//				{ -0.526487, 0.547282 }, { -0.246873, 0.833713 }, { 0.185639, -0.066162 }, { 0.851934, 0.456603 },
//				{ -0.827912, 0.117122 }, { 0.233512, -0.106274 }, { 0.583671, -0.709033 }, { -0.487023, 0.62514 },
//				{ -0.448939, 0.176725 }, { 0.155907, -0.166371 }, { 0.334204, 0.381237 }, { 0.081536, -0.106212 },
//				{ 0.227222, 0.527437 }, { 0.75929, 0.33072 }, { 0.204177, -0.023516 }, { 0.577939, 0.403784 },
//				{ -0.568534, 0.442948 }, { -0.01152, 0.021165 }, { 0.87572, 0.422476 }, { 0.297885, -0.632874 },
//				{ -0.015821, 0.031226 }, { 0.541359, -0.205969 }, { -0.689946, -0.508674 }, { -0.343049, 0.841653 },
//				{ 0.523902, -0.436156 }, { 0.249281, -0.71184 }, { 0.193449, 0.574598 }, { -0.257542, -0.753885 },
//				{ -0.021605, 0.15808 }, { 0.601559, -0.727041 }, { -0.791603, 0.095651 }, { -0.908298, -0.053376 },
//				{ 0.12202, 0.850966 }, { -0.725568, -0.292022 } };

//		return returnData;
	}// Of testData

	/**
	 ************
	 * The label of test data.
	 * 
	 * @return The label of test data(double[]).
	 ************
	 */
	public static double[] testLabel1() {
//		double[] returnLabel = { -1.0, 1.0, -1.0, 1.0, -1.0, -1.0, -1.0, 1.0, -1.0, 1.0, 1.0, -1.0, 1.0, 1.0, 1.0, -1.0,
//				1.0, -1.0, 1.0, 1.0, -1.0, -1.0, 1.0, 1.0, -1.0, -1.0, 1.0, -1.0, 1.0, 1.0, -1.0, -1.0, -1.0, 1.0, 1.0,
//				1.0, -1.0, -1.0, -1.0, 1.0, 1.0, -1.0, -1.0, -1.0, 1.0, 1.0, -1.0, 1.0, -1.0, 1.0, 1.0, 1.0, 1.0, -1.0,
//				1.0, 1.0, 1.0, -1.0, -1.0, -1.0, 1.0, 1.0, 1.0, -1.0, 1.0, -1.0, -1.0, -1.0, 1.0, -1.0, -1.0, 1.0, -1.0,
//				-1.0, 1.0, 1.0, -1.0, 1.0, -1.0, -1.0, 1.0, -1.0, -1.0, 1.0, -1.0, -1.0, 1.0, -1.0, -1.0, -1.0, -1.0,
//				-1.0, -1.0, -1.0, 1.0, -1.0, -1.0, -1.0, -1.0, -1.0 };
		ArrayList<ArrayList<Double>> origin_data= FileProcessImpl.read_csv("C:\\Users\\FUBOFENG\\Desktop\\实训1\\Bug-prediction\\springboot\\src\\main\\resources\\static\\JDT.csv",true);
		ArrayList<ArrayList<Double>> all_labels=MatrixOperation.iloc(origin_data,0,origin_data.get(0).size()-1,origin_data.size()-1,origin_data.get(0).size()-1);
		double[][]labels=MatrixOperation.conversetoDouble(all_labels,false);
		double [] labelcon = new double[labels.length];
		for(int i = 0;i<labels.length;i++){
			if(labels[i][0] == 0) labelcon[i] = -1;
			else labelcon[i] = 1;
		}
		return labelcon;
//		return returnLabel;
	}// Of testLabel

	/**
	 ************
	 * The test data2.
	 * 
	 * @return The test data(double[][]).
	 ************
	 */
	public static double[][] testData2() {
		System.out.println(
				"Data from SimpleTool.testData2:\nThe simple binary data set(num-instances: 5; label: 1 or -1).\n");
		double[][] returnData = { { 1, 2.1 }, { 1.5, 1.6 }, { 1.3, 1 }, { 1, 1 }, { 2, 1 } };
		return returnData;
	}// Of testData2

	/**
	 ************
	 * The label of test data2.
	 * 
	 * @return The label of test data(double[]).
	 ************
	 */
	public static double[] testLabel2() {
		double[] returnLabel = { 1, 1, -1, -1, 1 };
		return returnLabel;
	}// Of testLabel2

	/**
	 ************
	 * The main
	 ************
	 */
	public static void main(String[] args) {
		if (FUNCTION_INTRODUCTION) {
			simpleToolIntroduction();
		} // Of if
		String[][] tempString = fileRead("src/data/trainingDataSvm.txt", "\t");
		for (int i = 0; i < tempString.length; i++) {
			System.out.println(Arrays.toString(tempString[i]));
		}//Of for i
	}// Of main
}// Of SimpleTool
