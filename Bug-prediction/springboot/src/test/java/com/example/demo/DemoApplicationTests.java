package com.example.demo;

import com.example.demo.utils.MatrixOperation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private MatrixOperation matrixOperation;

	@Test
	void contextLoads() {
		Double[][]A={{1.0,2.0,3.0},{1.0,2.0,3.0},{1.0,2.0,3.0}};
		Double[][]B={{1.0,2.0,3.0},{1.0,2.0,3.0},{1.0,2.0,3.0}};
		Double[][]result1=MatrixOperation.add(A,B);
		Double[][]result2=MatrixOperation.minus(A,B);
		Double[][]result3=MatrixOperation.iloc(A,1,1,2,2);
		Double[][]result4=MatrixOperation.iloc(A,2,2,1,1);
		Double[][]result5=MatrixOperation.matrixMatmul(A,B);
		Double[][]result6=MatrixOperation.trans(A);
		Double result7=MatrixOperation.sum(A);
		Double result8=MatrixOperation.mean(A);
		Double result9=MatrixOperation.sum_column(A,0);
		Double result10=MatrixOperation.mean_column(A,0);
		Double[][]result11=MatrixOperation.multi(A,B);
	}

}
