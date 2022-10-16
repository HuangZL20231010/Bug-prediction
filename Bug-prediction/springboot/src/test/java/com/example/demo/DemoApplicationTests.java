package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
        String filePath = "D:\\ShiXun\\Bug-prediction\\springboot\\src\\main\\resources\\static\\uploadFiles\\JDT.csv";
        File file = new File(filePath);
        FileReader fileReader = null;
        String[] words;

        try {
            fileReader = new FileReader(file);

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            words = line.split(",");
            System.out.println(words);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
