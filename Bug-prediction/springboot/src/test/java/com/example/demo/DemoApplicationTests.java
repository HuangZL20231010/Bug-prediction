package com.example.demo;

import com.example.demo.service.impl.FileProcessImpl;
import com.example.demo.service.impl.LogisticRegressionImpl;
import com.example.demo.utils.MatrixOperation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private MatrixOperation matrixOperation;

	@Test
	void contextLoads() {
		LogisticRegressionImpl logisticRegression=new LogisticRegressionImpl(false,61);
		ArrayList<ArrayList<Double>> origin_data= FileProcessImpl.read_csv("src/main/resources/static/JDT.csv",true);
		ArrayList<ArrayList<Double>> all_features=MatrixOperation.converse(MatrixOperation.iloc(origin_data,0,0,origin_data.size()-1,origin_data.get(0).size()-2),true);
		ArrayList<ArrayList<Double>> all_labels=MatrixOperation.iloc(origin_data,0,origin_data.get(0).size()-1,origin_data.size()-1,origin_data.get(0).size()-1);
		logisticRegression.train(all_features,all_labels,100,10,0.1);
		ArrayList<ArrayList<Double>> result=logisticRegression.forward(all_features);
		Double acc=logisticRegression.evaluate(result,all_labels);
		System.out.println(acc);
	}

}
