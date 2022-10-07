package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;

@Service
public class FileProcessImpl {
    //读取数据集,返回数据集的所有信息(特征+标签)
    // head=true表示文件包含标题，head=false表示文件不包含标题
    public static ArrayList<ArrayList<Double>> read_csv(String fileName, Boolean head){
        ArrayList<ArrayList<Double>>data=new ArrayList<>();
        File csv = new File(fileName);
        csv.setReadable(true);
        csv.setWritable(true);
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(new FileInputStream(csv), "UTF-8");
            br = new BufferedReader(isr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String line = "";
        try {
            while ((line = br.readLine()) != null) {
                if(head==true){
                    head=false;
                    continue;
                }
                String[] list=line.split(",");
                ArrayList<Double> temp_data=new ArrayList<>();

                for(int i=0;i<list.length-1;i++){
                    temp_data.add(Double.parseDouble(list[i]));
                }
                //如果最后一列是标签列，那么0代表样本标签为正常，1代表样本标签为缺陷
                if(list[list.length-1].equals("clean")){
                    temp_data.add(0.0);
                }else if(list[list.length-1].equals("buggy")){
                    temp_data.add(1.0);
                }else{
                    Double num=Double.parseDouble(list[list.length-1]);
                    temp_data.add(Double.parseDouble(list[list.length-1]));
                }
                data.add(temp_data);
            }
            System.out.println(fileName+",csv表格读取行数：" +data.size());
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
