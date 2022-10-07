package com.example.demo.service.impl;

import com.example.demo.service.UserTrainInfoService;
import com.example.demo.utils.Global;
import com.example.demo.utils.MatrixOperation;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class UserTrainInfoServiceImpl implements UserTrainInfoService {

    @Override
    public boolean storeFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();

        File dest = new File(Global.resourcesPath + "uploadFiles/" + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest); // 保存文件
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String systemPrediction(String filePath, String fileName) {

        /* 训练数据 */
        LogisticRegressionImpl logisticRegression=new LogisticRegressionImpl(false,61);
        ArrayList<ArrayList<Double>> origin_data= FileProcessImpl.read_csv(filePath,true);
        ArrayList<ArrayList<Double>> all_features= MatrixOperation.converse(MatrixOperation.iloc(origin_data,0,0,origin_data.size()-1,origin_data.get(0).size()-2),true);
        ArrayList<ArrayList<Double>> result=logisticRegression.predict(all_features);

        /* 将训练结果存到本地 */
        String destinationPath = Global.resourcesPath + "/predictionFiles/result_" + fileName;
        File file=new File(destinationPath);
        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw=new BufferedWriter(fw);
            int i, j;
            for(i = 0; i < result.size(); i++){
                for(j = 0; j < result.get(i).size(); j++){
                    bw.write(String.valueOf(result.get(i).get(j)));
                    bw.write(",");
                }
                System.out.println(j);
                bw.write('\n');
            }
            bw.close();
            fw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return destinationPath;
    }

}
